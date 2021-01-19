package com.easytools.tools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * package: com.easytools.tools.ServiceUtils
 * author: gyc
 * description:服务相关
 * time: create at 2016/10/17 16:03
 */

public class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 返回正在运行的服务
     *
     * @return
     */
    public static Set<String> getAllRunningServices() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) return null;
        for (RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * 开启服务
     *
     * @param className
     */
    public static void startService(final String className) {
        try {
            startService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启服务
     *
     * @param cls
     */
    public static void startService(final Class<?> cls) {
        startService(new Intent(Utils.getApp(), cls));
    }

    /**
     * 开启服务
     *
     * @param intent
     */
    public static void startService(Intent intent) {
        try {
            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Utils.getApp().startForegroundService(intent);
            } else {
                Utils.getApp().startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止服务
     *
     * @param className 类名
     * @return 是否执行成功
     */
    public static boolean stopService(String className) {
        try {
            return stopService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止服务
     *
     * @param cls The name of class.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(final Class<?> cls) {
        return stopService(new Intent(Utils.getApp(), cls));
    }

    /**
     * 停止服务
     *
     * @param intent The intent.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean stopService(@NonNull Intent intent) {
        try {
            return Utils.getApp().stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 绑定服务
     *
     * @param className The name of class.
     * @param conn      The ServiceConnection object.
     * @param flags     Operation options for the binding.
     *                  <ul>
     *                  <li>0</li>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(final String className, final ServiceConnection conn, final int flags) {
        try {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务
     *
     * @param cls   The service class.
     * @param conn  The ServiceConnection object.
     * @param flags Operation options for the binding.
     *              <ul>
     *              <li>0</li>
     *              <li>{@link Context#BIND_AUTO_CREATE}</li>
     *              <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *              <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *              <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *              <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *              <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *              </ul>
     */
    public static void bindService(final Class<?> cls, final ServiceConnection conn, final int flags) {
        bindService(new Intent(Utils.getApp(), cls), conn, flags);
    }

    /**
     * 绑定服务
     *
     * @param intent The intent.
     * @param conn   The ServiceConnection object.
     * @param flags  Operation options for the binding.
     *               <ul>
     *               <li>0</li>
     *               <li>{@link Context#BIND_AUTO_CREATE}</li>
     *               <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *               <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *               <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *               <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *               <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *               </ul>
     */
    public static void bindService(@NonNull final Intent intent, @NonNull final ServiceConnection conn, final int flags) {
        try {
            Utils.getApp().bindService(intent, conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解绑服务
     *
     * @param conn The ServiceConnection object.
     */
    public static void unbindService(final ServiceConnection conn) {
        Utils.getApp().unbindService(conn);
    }

    /**
     * 监测服务是否正在运行
     *
     * @param cls The service class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isServiceRunning(final Class<?> cls) {
        return isServiceRunning(cls.getName());
    }

    /**
     * 监测服务是否正在运行
     *
     * @param className The name of class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isServiceRunning(@NonNull final String className) {
        try {
            ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
            if (info == null || info.size() == 0) return false;
            for (RunningServiceInfo aInfo : info) {
                if (className.equals(aInfo.service.getClassName())) return true;
            }
            return false;
        } catch (Exception ignore) {
            return false;
        }
    }
}
