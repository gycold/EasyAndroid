package com.css.fingerprint.impl;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.css.fingerprint.CipherHelper;
import com.css.fingerprint.FingerManagerBuilder;
import com.css.fingerprint.SharePreferenceUtil;
import com.css.fingerprint.interfaces.IBiometricPrompt;
import com.css.fingerprint.interfaces.IFingerCallback;

import javax.crypto.Cipher;

/**
 * Android 9.0及以上的指纹认证实现，相比Android 6.0 指纹识别框可以自定义，而9.0不允许开发者自定义指纹识别框
 */
@RequiresApi(Build.VERSION_CODES.P)
public class BiometricPromptImpl28 implements IBiometricPrompt {

    private AppCompatActivity mActivity;
    private CancellationSignal mCancellationSignal;
    private boolean mSelfCanceled;//用户主动取消指纹识别

    /**
     * 此类为加密和解密提供密码功能
     */
    private Cipher cipher;
    private IFingerCallback mFingerCallback;

    private BiometricPrompt mBiometricPrompt;
    private static final String SECRET_MESSAGE = "Very secret message";

    @RequiresApi(Build.VERSION_CODES.P)
    public BiometricPromptImpl28(AppCompatActivity activity, FingerManagerBuilder fingerManagerController) {
        this.mActivity = activity;
        this.cipher = CipherHelper.getInstance().createCipher();
        this.mFingerCallback = fingerManagerController.getFingerCallback();
        //Android 9.0及以上显示系统的指纹认证对话框
        this.mBiometricPrompt = new BiometricPrompt
                .Builder(activity)
                .setTitle(fingerManagerController.getTitle())
                .setDescription(fingerManagerController.getDes())
                .setNegativeButton(fingerManagerController.getNegativeText(),
                        activity.getMainExecutor(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSelfCanceled = true;
                                mFingerCallback.onCancel();
                                mCancellationSignal.cancel();
                            }
                        })
                .build();
    }

    /**
     * 开始指纹认证
     *
     * @param cancel
     */
    @RequiresApi(Build.VERSION_CODES.P)
    @Override
    public void authenticate(@Nullable final CancellationSignal cancel) {
        mSelfCanceled = false;
        mCancellationSignal = cancel;
        //检测指纹库是否发生变化
        boolean exceptionState = CipherHelper.getInstance().initCipher(cipher);
        boolean flag = SharePreferenceUtil.isEnableFingerDataChange(mActivity) && (exceptionState || SharePreferenceUtil.isFingerDataChange(mActivity));
        if (flag) {
            SharePreferenceUtil.saveFingerDataChange(mActivity, true);
            mFingerCallback.onChange();
            return;
        }
        //开始指纹认证
        mBiometricPrompt.authenticate(new BiometricPrompt.CryptoObject(cipher),
                cancel, mActivity.getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(mActivity, errString, Toast.LENGTH_SHORT).show();
                        //指纹认证失败五次会报错，会停留几秒钟后才可以重试
                        cancel.cancel();
                        if (!mSelfCanceled) {
                            mFingerCallback.onError(errString.toString());
                        }
                    }

                    @Override
                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                        super.onAuthenticationHelp(helpCode, helpString);
                        mFingerCallback.onHelp(helpString.toString());
                    }

                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Cipher cipher = result.getCryptoObject().getCipher();
                        if (cipher != null) {
                            try {
                                /*
                                 * 用于检测三星手机指纹库变化，
                                 * 三星手机指纹库发生变化后前面的initCipher检测不到KeyPermanentlyInvalidatedException
                                 * 但是cipher.doFinal(SECRET_MESSAGE.getBytes())会抛出异常
                                 * 因此以此监听三星手机的指纹库变化
                                 */
                                //针对三星手机，开启了监听才去检测设备指纹库变化
                                if (SharePreferenceUtil.isEnableFingerDataChange(mActivity)) {
                                    ////获取加密数据
                                    cipher.doFinal(SECRET_MESSAGE.getBytes());
                                }
                                cancel.cancel();
                                mFingerCallback.onSucceed();
                                //开启监听设备指纹数据变化
                                SharePreferenceUtil.saveEnableFingerDataChange(mActivity, true);
                            } catch (Exception e) {
                                e.printStackTrace();
                                cancel.cancel();

                                SharePreferenceUtil.saveFingerDataChange(mActivity, true);
                                mFingerCallback.onChange();

                            }
                        }

                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        //指纹不匹配
                        mFingerCallback.onFailed();
                    }
                });
    }

}
