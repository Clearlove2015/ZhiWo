package com.zhaoqy.self.ui.activity.setting.about;

import android.widget.ImageView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.BindView;

public class AwardActivity extends BaseToolboxActivity {

    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.airpay)
    ImageView airpay;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_award;
    }

    @Override
    protected void initData() {

    }
}
