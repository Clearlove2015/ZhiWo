package com.zhaoqy.self.ui.activity.drawer;

import android.view.View;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.OnClick;

public class PlatformActivity extends BaseToolboxActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_platform;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.careland, R.id.gaode, R.id.baidu, R.id.xunfei, R.id.weixin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.careland: {
                /**
                 * 凯立德开放平台
                 */
                break;
            }
            case R.id.gaode: {
                /**
                 * 高德开放平台
                 */
                break;
            }
            case R.id.baidu: {
                /**
                 * 百度开放平台
                 */
                break;
            }
            case R.id.xunfei: {
                /**
                 * 讯飞开放平台
                 */
                break;
            }
            case R.id.weixin: {
                /**
                 * 微信小程序
                 */
                break;
            }
        }
    }
}
