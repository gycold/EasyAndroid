package com.easytools.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * package: com.easytools.tools.AppUtils
 * author: gyc
 * description:App相关工具类：
 * <p>
 * registerAppStatusChangedListener  : 注册 App 前后台切换监听器
 * unregisterAppStatusChangedListener: 注销 App 前后台切换监听器
 * installApp                        : 安装 App（支持 8.0）
 * installAppSilent                  : 静默安装 App
 * uninstallApp                      : 卸载 App
 * uninstallAppSilent                : 静默卸载 App
 * isAppInstalled                    : 判断 App 是否安装
 * isAppRoot                         : 判断 App 是否有 root 权限
 * isAppDebug                        : 判断 App 是否是 Debug 版本
 * isAppSystem                       : 判断 App 是否是系统应用
 * isAppForeground                   : 判断 App 是否处于前台
 * launchApp                         : 打开 App
 * relaunchApp                       : 重启 App
 * launchAppDetailsSettings          : 打开 App 具体设置
 * exitApp                           : 关闭应用
 * getAppIcon                        : 获取 App 图标
 * getAppPackageName                 : 获取 App 包名
 * getAppName                        : 获取 App 名称
 * getAppPath                        : 获取 App 路径
 * getAppVersionName                 : 获取 App 版本号
 * getAppVersionCode                 : 获取 App 版本码
 * getAppSignature                   : 获取 App 签名
 * getAppSignatureSHA1               : 获取应用签名的的 SHA1 值
 * getAppSignatureSHA256             : 获取应用签名的的 SHA256 值
 * getAppSignatureMD5                : 获取应用签名的的 MD5 值
 * getAppInfo                        : 获取 App 信息
 * getAppsInfo                       : 获取所有已安装 App 信息
 * <p>
 * time: create at 2016/10/9 21:56
 */

