package com.zhaoqy.self.ui.activity.setting;

import android.view.View;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.OnClick;

public class FeedbackActivity extends BaseToolboxActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit: {
                /**
                 * 提交
                 */
                break;
            }

        }
    }
}
