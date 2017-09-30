package com.zhaoqy.self.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhaoqy on 2017/9/19.
 */

public class AppContext {

    private static Context mContext = null;
    private static Application mApplication = null;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static void setApplication(Application application) {
        mApplication = application;
    }
}