public class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Register the status of application changed listener.
     *
     * @param listener The status of application changed listener
     */
    public static void registerAppStatusChangedListener(@NonNull final Utils.OnAppStatusChangedListener listener) {
        UtilsBridge.addOnAppStatusChangedListener(listener);
    }

    /**
     * Unregister the status of application changed listener.
     *
     * @param listener The status of application changed listener
     */
    public static void unregisterAppStatusChangedListener(@NonNull final Utils.OnAppStatusChangedListener listener) {
        UtilsBridge.removeOnAppStatusChangedListener(listener);
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     */
    public static void installApp(final String filePath) {
        installApp(UtilsBridge.getFileByPath(filePath));
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     */
    public static void installApp(final File file) {
        Intent installAppIntent = UtilsBridge.getInstallAppIntent(file);
        if (installAppIntent == null) return;
        Utils.getApp().startActivity(installAppIntent);
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param uri The uri.
     */
    public static void installApp(final Uri uri) {
        Intent installAppIntent = UtilsBridge.getInstallAppIntent(uri);
        if (installAppIntent == null) return;
        Utils.getApp().startActivity(installAppIntent);
    }

    /**
     * Uninstall the app.
     * <p>Target APIs greater than 25 must hold
     * Must hold {@code <uses-permission android:name="android.permission.REQUEST_DELETE_PACKAGES" />}</p>
     *
     * @param packageName The name of the package.
     */
    public static void uninstallApp(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return;
        Utils.getApp().startActivity(UtilsBridge.getUninstallAppIntent(packageName));
    }

    /**
     * Return whether the app is installed.
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(final String pkgName) {
        if (UtilsBridge.isSpace(pkgName)) return false;
        PackageManager pm = Utils.getApp().getPackageManager();
        try {
            return pm.getApplicationInfo(pkgName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Return whether the application with root permission.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRoot() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("echo root", true);
        return result.result == 0;
    }

    /**
     * Return whether it is a debug application.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug() {
        return isAppDebug(Utils.getApp().getPackageName());
    }

    /**
     * Return whether it is a debug application.
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return false;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return whether it is a system application.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem() {
        return isAppSystem(Utils.getApp().getPackageName());
    }

    /**
     * Return whether it is a system application.
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return false;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return whether application is foreground.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground() {
        return UtilsBridge.isAppForeground();
    }

    /**
     * Return whether application is foreground.
     * <p>Target APIs greater than 21 must hold
     * {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground(@NonNull final String pkgName) {
        return !UtilsBridge.isSpace(pkgName) && pkgName.equals(UtilsBridge.getForegroundProcessName());
    }

    /**
     * Return whether application is running.
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRunning(final String pkgName) {
        if (UtilsBridge.isSpace(pkgName)) return false;
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(Integer.MAX_VALUE);
            if (taskInfo != null && taskInfo.size() > 0) {
                for (ActivityManager.RunningTaskInfo aInfo : taskInfo) {
                    if (aInfo.baseActivity != null) {
                        if (pkgName.equals(aInfo.baseActivity.getPackageName())) {
                            return true;
                        }
                    }
                }
            }
            List<ActivityManager.RunningServiceInfo> serviceInfo = am.getRunningServices(Integer.MAX_VALUE);
            if (serviceInfo != null && serviceInfo.size() > 0) {
                for (ActivityManager.RunningServiceInfo aInfo : serviceInfo) {
                    if (pkgName.equals(aInfo.service.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Launch the application.
     *
     * @param packageName The name of the package.
     */
    public static void launchApp(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return;
        Intent launchAppIntent = UtilsBridge.getLaunchAppIntent(packageName);
        if (launchAppIntent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        Utils.getApp().startActivity(launchAppIntent);
    }

    /**
     * Relaunch the application.
     */
    public static void relaunchApp() {
        relaunchApp(false);
    }

    /**
     * Relaunch the application.
     *
     * @param isKillProcess True to kill the process, false otherwise.
     */
    public static void relaunchApp(final boolean isKillProcess) {
        Intent intent = UtilsBridge.getLaunchAppIntent(Utils.getApp().getPackageName());
        if (intent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        Utils.getApp().startActivity(intent);
        if (!isKillProcess) return;
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(Utils.getApp().getPackageName());
    }

    /**
     * Launch the application's details settings.
     *
     * @param pkgName The name of the package.
     */
    public static void launchAppDetailsSettings(final String pkgName) {
        if (UtilsBridge.isSpace(pkgName)) return;
        Intent intent = UtilsBridge.getLaunchAppDetailsSettingsIntent(pkgName, true);
        if (!UtilsBridge.isIntentAvailable(intent)) return;
        Utils.getApp().startActivity(intent);
    }

    /**
     * Launch the application's details settings.
     *
     * @param activity    The activity.
     * @param requestCode The requestCode.
     */
    public static void launchAppDetailsSettings(final Activity activity, final int requestCode) {
        launchAppDetailsSettings(activity, requestCode, Utils.getApp().getPackageName());
    }

    /**
     * Launch the application's details settings.
     *
     * @param activity    The activity.
     * @param requestCode The requestCode.
     * @param pkgName     The name of the package.
     */
    public static void launchAppDetailsSettings(final Activity activity, final int requestCode, final String pkgName) {
        if (activity == null || UtilsBridge.isSpace(pkgName)) return;
        Intent intent = UtilsBridge.getLaunchAppDetailsSettingsIntent(pkgName, false);
        if (!UtilsBridge.isIntentAvailable(intent)) return;
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Exit the application.
     */
    public static void exitApp() {
        UtilsBridge.finishAllActivities();
        System.exit(0);
    }

    /**
     * Return the application's icon.
     *
     * @return the application's icon
     */
    @Nullable
    public static Drawable getAppIcon() {
        return getAppIcon(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's icon.
     *
     * @param packageName The name of the package.
     * @return the application's icon
     */
    @Nullable
    public static Drawable getAppIcon(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the application's icon resource identifier.
     *
     * @return the application's icon resource identifier
     */
    public static int getAppIconId() {
        return getAppIconId(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's icon resource identifier.
     *
     * @param packageName The name of the package.
     * @return the application's icon resource identifier
     */
    public static int getAppIconId(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return 0;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? 0 : pi.applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * Return true if this is the first ever time that the application is installed on the device.
     *
     * @return true if this is the first ever time that the application is installed on the device.
     */
    public static boolean isFirstTimeInstall() {
        try {
            long firstInstallTime = Utils.getApp().getPackageManager().getPackageInfo(getAppPackageName(), 0).firstInstallTime;
            long lastUpdateTime = Utils.getApp().getPackageManager().getPackageInfo(getAppPackageName(), 0).lastUpdateTime;
            return firstInstallTime == lastUpdateTime;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Return true if app was previously installed and this one is an update/upgrade to that one, returns false if this is a fresh installation and not an update/upgrade.
     *
     * @return true if app was previously installed and this one is an update/upgrade to that one, returns false if this is a fresh installation and not an update/upgrade.
     */
    public static boolean isAppUpgraded() {
        try {
            long firstInstallTime = Utils.getApp().getPackageManager().getPackageInfo(getAppPackageName(), 0).firstInstallTime;
            long lastUpdateTime = Utils.getApp().getPackageManager().getPackageInfo(getAppPackageName(), 0).lastUpdateTime;
            return firstInstallTime != lastUpdateTime;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Return the application's package name.
     *
     * @return the application's package name
     */
    @NonNull
    public static String getAppPackageName() {
        return Utils.getApp().getPackageName();
    }

    /**
     * Return the application's name.
     *
     * @return the application's name
     */
    @NonNull
    public static String getAppName() {
        return getAppName(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's name.
     *
     * @param packageName The name of the package.
     * @return the application's name
     */
    @NonNull
    public static String getAppName(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return "";
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's path.
     *
     * @return the application's path
     */
    @NonNull
    public static String getAppPath() {
        return getAppPath(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's path.
     *
     * @param packageName The name of the package.
     * @return the application's path
     */
    @NonNull
    public static String getAppPath(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return "";
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version name.
     *
     * @return the application's version name
     */
    @NonNull
    public static String getAppVersionName() {
        return getAppVersionName(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's version name.
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    @NonNull
    public static String getAppVersionName(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return "";
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's version code.
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return -1;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Return the application's minimum sdk version code.
     *
     * @return the application's minimum sdk version code
     */
    public static int getAppMinSdkVersion() {
        return getAppMinSdkVersion(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's minimum sdk version code.
     *
     * @param packageName The name of the package.
     * @return the application's minimum sdk version code
     */
    public static int getAppMinSdkVersion(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return -1;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) return -1;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            if (null == pi) return -1;
            ApplicationInfo ai = pi.applicationInfo;
            return null == ai ? -1 : ai.minSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Return the application's target sdk version code.
     *
     * @return the application's target sdk version code
     */
    public static int getAppTargetSdkVersion() {
        return getAppTargetSdkVersion(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's target sdk version code.
     *
     * @param packageName The name of the package.
     * @return the application's target sdk version code
     */
    public static int getAppTargetSdkVersion(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return -1;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            if (null == pi) return -1;
            ApplicationInfo ai = pi.applicationInfo;
            return null == ai ? -1 : ai.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Return the application's signature.
     *
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures() {
        return getAppSignatures(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's signature.
     *
     * @param packageName The name of the package.
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures(final String packageName) {
        if (UtilsBridge.isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES);
                if (pi == null) return null;

                SigningInfo signingInfo = pi.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    return signingInfo.getApkContentsSigners();
                } else {
                    return signingInfo.getSigningCertificateHistory();
                }
            } else {
                PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
                if (pi == null) return null;

                return pi.signatures;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the application's signature.
     *
     * @param file The file.
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures(final File file) {
        if (file == null) return null;
        PackageManager pm = Utils.getApp().getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            PackageInfo pi = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_SIGNING_CERTIFICATES);
            if (pi == null) return null;

            SigningInfo signingInfo = pi.signingInfo;
            if (signingInfo.hasMultipleSigners()) {
                return signingInfo.getApkContentsSigners();
            } else {
                return signingInfo.getSigningCertificateHistory();
            }
        } else {
            PackageInfo pi = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_SIGNATURES);
            if (pi == null) return null;

            return pi.signatures;
        }
    }

    /**
     * Return the application's signature for SHA1 value.
     *
     * @return the application's signature for SHA1 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA1() {
        return getAppSignaturesSHA1(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's signature for SHA1 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA1 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA1(final String packageName) {
        return getAppSignaturesHash(packageName, "SHA1");
    }

    /**
     * Return the application's signature for SHA256 value.
     *
     * @return the application's signature for SHA256 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA256() {
        return getAppSignaturesSHA256(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's signature for SHA256 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA256 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA256(final String packageName) {
        return getAppSignaturesHash(packageName, "SHA256");
    }

    /**
     * Return the application's signature for MD5 value.
     *
     * @return the application's signature for MD5 value
     */
    @NonNull
    public static List<String> getAppSignaturesMD5() {
        return getAppSignaturesMD5(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's signature for MD5 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for MD5 value
     */
    @NonNull
    public static List<String> getAppSignaturesMD5(final String packageName) {
        return getAppSignaturesHash(packageName, "MD5");
    }

    /**
     * Return the application's user-ID.
     *
     * @return the application's signature for MD5 value
     */
    public static int getAppUid() {
        return getAppUid(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's user-ID.
     *
     * @param pkgName The name of the package.
     * @return the application's signature for MD5 value
     */
    public static int getAppUid(String pkgName) {
        try {
            return Utils.getApp().getPackageManager().getApplicationInfo(pkgName, 0).uid;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static List<String> getAppSignaturesHash(final String packageName, final String algorithm) {
        ArrayList<String> result = new ArrayList<>();
        if (UtilsBridge.isSpace(packageName)) return result;
        Signature[] signatures = getAppSignatures(packageName);
        if (signatures == null || signatures.length <= 0) return result;
        for (Signature signature : signatures) {
            String hash = UtilsBridge.bytes2HexString(UtilsBridge.hashTemplate(signature.toByteArray(), algorithm))
                    .replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
            result.add(hash);
        }
        return result;
    }

    /**
     * Return the application's information.
     * <ul>
     * <li>name of package</li>
     * <li>icon</li>
     * <li>name</li>
     * <li>path of package</li>
     * <li>version name</li>
     * <li>version code</li>
     * <li>minimum sdk version code</li>
     * <li>target sdk version code</li>
     * <li>is system</li>
     * </ul>
     *
     * @return the application's information
     */
    @Nullable
    public static AppInfo getAppInfo() {
        return getAppInfo(Utils.getApp().getPackageName());
    }

    /**
     * Return the application's information.
     * <ul>
     * <li>name of package</li>
     * <li>icon</li>
     * <li>name</li>
     * <li>path of package</li>
     * <li>version name</li>
     * <li>version code</li>
     * <li>minimum sdk version code</li>
     * <li>target sdk version code</li>
     * <li>is system</li>
     * </ul>
     *
     * @param packageName The name of the package.
     * @return the application's information
     */
    @Nullable
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            if (pm == null) return null;
            return getBean(pm, pm.getPackageInfo(packageName, 0));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the applications' information.
     *
     * @return the applications' information
     */
    @NonNull
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = Utils.getApp().getPackageManager();
        if (pm == null) return list;
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

    /**
     * Return the application's package information.
     *
     * @return the application's package information
     */
    @Nullable
    public static AppUtils.AppInfo getApkInfo(final File apkFile) {
        if (apkFile == null || !apkFile.isFile() || !apkFile.exists()) return null;
        return getApkInfo(apkFile.getAbsolutePath());
    }

    /**
     * Return the application's package information.
     *
     * @return the application's package information
     */
    @Nullable
    public static AppUtils.AppInfo getApkInfo(final String apkFilePath) {
        if (UtilsBridge.isSpace(apkFilePath)) return null;
        PackageManager pm = Utils.getApp().getPackageManager();
        if (pm == null) return null;
        PackageInfo pi = pm.getPackageArchiveInfo(apkFilePath, 0);
        if (pi == null) return null;
        ApplicationInfo appInfo = pi.applicationInfo;
        appInfo.sourceDir = apkFilePath;
        appInfo.publicSourceDir = apkFilePath;
        return getBean(pm, pi);
    }


    /**
     * Return whether the application was first installed.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFirstTimeInstalled() {
        try {
            PackageInfo pi = Utils.getApp().getPackageManager().getPackageInfo(Utils.getApp().getPackageName(), 0);
            return pi.firstInstallTime == pi.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pi == null) return null;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        String packageName = pi.packageName;
        ApplicationInfo ai = pi.applicationInfo;
        if (ai == null) {
            return new AppInfo(packageName, "", null, "", versionName, versionCode, -1, -1, false);
        }
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        int minSdkVersion = -1;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            minSdkVersion = ai.minSdkVersion;
        }
        int targetSdkVersion = ai.targetSdkVersion;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, minSdkVersion, targetSdkVersion, isSystem);
    }

    /**
     * The application's information.
     */
    public static class AppInfo {

        private String packageName;
        private String name;
        private Drawable icon;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private int minSdkVersion;
        private int targetSdkVersion;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        public int getMinSdkVersion() {
            return minSdkVersion;
        }

        public void setMinSdkVersion(int minSdkVersion) {
            this.minSdkVersion = minSdkVersion;
        }

        public int getTargetSdkVersion() {
            return targetSdkVersion;
        }

        public void setTargetSdkVersion(int targetSdkVersion) {
            this.targetSdkVersion = targetSdkVersion;
        }

        public AppInfo(String packageName, String name, Drawable icon, String packagePath, String versionName, int versionCode, int minSdkVersion, int targetSdkVersion, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setMinSdkVersion(minSdkVersion);
            this.setTargetSdkVersion(targetSdkVersion);
            this.setSystem(isSystem);
        }

        @Override
        @NonNull
        public String toString() {
            return "{" +
                    "\n    pkg name: " + getPackageName() +
                    "\n    app icon: " + getIcon() +
                    "\n    app name: " + getName() +
                    "\n    app path: " + getPackagePath() +
                    "\n    app v name: " + getVersionName() +
                    "\n    app v code: " + getVersionCode() +
                    "\n    app v min: " + getMinSdkVersion() +
                    "\n    app v target: " + getTargetSdkVersion() +
                    "\n    is system: " + isSystem() +
                    "\n}";
        }
    }
}
