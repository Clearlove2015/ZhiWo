package com.zhaoqy.self.ui.activity.main.tool.imagemanager;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.dialog.ProgressDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GalleryActivity extends BaseToolboxActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public final static String EXTRA_IMAGE_LIST = "image_list";
    public final static String EXTRA_IMAGE_POSITION = "image_position";

    private ArrayList<String> list;
    private int position;

    @BindView(R.id.page)
    TextView page;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private GalleryAdapter adapter;
    private Bitmap bmp = null;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initData() {
        list = getIntent().getStringArrayListExtra(EXTRA_IMAGE_LIST);
        position = getIntent().getIntExtra(EXTRA_IMAGE_POSITION, 0);
        initAdapter();
        updatePage(position);
    }

    private void initAdapter() {
        adapter = new GalleryAdapter(this, viewPager, list);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updatePage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @OnClick({R.id.wallpaper})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wallpaper: {
                makeWallpaper();
                break;
            }
        }
    }

    private void updatePage(int position) {
        page.setText((position + 1) + "/" + list.size());
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

        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inDither = false;
        bfOptions.inPurgeable = true;
        bfOptions.inTempStorage = new byte[12 * 1024];
        bfOptions.inJustDecodeBounds = false;// true 只生成边框
        File file = new File(list.get(position));
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
            if (fs != null){
                bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
                if(bmp == null){
                    if (ProgressDialog.isShowProgress()) {
                        ProgressDialog.cancelProgress();
                    }
                    Toast.makeText(GalleryActivity.this, "设置壁纸失败, 换张图片试试吧!", Toast.LENGTH_SHORT).show();
                }else{
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                    int desiredMinimumWidth = getWindowManager().getDefaultDisplay().getWidth();
                    int desiredMinimumHeight = getWindowManager().getDefaultDisplay().getHeight();
                    try {
                        wallpaperManager.suggestDesiredDimensions(desiredMinimumWidth, desiredMinimumHeight);
                        wallpaperManager.setBitmap(bmp);
                        if (ProgressDialog.isShowProgress()) {
                            ProgressDialog.cancelProgress();
                        }
                        Toast.makeText(GalleryActivity.this, "壁纸设置成功", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        if (ProgressDialog.isShowProgress()) {
                            ProgressDialog.cancelProgress();
                        }
                        Toast.makeText(GalleryActivity.this, "设置壁纸失败, 换张图片试试吧!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            if (ProgressDialog.isShowProgress()) {
                ProgressDialog.cancelProgress();
            }
            Toast.makeText(GalleryActivity.this, "设置壁纸失败, 换张图片试试吧!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy(){
        if(bmp!=null && !bmp.isRecycled()){
            bmp.recycle();
        }
        super.onDestroy();
    }
}
