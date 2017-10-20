package com.zhaoqy.self.ui.activity.main.knowledge.banner.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by zhaoqy on 2017/9/27.
 */

public class FrescoImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         * 用fresco加载图片
         */
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);

    }

    /**
     * 提供createImageView 方法，方便fresco自定义ImageView
     */
    @Override
    public ImageView createImageView(Context context) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
        return simpleDraweeView;
    }
}
