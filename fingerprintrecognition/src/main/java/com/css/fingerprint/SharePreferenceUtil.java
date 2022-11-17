package com.css.fingerprint;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 指纹数据存储
 */
public class SharePreferenceUtil {
    private static final String DEFAULT_NAME = "finger";
    /*
    * 是否启用指纹变化监听
    * 如果还没有开启指纹数据变化监听，但是指纹数据已经发生了改变，这种情况需要排除，清除指纹数据变化，重新生成指纹加密库key
    */
    private static String KEY_IS_FINGER_CHANGE_ENABLE = "is_finger_change_enable";
    private static String KEY_IS_FINGER_CHANGE = "is_finger_change";//指纹是否变化了

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, Context.MODE_PRIVATE);
    }

    //保存是否启动监听设备指纹数据变化的状态
    public static void saveEnableFingerDataChange(Context context, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_FINGER_CHANGE_ENABLE, value);
        editor.apply();
    }

    //判断是否启动了监听设备指纹数据变化
    public static boolean isEnableFingerDataChange(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_IS_FINGER_CHANGE_ENABLE, false);
    }
    //判断指纹数据是否变化
    public static void saveFingerDataChange(Context context, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_FINGER_CHANGE, value);
        editor.apply();
    }
    //判断指纹数据是否变化
    public static boolean isFingerDataChange(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_IS_FINGER_CHANGE, false);
    }


}
