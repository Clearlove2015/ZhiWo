package com.zhaoqy.self.ui.base;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseEventActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
