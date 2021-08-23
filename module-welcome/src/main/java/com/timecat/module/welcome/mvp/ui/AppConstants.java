package com.timecat.module.welcome.mvp.ui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.timecat.extend.arms.BaseApplication;

public class AppConstants {
    public static final String FIRST_OPEN = "first_open" + getVersion(BaseApplication.getInstance());

    public static String getVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1";
        }
    }
}
