package com.zhaoqy.self.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.zhaoqy.self.ui.activity.MainActivity;
import com.zhaoqy.self.util.FileUtils;
import com.zhaoqy.self.util.SPUtil;
import com.zxy.recovery.core.Recovery;

/**
 * Created by zhaoqy on 2017/9/19.
 */

public class SelfApplication extends Application {

    public static Context mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        AppContext.setApplication(this);
        AppContext.setContext(getApplicationContext());
        SPUtil.init(this);
        FileUtils.initFile();
        /**
         * 初始化内存泄漏检测库
         */
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        /**
         * Fresco使用之前，要初始化
         */
        Fresco.initialize(this);

        /**
         * 初始化Recovery
         */
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .init(this);
    }

}
