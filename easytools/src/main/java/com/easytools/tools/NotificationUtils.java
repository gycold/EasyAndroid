package com.easytools.tools;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * package: com.easytools.tools.NotificationUtils
 * author: gyc
 * description: Notification相关，兼容Android 8.0
 * time: create at 2016/10/17 15:57
 */

public class NotificationUtils {


    public static NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    /**
     * 8.0以上，获取NotificationChannel对象
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    /**
     * 获取 NotificationManager
     * @return
     */
    public static NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) Utils.getApp().getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    /**
     * 8.0以上，获取Notification.Builder
     * @param title
     * @param content
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification.Builder getChannelNotification(String title, String content) {
        return new Notification.Builder(Utils.getApp().getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    /**
     * 8.0以下，获取NotificationCompat.Builder
     * @param title
     * @param content
     * @return
     */
    public static NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(Utils.getApp().getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    /**
     * 设置通知信息
     * @param title
     * @param content
     */
    public static void sendNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).build();
            getManager().notify(1, notification);
        } else {
            Notification notification = getNotification_25(title, content).build();
            getManager().notify(1, notification);
        }
    }

}
