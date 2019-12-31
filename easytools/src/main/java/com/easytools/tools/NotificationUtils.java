package com.easytools.tools;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * package: com.easytools.tools.NotificationUtils
 * author: gyc
 * description:
 *  从 Android 8.0（API 级别 26）开始，所有通知都必须分到一个渠道，否则通知将不会显示；
 *  * 在运行 Android 7.1（API 级别 25）及更低版本的设备上，用户可以仅按应用来管理通知（实际上，每个应用在 Android 7.1 或更低版本中仅有一个渠道）。
 *  * 8.0以上必须设置通知重要性，Android 使用通知的重要性来决定该通知应在多大程度上（视觉和听觉上）打扰用户。通知的重要性越高，通知的打扰级别就越高。
 *  * 在 Android 8.0（API 级别 26）及更高版本上，通知的重要性由通知目标发布渠道的 importance 决定。用户可以在系统设置中更改通知渠道的重要性。在 Android 7.1（API 级别 25）及更低版本上，各通知的重要性由通知的priority 决定。
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
