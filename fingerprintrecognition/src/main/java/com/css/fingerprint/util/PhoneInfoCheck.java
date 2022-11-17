package com.css.fingerprint.util;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述手机信息的检测对象
 */
public class PhoneInfoCheck {

    private static final String TAG = "PhoneInfoCheck";

    private final String SONY = "sony";
    private final String OPPO = "oppo";
    private final String HUAWEI = "huawei";
    private final String HONOR = "honor";
    private final String KNT = "knt";

    private static PhoneInfoCheck instance = null;

    public static synchronized PhoneInfoCheck getInstance(Context context, String brand) {
        if (null == instance)
            instance = new PhoneInfoCheck(context, brand);
        return instance;
    }

    private Context context;
    private String brand;

    private PhoneInfoCheck(Context context, String brand) {
        this.context = context;
        this.brand = brand;
    }

    /**
     * 跳转到指纹页面 或 通知用户去指纹录入
     */
    public void startFingerprint() {

        String pcgName = null;
        String clsName = null;

        if (compareTextSame(SONY)) {
            pcgName = "com.android.settings";
            clsName = "com.android.settings.Settings$FingerprintEnrollSuggestionActivity";
        } else if (compareTextSame(OPPO)) {
            pcgName = "com.coloros.fingerprint";
            clsName = "com.coloros.fingerprint.FingerLockActivity";
        } else if (compareTextSame(HUAWEI)) {
            pcgName = "com.android.settings";
            clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity";
        } else if (compareTextSame(HONOR)) {
            pcgName = "com.android.settings";
            clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity";
        }
        // TODO 后续机型会继续加入的 （Deliliu）
        // ....
        else {
            // 如果以上判断没有符合该机型，那就跳转到设置界面，让用户自己设置吧
            // Toast.makeText(context, "请到设置中，找到指纹录入，进行指纹录入操作", Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(context)
                    .setTitle("指纹录入")
                    .setMessage("请到设置中，找到指纹录入，进行指纹录入操作")
                    .setPositiveButton("好的，我去录入指纹", null)
                    .show();

            // 跳转到Settings页面的Intent
            /*pcgName = "com.android.settings";
            clsName = "com.android.settings.Settings";*/
        }

        if (!TextUtils.isEmpty(pcgName) && !TextUtils.isEmpty(clsName)) {

            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(pcgName, clsName);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setComponent(componentName);
            context.startActivity(intent);
        }
    }

    /**
     * 获得当前手机品牌
     *
     * @return 例如：HONOR
     */
    private String getBrand() {
        Log.d(TAG, "PhoneInfoCheck Board:" + android.os.Build.BRAND + " brand:" + brand);
        return this.brand;
    }

    /**
     * 对比两个字符串，并且比较字符串是否包含在其中的，并且忽略大小写
     *
     * @param value
     * @return
     */
    private boolean compareTextSame(String value) {
        return value.toUpperCase().indexOf(getBrand().toUpperCase()) >= 0;
    }

    @Deprecated
    public static void main(String[] args) {
        String str = "java JavA ABC";
        Pattern test_ = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
        Matcher result_ = test_.matcher(str);

        // str = result_.replaceAll("Perl6");

        System.out.println(str);
        System.out.println(result_);

        // -----------------------------------

        String str1 = "abcdefghijklmnabc";
        String str2 = "HiJK";
        // 查找是否存在指定的字符
        System.out.println(str2.indexOf(str1));
        // 忽略大小写查找
        System.out.println("HONOR".toUpperCase().indexOf("HONOR".toUpperCase()) >= 0);

    }
}
