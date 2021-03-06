package com.zhaoqy.self.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;

import java.util.List;

public class AppInfo {

	private static final String TAG = "AppInfo";

	public static String getPkgName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			String pkgName = packageInfo.applicationInfo.packageName;
			return pkgName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取app名称
	 * 
	 * @param context
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 通过pkgname获取版本号VerCode
	 * 
	 * @param context
	 */
	public static int getVerCode(Context context) {
		int verCode = 0;
		String pkgname = getPkgName(context);
		PackageManager pm = context.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(pkgname,
					PackageManager.GET_PERMISSIONS);
			if (packageInfo != null) {
				verCode = packageInfo.versionCode;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}

	/**
	 * 通过pkgname获取版本号VerName
	 * 
	 * @param context
	 */
	public static String getVerName(Context context) {
		String verName = "";
		String pkgname = getPkgName(context);
		PackageManager pm = context.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(pkgname,
					PackageManager.GET_PERMISSIONS);
			if (packageInfo != null) {
				verName = packageInfo.versionName;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * 获取android系统版本号
	 */
	public static String getSystemVer() {
		// String device_model = Build.MODEL; // MODEL
		// String version_sdk = Build.VERSION.SDK; // SDK版本号
		String version_release = Build.VERSION.RELEASE; // 系统版本号

		// CldLog.i(TAG, "   device_model: " + device_model);
		// CldLog.i(TAG, "    version_sdk: " + version_sdk);
		return version_release;
	}

	public static boolean isForeground(Context context) {
		if (context == null)
			return false;

		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		String curPkg = cn.getPackageName();
		if (!TextUtils.isEmpty(curPkg)
				&& curPkg.equals(context.getPackageName())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断某个界面是否在前台
	 * 
	 * @param context
	 *            Context
	 * @param className
	 *            界面的类名
	 * @return 是否在前台显示
	 */
	public static boolean isForeground(Context context, String className) {
		if (context == null || TextUtils.isEmpty(className))
			return false;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;
			if (className.equals(cpn.getClassName()))
				return true;
		}
		return false;
	}

	/**
	 * @Title: getRunningActivityName
	 * @Description: 获得当前activity的名字
	 * @param context
	 * @return: String
	 */
	public static String getRunningActivityName(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
				.getClassName();
		String contextActivity = runningActivity.substring(runningActivity
				.lastIndexOf(".") + 1);
		return contextActivity;
	}
}
