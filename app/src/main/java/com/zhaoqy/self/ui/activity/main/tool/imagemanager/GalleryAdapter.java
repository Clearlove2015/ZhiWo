package com.zhaoqy.self.ui.activity.main.tool.imagemanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoqy.self.R;
import com.zhaoqy.self.util.BitmapHelper;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by zhaoqy on 2017/10/13.
 */

public class GalleryAdapter extends PagerAdapter {

    Context context;
    ViewPager viewPager;
    List<String> list;

    public GalleryAdapter(Context context, ViewPager viewPager, List<String> list) {
        super();
        this.context = context;
        this.viewPager = viewPager;
        this.list = list;
    }

    @Override
    public int getCount() {
        if  (list == null || list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_big_image, null);
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        BitmapHelper.getInstance().addPathToShowlist(list.get(position));
        photoView.setTag(list.get(position));
        Bitmap bitmap = BitmapHelper.getInstance().getBitmap(list.get(position), 0, 0,
                new BitmapHelper.ILoadImageCallback() {

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
        BitmapHelper.getInstance().removePathFromShowlist(list.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
