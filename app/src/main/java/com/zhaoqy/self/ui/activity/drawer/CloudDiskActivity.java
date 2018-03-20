package com.zhaoqy.self.ui.activity.drawer;

import android.view.View;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import butterknife.OnClick;

public class CloudDiskActivity extends BaseBarActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_cloud_disk;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.baiduyun, R.id.disk360, R.id.mircodisk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.baiduyun: {
                /**
                 * 百度云
                 */
                break;
            }
            case R.id.disk360: {
                /**
                 * 360云盘
                 */
                break;
            }
            case R.id.mircodisk: {
                /**
                 * 微盘
                 */
                break;
            }
        }
    }
}
