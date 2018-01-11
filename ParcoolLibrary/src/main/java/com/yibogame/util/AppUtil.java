package com.yibogame.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;


import com.yibogame.app.BaseApplication;

import java.io.File;
import java.util.List;

/**
 * Created by parcool on 2016/4/22.
 */
public class AppUtil {
    private static AppUtil ourInstance = new AppUtil();

    public static AppUtil getInstance() {
        return ourInstance;
    }

    private AppUtil() {
    }

    /**
     * 获取应用程序名称
     */
    public String getAppName() {
        try {
            PackageManager packageManager = BaseApplication.context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(BaseApplication.context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return BaseApplication.context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 得到软件显示版本信息
     *
     * @return 当前版本信息
     */
    public String getVersionName() {
        String verName = "";
        try {
            String packageName = BaseApplication.context.getPackageName();
            verName = BaseApplication.context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 得到软件版本号
     *
     * @return 当前版本Code
     */
    public int getVersionCode() {
        int verCode = -1;
        try {
            String packageName = BaseApplication.context.getPackageName();
            verCode = BaseApplication.context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }


    /**
     * 安装apk
     *
     * @param file APK文件
     */
    public void installApk(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        BaseApplication.context.startActivity(intent);
    }

    public Intent installIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 安装apk
     *
     * @param file APK文件uri
     */
    public void installApk(Uri file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/vnd.android.package-archive");
        BaseApplication.context.startActivity(intent);
    }

    public boolean isAppInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

}
