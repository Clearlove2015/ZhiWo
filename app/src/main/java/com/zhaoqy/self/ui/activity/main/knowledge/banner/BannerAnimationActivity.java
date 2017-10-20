package com.zhaoqy.self.ui.activity.main.knowledge.banner;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.youth.banner.transformer.CubeInTransformer;
import com.youth.banner.transformer.CubeOutTransformer;
import com.youth.banner.transformer.DefaultTransformer;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.transformer.FlipHorizontalTransformer;
import com.youth.banner.transformer.FlipVerticalTransformer;
import com.youth.banner.transformer.ForegroundToBackgroundTransformer;
import com.youth.banner.transformer.RotateDownTransformer;
import com.youth.banner.transformer.RotateUpTransformer;
import com.youth.banner.transformer.ScaleInOutTransformer;
import com.youth.banner.transformer.StackTransformer;
import com.youth.banner.transformer.TabletTransformer;
import com.youth.banner.transformer.ZoomInTransformer;
import com.youth.banner.transformer.ZoomOutSlideTransformer;
import com.youth.banner.transformer.ZoomOutTranformer;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.knowledge.banner.loader.GlideImageLoader;
import com.zhaoqy.self.ui.adapter.BannerAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.widget.recyclerview.decoration.LinearDivider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class BannerAnimationActivity extends BaseToolboxActivity implements OnBannerListener, BannerAdapter.OnItemClickListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BannerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    List<Class<? extends ViewPager.PageTransformer>> transformers = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_banner_animation;
    }

    @Override
    protected void initData() {
        initAdapter();
        initBanner();
        initAnim();
    }

    private void initAdapter() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.banner_anim)));
        mAdapter = new BannerAdapter(mDatas);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LinearDivider(this, LinearLayoutManager.VERTICAL));
    }

    private void initBanner() {
        List<String> images = Arrays.asList(getResources().getStringArray(R.array.banner_url));
        banner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    private void initAnim() {
        transformers.add(DefaultTransformer.class);
        transformers.add(AccordionTransformer.class);
        transformers.add(BackgroundToForegroundTransformer.class);
        transformers.add(ForegroundToBackgroundTransformer.class);
        transformers.add(CubeInTransformer.class);//兼容问题，慎用
        transformers.add(CubeOutTransformer.class);
        transformers.add(DepthPageTransformer.class);
        transformers.add(FlipHorizontalTransformer.class);
        transformers.add(FlipVerticalTransformer.class);
        transformers.add(RotateDownTransformer.class);
        transformers.add(RotateUpTransformer.class);
        transformers.add(ScaleInOutTransformer.class);
        transformers.add(StackTransformer.class);
        transformers.add(TabletTransformer.class);
        transformers.add(ZoomInTransformer.class);
        transformers.add(ZoomOutTranformer.class);
        transformers.add(ZoomOutSlideTransformer.class);
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(this,"你点击了：" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 开始轮播
         */
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        /**
         * 结束轮播
         */
        banner.stopAutoPlay();
    }

    @Override
    public void onItemClick(View view, int position) {
        banner.setBannerAnimation(transformers.get(position));
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
