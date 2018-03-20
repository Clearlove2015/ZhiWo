package com.zhaoqy.self.ui.activity.main.knowledge.mvp;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.ui.dialog.ProgressDialog;
import com.zhaoqy.self.util.FileUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;

public class ImageDetailActivity extends BaseBarActivity implements View.OnClickListener {

    @BindView(R.id.photoview)
    PhotoView photoview;
    private String bitmapUrl;
    private String bitmapId;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void initData() {
        bitmapId = getIntent().getExtras().getString("PicId");
        bitmapUrl = getIntent().getExtras().getString("PicUrl");
        Glide.with(this)
                .load(bitmapUrl)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .into(photoview);
    }

    @OnClick({R.id.save, R.id.wallpaper})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save: {
                savePicture(this, bitmapId);
                break;
            }
            case R.id.wallpaper: {
                makeWallpaper();
                break;
            }
        }
    }

    private void savePicture(final Context context, final String id) {
        String str = getResources().getString(
                R.string.tip_downloading);
        ProgressDialog.showProgress(this, str, new ProgressDialog.ProgressDialogListener () {

            @Override
            public void onCancel() {

            }
        });

        Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    /**
                     * Picasso加载图片质量高于Glide
                     */
                    bitmap = Picasso.with(context).load(bitmapUrl).get();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                if (bitmap == null) {
                    subscriber.onError(new Exception("无法下载图片"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {

                    @Override
                    public void onCompleted() {
                        if (ProgressDialog.isShowProgress()) {
                            ProgressDialog.cancelProgress();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        FileUtils.saveBitmapToFile(ImageDetailActivity.this,id,bitmap);
                    }
                });
    }

    /**
     * 把图片设置成手机壁纸
     */
    private void makeWallpaper() {
        String str = getResources().getString(
                R.string.tip_setting);
        ProgressDialog.showProgress(this, str, new ProgressDialog.ProgressDialogListener () {

            @Override
            public void onCancel() {

            }
        });

        Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(ImageDetailActivity.this).load(bitmapUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {

                    @Override
                    public void onCompleted() {
                        if (ProgressDialog.isShowProgress()) {
                            ProgressDialog.cancelProgress();
                        }
                        Toast.makeText(ImageDetailActivity.this, "壁纸设置成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        try {
                            WallpaperManager instance = WallpaperManager.getInstance(ImageDetailActivity.this);
                            int desiredMinimumWidth = getWindowManager().getDefaultDisplay().getWidth();
                            int desiredMinimumHeight = getWindowManager().getDefaultDisplay().getHeight();
                            instance.suggestDesiredDimensions(desiredMinimumWidth, desiredMinimumHeight);
                            instance.setBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
