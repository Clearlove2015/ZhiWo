package com.zhaoqy.self.ui.activity.drawer;

import android.view.View;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.OnClick;

public class NewsActivity extends BaseToolboxActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_news;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.zhihu, R.id.toutiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhihu: {
                /**
                 * 知乎
                 */
                break;
            }
            case R.id.toutiao: {
                /**
                 * 今日头条
                 */
                break;
            }
        }
    }
}
