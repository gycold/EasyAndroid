package com.easytools.tools;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.Manifest.permission.EXPAND_STATUS_BAR;

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


    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    public static final int IMPORTANCE_NONE        = 0;
    public static final int IMPORTANCE_MIN         = 1;
    public static final int IMPORTANCE_LOW         = 2;
    public static final int IMPORTANCE_DEFAULT     = 3;
    public static final int IMPORTANCE_HIGH        = 4;

    @IntDef({IMPORTANCE_UNSPECIFIED, IMPORTANCE_NONE, IMPORTANCE_MIN, IMPORTANCE_LOW, IMPORTANCE_DEFAULT, IMPORTANCE_HIGH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Importance {
    }

    /**
     * Return whether the notifications enabled.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean areNotificationsEnabled() {
        return NotificationManagerCompat.from(Utils.getApp()).areNotificationsEnabled();
    }

    /**
     * Post a notification to be shown in the status bar.
     *
     * @param id       An identifier for this notification.
     * @param consumer The consumer of create the builder of notification.
     */
    public static void notify(int id, Utils.Consumer<NotificationCompat.Builder> consumer) {
        notify(null, id, ChannelConfig.DEFAULT_CHANNEL_CONFIG, consumer);
    }

    /**
     * Post a notification to be shown in the status bar.
     *
     * @param tag      A string identifier for this notification.  May be {@code null}.
     * @param id       An identifier for this notification.
     * @param consumer The consumer of create the builder of notification.
     */
    public static void notify(String tag, int id, Utils.Consumer<NotificationCompat.Builder> consumer) {
        notify(tag, id, ChannelConfig.DEFAULT_CHANNEL_CONFIG, consumer);
    }

    /**
     * Post a notification to be shown in the status bar.
     *
     * @param id            An identifier for this notification.
     * @param channelConfig The notification channel of config.
     * @param consumer      The consumer of create the builder of notification.
     */
    public static void notify(int id, ChannelConfig channelConfig, Utils.Consumer<NotificationCompat.Builder> consumer) {
        notify(null, id, channelConfig, consumer);
    }

    /**
     * Post a notification to be shown in the status bar.
     *
     * @param tag           A string identifier for this notification.  May be {@code null}.
     * @param id            An identifier for this notification.
     * @param channelConfig The notification channel of config.
     * @param consumer      The consumer of create the builder of notification.
     */
    public static void notify(String tag, int id, ChannelConfig channelConfig, Utils.Consumer<NotificationCompat.Builder> consumer) {
        NotificationManagerCompat.from(Utils.getApp()).notify(tag, id, getNotification(channelConfig, consumer));
    }


    public static Notification getNotification(ChannelConfig channelConfig, Utils.Consumer<NotificationCompat.Builder> consumer) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) Utils.getApp().getSystemService(Context.NOTIFICATION_SERVICE);
            //noinspection ConstantConditions
            nm.createNotificationChannel(channelConfig.getNotificationChannel());
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(Utils.getApp());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelConfig.mNotificationChannel.getId());
        }
        if (consumer != null) {
            consumer.accept(builder);
        }

        return builder.build();
    }

    /**
     * Cancel The notification.
     *
     * @param tag The tag for the notification will be cancelled.
     * @param id  The identifier for the notification will be cancelled.
     */
    public static void cancel(String tag, final int id) {
        NotificationManagerCompat.from(Utils.getApp()).cancel(tag, id);
    }

    /**
     * Cancel The notification.
     *
     * @param id The identifier for the notification will be cancelled.
     */
    public static void cancel(final int id) {
        NotificationManagerCompat.from(Utils.getApp()).cancel(id);
    }

    /**
     * Cancel all of the notifications.
     */
    public static void cancelAll() {
        NotificationManagerCompat.from(Utils.getApp()).cancelAll();
    }

    /**
     * Set the notification bar's visibility.
     * <p>Must hold {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />}</p>
     *
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void setNotificationBarVisibility(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        }
        invokePanels(methodName);
    }

    private static void invokePanels(final String methodName) {
        try {
            @SuppressLint("WrongConstant")
            Object service = Utils.getApp().getSystemService("statusbar");
            @SuppressLint("PrivateApi")
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ChannelConfig {

        public static final ChannelConfig DEFAULT_CHANNEL_CONFIG = new ChannelConfig(
                Utils.getApp().getPackageName(), Utils.getApp().getPackageName(), IMPORTANCE_DEFAULT
        );

        private NotificationChannel mNotificationChannel;

        public ChannelConfig(String id, CharSequence name, @Importance int importance) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel = new NotificationChannel(id, name, importance);
            }
        }

        public NotificationChannel getNotificationChannel() {
            return mNotificationChannel;
        }

        /**
         * Sets whether or not notifications posted to this channel can interrupt the user in
         * {@link android.app.NotificationManager.Policy#INTERRUPTION_FILTER_PRIORITY} mode.
         * <p>
         * Only modifiable by the system and notification ranker.
         */
        public ChannelConfig setBypassDnd(boolean bypassDnd) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setBypassDnd(bypassDnd);
            }
            return this;
        }

        /**
         * Sets the user visible description of this channel.
         *
         * <p>The recommended maximum length is 300 characters; the value may be truncated if it is too
         * long.
         */
        public ChannelConfig setDescription(String description) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setDescription(description);
            }
            return this;
        }

        /**
         * Sets what group this channel belongs to.
         * <p>
         * Group information is only used for presentation, not for behavior.
         * <p>
         * Only modifiable before the channel is submitted to
         * {@link NotificationManager#createNotificationChannel(NotificationChannel)}, unless the
         * channel is not currently part of a group.
         *
         * @param groupId the id of a group created by
         *                {@link NotificationManager#createNotificationChannelGroup)}.
         */
        public ChannelConfig setGroup(String groupId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setGroup(groupId);
            }
            return this;
        }

        /**
         * Sets the level of interruption of this notification channel.
         * <p>
         * Only modifiable before the channel is submitted to
         * {@link NotificationManager#createNotificationChannel(NotificationChannel)}.
         *
         * @param importance the amount the user should be interrupted by
         *                   notifications from this channel.
         */
        public ChannelConfig setImportance(@Importance int importance) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setImportance(importance);
            }
            return this;
        }

        /**
         * Sets the notification light color for notifications posted to this channel, if lights are
         * {@link NotificationChannel#enableLights(boolean) enabled} on this channel and the device supports that feature.
         * <p>
         * Only modifiable before the channel is submitted to
         * {@link NotificationManager#createNotificationChannel(NotificationChannel)}.
         */
        public ChannelConfig setLightColor(int argb) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setLightColor(argb);
            }
            return this;
        }

        /**
         * Sets whether notifications posted to this channel appear on the lockscreen or not, and if so,
         * whether they appear in a redacted form. See e.g. {@link Notification#VISIBILITY_SECRET}.
         * <p>
         * Only modifiable by the system and notification ranker.
         */
        public ChannelConfig setLockscreenVisibility(int lockscreenVisibility) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setLockscreenVisibility(lockscreenVisibility);
            }
            return this;
        }

        /**
         * Sets the user visible name of this channel.
         *
         * <p>The recommended maximum length is 40 characters; the value may be truncated if it is too
         * long.
         */
        public ChannelConfig setName(CharSequence name) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setName(name);
            }
            return this;
        }

        /**
         * Sets whether notifications posted to this channel can appear as application icon badges
         * in a Launcher.
         * <p>
         * Only modifiable before the channel is submitted to
         * {@link NotificationManager#createNotificationChannel(NotificationChannel)}.
         *
         * @param showBadge true if badges should be allowed to be shown.
         */
        public ChannelConfig setShowBadge(boolean showBadge) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setShowBadge(showBadge);
            }
            return this;
        }

        /**
         * Sets the sound that should be played for notifications posted to this channel and its
         * audio attributes. Notification channels with an {@link NotificationChannel#getImportance() importance} of at
         * least {@link NotificationManager#IMPORTANCE_DEFAULT} should have a sound.
         * <p>
         * Only modifiable before the channel is submitted to
         * {@link NotificationManager#createNotificationChannel(NotificationChannel)}.
         */
        public ChannelConfig setSound(Uri sound, AudioAttributes audioAttributes) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setSound(sound, audioAttributes);
            }
            return this;
        }

        /**
         * Sets the vibration pattern for notifications posted to this channel. If the provided
         * pattern is valid (non-null, non-empty), will {@link NotificationChannel#enableVibration(boolean)} enable
         * vibration} as well. Otherwise, vibration will be disabled.
         * <p>
         * Only modifiable before the channel is submitted to
         * {@link NotificationManager#createNotificationChannel(NotificationChannel)}.
         */
        public ChannelConfig setVibrationPattern(long[] vibrationPattern) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationChannel.setVibrationPattern(vibrationPattern);
            }
            return this;
        }
    }

}
