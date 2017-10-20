package com.zhaoqy.self.ui.activity.main.knowledge.banner;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.knowledge.banner.loader.GlideImageLoader;
import com.zhaoqy.self.ui.adapter.BannerAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.widget.recyclerview.decoration.LinearDivider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class BannerIndicatorActivity extends BaseToolboxActivity implements OnBannerListener, BannerAdapter.OnItemClickListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BannerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_banner_indicator;
    }

    @Override
    protected void initData() {
        initAdapter();
        initBanner();
    }

    private void initAdapter() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.banner_indicator)));
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
        switch (position){
            case 0: {
                banner.setIndicatorGravity(BannerConfig.LEFT);
                break;
            }
            case 1: {
                banner.setIndicatorGravity(BannerConfig.CENTER);
                break;
            }
            case 2: {
                banner.setIndicatorGravity(BannerConfig.RIGHT);
                break;
            }
        }
        banner.start();
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
