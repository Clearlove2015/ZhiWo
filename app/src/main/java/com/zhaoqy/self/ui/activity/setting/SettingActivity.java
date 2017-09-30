package com.zhaoqy.self.ui.activity.setting;

import android.content.Intent;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.zhaoqy.self.R;
import com.zhaoqy.self.app.AppConst;
import com.zhaoqy.self.service.FloatingBallService;
import com.zhaoqy.self.ui.activity.setting.about.AboutActivity;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.util.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseToolboxActivity implements View.OnClickListener {

    @BindView(R.id.open_guide)
    SwitchCompat open_guide;
    @BindView(R.id.floating_ball)
    SwitchCompat floating_ball;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        initFirstOpen();
        initFloatingBall();
        setOnCheckedChangeListener();
    }

    @OnClick({R.id.pay, R.id.feedback, R.id.clear, R.id.about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay: {
                /**
                 * 支付密码
                 */
                Intent intent = new Intent(this, PayPwdActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.feedback: {
                /**
                 * 意见反馈
                 */
                Intent intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.clear: {
                /**
                 * 清除缓存
                 */
                break;
            }
            case R.id.about: {
                /**
                 * 关于
                 */
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void initFirstOpen() {
        boolean isFirstOpen = SPUtil.getBoolean(AppConst.ISFIRSTOPEN, false);
        if (isFirstOpen) {
            open_guide.setChecked(true);
        } else {
            open_guide.setChecked(false);
        }
    }

    private void initFloatingBall() {
        boolean isOpenFloatingBall = SPUtil.getBoolean(AppConst.ISOPEN_FLOATINGBALL, false);
        if (isOpenFloatingBall) {
            floating_ball.setChecked(true);
        } else {
            floating_ball.setChecked(false);
        }
    }

    private void setOnCheckedChangeListener() {
        open_guide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SPUtil.put(AppConst.ISFIRSTOPEN, isChecked);
            }
        });

        floating_ball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SPUtil.put(AppConst.ISOPEN_FLOATINGBALL, isChecked);
                if (isChecked) {
                    Intent intent = new Intent(SettingActivity.this, FloatingBallService.class);
                    startService(intent);
                } else {
                    Intent intent = new Intent(SettingActivity.this, FloatingBallService.class);
                    stopService(intent);
                }
            }
        });
    }
}
