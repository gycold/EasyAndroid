package com.easytools.tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.io.File;


/**
 * package: com.easytools.tools.NotificationUtil
 * author: gyc
 * description:与Notification操作相关的工具类
 * time: create at 2016/10/17 15:57
 */

public class NotificationUtil {

    private ClickListener listener;

    public ClickListener getListener() {
        return listener;
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    /**
     * 发出一条通知，没有震动，使用默认的声音和光线，点击该通知后自动消失
     *
     * @param context           上下文
     * @param smallIcon         小图标资源，用于在状态栏中表示通知。
     *                          当视图展开时将在左侧绘制此图标，除非还指定了大图标，在这种情况下，小图标将移动到右侧
     * @param largeIcon         大图标资源
     * @param ticker            通知首次出现在通知栏，带上升动画效果的，如：“更新失败！”
     * @param title             通知标题
     * @param msg               通知显示的内容
     * @param uri               点击意图后携带的资源
     * @param activityClassName 意图跳转的类的全名：包.类
     * @param id                通知栏通知的id，同一个app，不同id会显示分别独立显示出来
     */
    public static void showNotificationByNomal(Context context, int smallIcon, Bitmap largeIcon,
                                               String ticker, String title, String msg, Uri uri,
                                               String
                                                       activityClassName, int id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context
                .NOTIFICATION_SERVICE);
        if (uri != null) {
            intent.setData(uri);
        } else if (activityClassName != null) {
            //设置意图，注意第二个参数是类的全名：包.类
            intent.setComponent(new ComponentName(context.getPackageName(), activityClassName));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            intent.setPackage(context.getPackageName());//如果非空，则仅匹配给定包名的类
        }
        builder.setContentTitle(title)//设置通知标题
                .setContentText(msg)//设置通知显示的内容
                .setTicker(ticker)//通知首次出现在通知栏，带上升动画效果的，如：“更新失败！”
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setShowWhen(true)//设置setWhen的时间是否出现内容视图中
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setAutoCancel(false)// 设置true,当用户单击面板就可以让通知将自动取消
                .setOngoing(true)// ture,设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,
                // 因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)//
                // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(smallIcon)
                //设置小图标资源，用于在状态栏中表示通知。当视图展开时将在左侧绘制此图标，除非还指定了大图标，在这种情况下，小图标将移动到右侧
                .setLargeIcon(largeIcon)//设置通知栏大图标资源
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent
                        .FLAG_UPDATE_CURRENT));
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(id, notification);
    }

    /**
     * 显示一条没有指定跳转类的通知
     *
     * @param context   上下文
     * @param smallIcon 小图标资源，用于在状态栏中表示通知。
     *                  当视图展开时将在左侧绘制此图标，除非还指定了大图标，在这种情况下，小图标将移动到右侧
     * @param ticker    通知首次出现在通知栏，带上升动画效果的，如：“更新失败！”
     * @param title     通知标题
     * @param msg       通知显示的内容
     * @param uri       点击意图后携带的资源
     */
    public static void showNotificationByNomal(Context context, int smallIcon, String ticker,
                                               String title, String msg, Uri uri) {
        showNotificationByNomal(context, smallIcon, null, ticker, title, msg, uri, null, 0);
    }

    /**
     * 创建一个下载更新的
     *
     * @param context
     * @param smallIcon
     * @param largeIcon
     * @param ticker
     * @param title
     * @param msg
     * @param progress
     * @param filePath
     * @param id
     * @param listener
     */
    public static void showNotificationByProgress(Context context, int smallIcon, Bitmap
            largeIcon, String ticker, String title, String msg, int progress, String filePath,
                                                  int id, ClickListener listener) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context
                .NOTIFICATION_SERVICE);
        builder.setContentTitle(title)//设置通知标题
                .setContentText(msg)//设置通知显示的内容
                .setTicker(ticker)//通知首次出现在通知栏，带上升动画效果的，如：“更新失败！”
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setShowWhen(true)//设置setWhen的时间是否出现内容视图中
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setAutoCancel(false)// 设置true,当用户单击面板就可以让通知将自动取消
                .setOngoing(true)// ture,设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,
                // 因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)//
                // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(smallIcon)
                //设置小图标资源，用于在状态栏中表示通知。当视图展开时将在左侧绘制此图标，除非还指定了大图标，在这种情况下，小图标将移动到右侧
                .setLargeIcon(largeIcon);//设置通知栏大图标资源
        if (progress > 0 && progress < 100) {
            // 将setProgress的第三个参数设为true即可显示为无明确进度的进度条样式
            builder.setProgress(100, progress, false);
            builder.setContentText("已下载:" + progress + "%");
        } else if (progress == 100) {
            builder.setContentText("点击安装");
            File apkFile = new File(filePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + apkFile.getAbsolutePath()),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
            manager.cancel(id);
            listener.onClick();//在点击方法里实现stopSelf();用以停止服务
        } else {
            builder.setProgress(0, 0, false);
        }
        // 如果progress<100，则使用一个空的intent,点击动作事件等都包含在这里
        builder.setContentIntent(progress >= 100 ? getContentIntent(context, filePath)
                : PendingIntent.getActivity(context, 0, new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT));
        Notification mNotification = builder.build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
//		mNotification.contentView.setImageViewResource(android.R.id.icon, R.drawable.ic_launcher);
        manager.notify(id, mNotification);
    }

    private static PendingIntent getContentIntent(Context context, String filePath) {
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.getAbsolutePath()),
                "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public static void showNotificationByBigContent() {

    }

    public static void showNotificationByRemoteView() {

    }

    public static void cancel() {

    }

    public interface ClickListener {
        void onClick();
    }

}
