package com.css.fingerprint;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.css.fingerprint.dialog.BaseFingerDialog;
import com.css.fingerprint.impl.BiometricPromptImpl23;
import com.css.fingerprint.impl.BiometricPromptImpl28;
import com.css.fingerprint.interfaces.IBiometricPrompt;

import javax.crypto.Cipher;

/**
 * 指纹识别管理类
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerManager {

    private static FingerManager fingerManager;

    private static FingerManagerBuilder mFingerManagerBuilder;

    /**
     * 这个对象的作用是用来取消指纹扫描器的扫描操作。比如在用户点击识别框上的“取消”按钮或者“密码验证”按钮后，就要及时取消扫描器的扫描操作。
     * 不及时取消的话，指纹扫描器就会一直扫描，直至超时
     */
    private CancellationSignal cancellationSignal;

    private IBiometricPrompt biometricPrompt;

    public enum SupportResult {
        DEVICE_UNSUPPORTED,//设备不支持指纹识别
        SUPPORT_WITHOUT_DATA,//设备支持指纹识别但是没有指纹数据
        SUPPORT,//设备支持且有指纹数据
        SUPPORT_WITHOUT_KEYGUARD//设备支持但未处于安全保护中（你的设备必须是使用屏幕锁保护的，这个屏幕锁可以是password，PIN或者图案都行）
    }

    private static FingerManager getInstance() {
        if (fingerManager == null) {
            synchronized (FingerManager.class) {
                if (fingerManager == null) {
                    fingerManager = new FingerManager();
                }
            }
        }
        return fingerManager;
    }

    public static FingerManager getInstance(FingerManagerBuilder fingerManagerBuilder) {
        mFingerManagerBuilder = fingerManagerBuilder;
        return getInstance();
    }

    /**
     * 检查设别是否支持指纹识别
     *
     * @return
     */
    public static SupportResult checkSupport(Context context) {
       //指纹系统服务
        FingerprintManager fingerprintManager = context.getSystemService(FingerprintManager.class);
        //判断硬件是否支持指纹
        if (!fingerprintManager.isHardwareDetected()) {
            return SupportResult.DEVICE_UNSUPPORTED;
        }
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        //判断是否处于安全保护中（你的设备必须是使用屏幕锁保护的，这个屏幕锁可以是password，PIN或者图案都行）  判断 是否开启锁屏密码
        if (!keyguardManager.isKeyguardSecure()) {
            return SupportResult.SUPPORT_WITHOUT_KEYGUARD;
        }
        //设备支持且有指纹数据
        if (fingerprintManager.hasEnrolledFingerprints()) {
            return SupportResult.SUPPORT;
        }
        //设备支持指纹识别但是没有指纹数据
        return SupportResult.SUPPORT_WITHOUT_DATA;

    }

    /**
     * 开始监听指纹识别器
     */
    public void startListener(AppCompatActivity activity) {
        createImpl(activity, mFingerManagerBuilder.getFingerDialogApi23());
        startListener();
    }

    private void createImpl(AppCompatActivity activity, BaseFingerDialog fingerDialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //如果为9.0以上则使用9.0的api
            biometricPrompt = new BiometricPromptImpl28(activity, mFingerManagerBuilder);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果为6.0以上则使用6.0的api
            biometricPrompt = new BiometricPromptImpl23(activity, fingerDialog, mFingerManagerBuilder);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startListener() {
        //如果还没有开启指纹数据变化监听，但是指纹数据已经发生了改变，就清除指纹数据变化，重新生成指纹加密库key
        if (!SharePreferenceUtil.isEnableFingerDataChange(mFingerManagerBuilder.getApplication()) && hasFingerprintChang(mFingerManagerBuilder.getApplication())) {
            updateFingerData(mFingerManagerBuilder.getApplication());
        } else {
            CipherHelper.getInstance().createKey(mFingerManagerBuilder.getApplication(), false);
        }

        if (cancellationSignal == null) {
            cancellationSignal = new CancellationSignal();
        }

        if (cancellationSignal.isCanceled()) {
            //取消扫描，每次取消后需要重新创建新示例
            cancellationSignal = new CancellationSignal();
        }
        //开始指纹认证
        biometricPrompt.authenticate(cancellationSignal);
    }

    /**
     * 同步指纹数据,解除指纹数据变化问题
     *
     * @param context
     */
    public static void updateFingerData(Context context) {
        CipherHelper.getInstance().createKey(context, true);
        SharePreferenceUtil.saveEnableFingerDataChange(context, false);
        SharePreferenceUtil.saveFingerDataChange(context, false);
    }

    /**
     * 检查设备是否有指纹变化(例外：三星手机必须识别成功回调是才能检测到指纹库是否发生变化)
     *
     * @return
     */
    public static boolean hasFingerprintChang(Context context) {
        if (SharePreferenceUtil.isFingerDataChange(context)) {
            return true;
        }
        CipherHelper.getInstance().createKey(context, false);
        Cipher cipher = CipherHelper.getInstance().createCipher();
        return CipherHelper.getInstance().initCipher(cipher);
    }

    /**
     * 检查设备是否有支持指纹识别
     *
     * @return
     */
    public static boolean isHardwareDetected(Context context) {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        return fingerprintManagerCompat.isHardwareDetected();
    }

    /**
     * 检查设备是否有指纹数据
     *
     * @return
     */
    public static boolean hasFingerprintData(Context context) {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        return fingerprintManagerCompat.hasEnrolledFingerprints();
    }

    public static FingerManagerBuilder build() {
        return new FingerManagerBuilder();
    }
}
