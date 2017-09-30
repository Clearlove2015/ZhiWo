package com.zhaoqy.self.ui.activity.info;

import com.bumptech.glide.Glide;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

public class BigImageActivity extends BaseToolboxActivity {

    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_big_image;
    }

    @Override
    protected void initData() {
        Glide.with(this)
                .load(R.mipmap.head)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img_failed)
                .fitCenter()
                .into(photoView);
    }
}
