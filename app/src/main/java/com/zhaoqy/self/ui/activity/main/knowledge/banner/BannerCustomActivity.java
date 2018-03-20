package com.zhaoqy.self.ui.activity.main.knowledge.banner;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.knowledge.banner.loader.GlideImageLoader;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class BannerCustomActivity extends BaseBarActivity {

    @BindView(R.id.banner1)
    Banner banner1;
    @BindView(R.id.banner2)
    Banner banner2;
    @BindView(R.id.banner3)
    Banner banner3;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_banner_custom;
    }

    @Override
    protected void initData() {
        List<String> images = Arrays.asList(getResources().getStringArray(R.array.banner_url));
        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.banner_title));

        banner1.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .start();

        banner2.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .start();

        banner3.setImages(images)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .start();
    }
}
