package com.easytools.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * package: com.easytools.tools.CrashUtils
 * author: gyc
 * description:崩溃相关的工具类，捕获崩溃信息，保存在本地txt文件
 * time: create at 2017/1/13 18:01
 */

public class CrashUtils implements Thread.UncaughtExceptionHandler{

    private static CrashUtils mInstance = new CrashUtils();
    private Thread.UncaughtExceptionHandler mHandler;
    private boolean mInitialized;
    private static String crashDir;
    private String versionName;
    private int versionCode;

    private CrashUtils() {}

    /**
     * 获取单例
     * 可以在Application中初始化：CrashUtils.getInstance().init(this);
     * @return
     */
    public static CrashUtils getInstance() {
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context
     * @return
     */
    public boolean init(Context context) {
        if (mInitialized) return true;
        if (SDCardUtils.isSDCardEnable()) {
            crashDir = context.getExternalCacheDir().getPath() + File.separator + "crash_info" + File.separator;
        } else {
            crashDir = context.getCacheDir().getPath() + File.separator + "crash_info" + File.separator;
        }

        try{
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return mInitialized = false;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }

    @Override
    /**
     * 捕获异常，写入本地文件
     */
    public void uncaughtException(Thread thread, Throwable throwable) {
        String now = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String fullPath = crashDir + now + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath)) return;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fullPath, false));
            pw.write(getCrashHead());
            throwable.printStackTrace();
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(pw);
                cause = cause.getCause();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(pw);
        }
        if (mHandler != null) {
            mHandler.uncaughtException(thread, throwable);
        }
    }

    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    private String getCrashHead() {
        return "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer(设备厂商): " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model(设备型号)       : " + Build.MODEL +// 设备型号
                "\nAndroid Version(系统版本)    : " + Build.VERSION.RELEASE +// 系统版本
                "\nAndroid SDK(安卓版本)        : " + Build.VERSION.SDK_INT +// SDK版本
                "\nApp VersionName(版本名称)    : " + versionName +
                "\nApp VersionCode(版本Code)    : " + versionCode +
                "\n************* Crash Log Head ****************\n\n";
    }
}