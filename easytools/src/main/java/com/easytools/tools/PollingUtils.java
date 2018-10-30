package com.easytools.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * package: com.easytools.tools.PollingUtils
 * author: gyc
 * description:轮询相关工具类
 * time: create at 2017/1/2 20:46
 */

public class PollingUtils {

    private PollingUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 开启轮询服务
     * @param context 上下文
     * @param seconds 轮询间隔
     * @param cls 开启的服务类
     * @param action 设置要启动的标示
     */
    public static void startPollingService(Context context, int seconds,
                                           Class<?> cls, String action) {
        // 获取AlarmManagerar系统服务
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        // 包装需要执行Service的Intent
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 触发服务的起始时间 ，这个是从开机到现在的毫秒数，包括手机睡眠时间
        long triggerAtTime = SystemClock.elapsedRealtime();

        // 使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
                seconds * 1000, pendingIntent);
    }

    /**
     * 停止轮询
     * @param context
     * @param cls
     * @param action
     */
    public static void stopPollingService(Context context, Class<?> cls,
                                          String action) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //取消正在执行的服务
        manager.cancel(pendingIntent);
    }
}
