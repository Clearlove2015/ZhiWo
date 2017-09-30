package com.zhaoqy.self.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.gyf.barlibrary.ImmersionBar;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        unbinder = ButterKnife.bind(this);
        /**
         * 初始化沉浸式
         */
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        initToolbox();
        initViews();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mImmersionBar != null) {
            /**
             * 销毁mImmersionBar
             */
            mImmersionBar.destroy();
        }
    }

    protected abstract int getLayoutResID();

    protected void initImmersionBar() {
        /**
         * 初始化mImmersionBar
         */
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    protected abstract void initToolbox();

    protected abstract void initViews();

    protected abstract void setListener();

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }
}
