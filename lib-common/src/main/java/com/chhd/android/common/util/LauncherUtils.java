package com.chhd.android.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.chhd.android.common.global.BaseApplication;

import java.util.List;

/**
 * author : 葱花滑蛋
 * time   : 2018/01/06
 * desc   :
 */
public class LauncherUtils {

    private LauncherUtils() {
    }

    private static Context getContext() {
        return CommonUtils.getApplication();
    }

    public static void changeLauncherInfo(String activeName, String inactiveName) {
        changeLauncherInfo(activeName, inactiveName, false);
    }

    public static void changeLauncherInfo(String activeName, String inactiveName, boolean isImmediate) {
        PackageManager packageManager = getContext().getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(getContext(), inactiveName),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(getContext(), activeName),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        if (isImmediate) {
            restartSystemLauncher();
        }
    }

    private static void restartSystemLauncher() {
        PackageManager packageManager = getContext().getPackageManager();
        ActivityManager activityManager = (ActivityManager) getContext()
                .getSystemService(Activity.ACTIVITY_SERVICE);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            if (resolveInfo.activityInfo != null && activityManager != null) {
                activityManager.killBackgroundProcesses(resolveInfo.activityInfo.packageName);
            }
        }
    }
}
