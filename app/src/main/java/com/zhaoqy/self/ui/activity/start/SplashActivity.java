package com.zhaoqy.self.ui.activity.start;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhaoqy.self.R;
import com.zhaoqy.self.app.AppConst;
import com.zhaoqy.self.ui.activity.MainActivity;
import com.zhaoqy.self.util.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 功能特性：文字竖排显示
 * 技术点：
 * 1. 控件android:ems属性
 * 注意：只有在android:layout_width="wrap_content"时，android:ems属性才会有效果，否则无效
 * 2. 隐藏标题栏（自定义theme）
 * 3. 动画
 */
public class SplashActivity extends AppCompatActivity {

    Unbinder unbinder;
    @BindView(R.id.splash)
    RelativeLayout splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);

        /**
         * 设置日间-夜间模式
         */
        if (SPUtil.getBoolean(AppConst.ISNIGHT, false)) {
            /**
             * 夜间模式
             */
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            /**
             * 日间模式
             */
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        startAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 设置动画
     */
    private void startAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.05f, 1.0f, 1.05f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.8f, 1.0f);
        scaleAnimation.setDuration(500);
        alphaAnimation.setDuration(500);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        splash.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //onJump();
                jumpToNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    protected void onJump() {
        boolean isFirstOpen = SPUtil.getBoolean(AppConst.ISFIRSTOPEN, true);
        if (isFirstOpen) {
            SPUtil.put(AppConst.ISFIRSTOPEN, false);
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            boolean isLogin = true;
            if (isLogin) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermissions();
    }

    /**
     * 延时跳转
     */
    private void jump() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                onJump();
            }
        }, 2500);
    }

    private void requestPermissions() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(
                        //电话、通讯录、短信
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.READ_PHONE_STATE,
                        //相机、麦克风
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        //存储空间
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        //位置(需要定位功能sdk)
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .callback(listener)
                .rationale(new RationaleListener() {

                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        /**
                         * 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请
                         */
                        AndPermission.rationaleDialog(SplashActivity.this, rationale).show();
                    }
                })
                .start();
    }

    private PermissionListener listener = new PermissionListener() {

        /**
         * 权限申请成功回调
         * @param requestCode 申请时设置的requestCode
         * @param grantPermissions
         */
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            isPermissionRequstFinish = true;
            if (requestCode == 100) {
                for (String permission : grantPermissions) {
                    //Log.e("SplashActivity", "permission: " + permission);
                }
            }
        }

        /**
         * 权限申请失败回调
         * @param requestCode 申请时设置的requestCode
         * @param deniedPermissions
         */
        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            isPermissionRequstFinish = true;
        }
    };

    private boolean isPermissionRequstFinish = false;

    private  Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    if (isPermissionRequstFinish) {
                        jumpToNext();
                    } else {
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
                }
            }
        }
    };

    private void jumpToNext() {
        if (isPermissionRequstFinish) {
            onJump();
        } else {
           mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
