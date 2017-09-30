package com.zhaoqy.self.ui.activity.main.banner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.banner.loader.GlideImageLoader;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BannerMainActivity extends BaseToolboxActivity implements View.OnClickListener, OnBannerListener {

    private static final int REFRESH_COMPLETE = 0X1112;

    @BindView(R.id.swipelayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.banner)
    Banner banner;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_banner_main;
    }

    @Override
    protected void initData() {
        mSwipeLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSwipeLayout.setEnabled(true);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
            }
        });

        banner.setImages(new ArrayList<>())
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @OnClick({R.id.banner0, R.id.banner1, R.id.banner2, R.id.banner3, R.id.banner4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner0: {
                /**
                 * 动画预览
                 */
                Intent intent = new Intent(this, BannerAnimationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.banner1: {
                /**
                 * 内置样式预览
                 */
                Intent intent = new Intent(this, BannerStyleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.banner2: {
                /**
                 * 自定义样式预览
                 */
                Intent intent = new Intent(this, BannerCustomActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.banner3: {
                /**
                 * 指示器设置预览
                 */
                Intent intent = new Intent(this, BannerIndicatorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.banner4: {
                /**
                 * 加载本地图片
                 */
                Intent intent = new Intent(this, BannerLocalActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    String[] urls = getResources().getStringArray(R.array.banner_url);
                    List list = Arrays.asList(urls);
                    List arrayList = new ArrayList(list);
                    banner.update(arrayList);
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        }
    };

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
}
