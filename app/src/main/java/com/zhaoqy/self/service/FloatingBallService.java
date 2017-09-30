package com.zhaoqy.self.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhaoqy.self.R;
import com.zhaoqy.self.util.DpUtil;

public class FloatingBallService extends Service {
    /**
     * 浮动窗口布局
     */
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    /**
     * 创建浮动窗口设置布局参数的对象
     */
    WindowManager mWindowManager;
    ImageView mFloatView;
    private boolean longClick = false;

    public FloatingBallService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams();
        //获取WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags =
                // LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                // LayoutParams.FLAG_NOT_TOUCHABLE

        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;

        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 20;
        wmParams.y = 200;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.layout_floating_ball, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);

        //浮动窗口按钮
        mFloatView = (ImageView) mFloatLayout.findViewById(R.id.floating_ball);
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        /**
         * 设置监听浮动窗口的触摸移动
         */
        mFloatView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (longClick) {
                    //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                    wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth() / 2;
                    //25为状态栏的高度
                    int statusBarHeight = DpUtil.getStatusBarHeight(getApplicationContext());
                    wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight() / 2 - statusBarHeight;
                    mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                }
                return false;
            }
        });

        mFloatView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                longClick = true;
                return false;
            }
        });

        mFloatView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                longClick = false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }
    }
}
