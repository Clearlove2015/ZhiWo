package com.zhaoqy.self.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Map;

/**
 * Created by zhaoqy on 2017/9/19.
 */

public class SPUtil {

    private static final String TARGET_SHARE_NAME = "com.zhaoqy";
    private static SharedPreferences.Editor mEditor = null;
    private static SharedPreferences mSP = null;

    @SuppressLint("WorldReadableFiles")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressWarnings("deprecation")
    public static void init(Context context) {
        mSP = context.getSharedPreferences(TARGET_SHARE_NAME,
                Context.MODE_WORLD_READABLE | Context.MODE_MULTI_PROCESS);
        mEditor = mSP.edit();
    }

    public static void put(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static void put(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public static void put(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public static void put(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public static void put(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public static String getString(String key) {
        return mSP.getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return mSP.getString(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return mSP.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mSP.getBoolean(key, defaultValue);
    }

    public static float getFloat(String key) {
        return mSP.getFloat(key, 0.0F);
    }

    public static long getLong(String key) {
        return mSP.getLong(key, 0L);
    }

    public static long getLong(String key, long defaultValue) {
        return mSP.getLong(key, defaultValue);
    }

    public static int getInt(String key) {
        return mSP.getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        return mSP.getInt(key, defaultValue);
    }

    public static Map<String, ?> getAll() {
        return mSP.getAll();
    }

    public static boolean isContains(String key) {
        return mSP.contains(key);
    }

    public static void remove(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public static void clear() {
        mEditor.clear().commit();
    }
}
