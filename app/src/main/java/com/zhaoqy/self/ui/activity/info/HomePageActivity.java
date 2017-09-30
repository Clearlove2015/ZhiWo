package com.zhaoqy.self.ui.activity.info;

import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseActivity;

import butterknife.BindView;
import jp.wasabeef.blurry.Blurry;

public class HomePageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head_bg)
    ImageView head_bg;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(toolbar).init();
    }

    @Override
    protected void initToolbox() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void initViews() {
        /**
         * 图片边模糊效果
         */
        Blurry.with(this)
                .from(BitmapFactory.decodeResource(getResources(), R.mipmap.head_bg))
                .into(head_bg);
    }

    @Override
    protected void setListener() {
        /**
         * 设置FloatingActionButton的点击事件监听
         */
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "我是Snackbar", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**
             * Toolbar导航按钮的按键事件
             */
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
