package com.zhaoqy.self.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoqy.self.R;
import com.zhaoqy.self.bean.SingleImageModel;
import com.zhaoqy.self.util.AlbumBitmapCacheHelper;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by zhaoqy on 2017/9/29.
 */

public class PreviewPageAdapter extends PagerAdapter {

    Context context;
    ViewPager viewPager;
    List<SingleImageModel> allimages;

    public PreviewPageAdapter(Context context, ViewPager viewPager, List<SingleImageModel> views) {
        super();
        this.context = context;
        this.viewPager = viewPager;
        this.allimages = views;
    }

    @Override
    public int getCount() {
        if  (allimages == null || allimages.isEmpty()) {
            return 0;
        } else {
            return allimages.size();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_big_image, null);
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        AlbumBitmapCacheHelper.getInstance().addPathToShowlist(allimages.get(position).path);
        photoView.setTag(allimages.get(position).path);
        Bitmap bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap(allimages.get(position).path, 0, 0,
                new AlbumBitmapCacheHelper.ILoadImageCallback() {

            @Override
            public void onLoadImageCallBack(Bitmap bitmap, String path, Object... objects) {
                PhotoView view = ((PhotoView)viewPager.findViewWithTag(path));
                if (view != null && bitmap != null) {
                    photoView.setImageBitmap(bitmap);
                }
            }
        }, position);
        if (bitmap != null){
            photoView.setImageBitmap(bitmap);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        AlbumBitmapCacheHelper.getInstance().removePathFromShowlist(allimages.get(position).path);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
