package com.zhaoqy.self.ui.activity.main.knowledge.banner;

import com.youth.banner.Banner;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.knowledge.banner.loader.GlideImageLoader;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BannerLocalActivity extends BaseToolboxActivity {

    @BindView(R.id.banner)
    Banner banner;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_banner_local;
    }

    @Override
    protected void initData() {
        /**
         * 本地图片数据（资源文件）
         */
        List<Integer> list=new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
    }
}
