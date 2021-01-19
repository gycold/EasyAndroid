package com.easytools.tools;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * package: com.easytools.tools.NotificationUtils
 * author: gyc
 * description:
 * 从 Android 8.0（API 级别 26）开始，所有通知都必须分到一个渠道，否则通知将不会显示；
 * * 在运行 Android 7.1（API 级别 25）及更低版本的设备上，用户可以仅按应用来管理通知（实际上，每个应用在 Android 7.1 或更低版本中仅有一个渠道）。
 * * 8.0以上必须设置通知重要性，Android 使用通知的重要性来决定该通知应在多大程度上（视觉和听觉上）打扰用户。通知的重要性越高，通知的打扰级别就越高。
 * * 在 Android 8.0（API 级别 26）及更高版本上，通知的重要性由通知目标发布渠道的 importance 决定。用户可以在系统设置中更改通知渠道的重要性。在 Android 7.1（API 级别 25）及更低版本上，各通知的重要性由通知的priority 决定。
 * time: create at 2016/10/17 15:57
 */

public class NotificationUtils {

    public static final String CHANNEL_ID = "channel_id_" + AppUtils.getAppPackageName();
    public static final int NOTIFICATION_ID = 0x01001;

    public static void createNotificationChannel(Context context, String channel_name, String channel_description) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channel_name, importance);
            channel.setDescription(channel_description);
            channel.setSound(null, null);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.createNotificationChannel(channel);
        }
    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context, String channel_name, String channel_description, int smallIcon, int largeIcon, String contentTitle, String contentText, PendingIntent pendingIntent) {
//        Bitmap bitmap = BitmapUtils.resource2Bitmap(context, largeIcon);
        createNotificationChannel(context, channel_name, channel_description);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), largeIcon);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(smallIcon)
                .setLargeIcon(bitmap)
                .setSound(null)
                .setOnlyAlertOnce(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder;
    }

    public static void showNotification(Context context, Class<?> cls, String channel_name, String channel_description, int smallIcon, int largeIcon, String contentTitle, String contentText) {
        Intent intentClick = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
        createNotificationChannel(context, channel_name, channel_description);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), largeIcon);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(smallIcon)
                .setLargeIcon(bitmap)
                .setSound(null)
                .setOnlyAlertOnce(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        nofity(context, builder);
    }

    public static void nofity(Context context, NotificationCompat.Builder builder) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void cancle(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public static Notification getNotification(NotificationCompat.Builder builder) {
        return builder.build();
    }

}
