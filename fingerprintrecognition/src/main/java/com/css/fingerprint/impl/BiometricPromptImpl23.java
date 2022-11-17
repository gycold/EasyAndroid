package com.css.fingerprint.impl;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.css.fingerprint.CipherHelper;
import com.css.fingerprint.FingerManagerBuilder;
import com.css.fingerprint.SharePreferenceUtil;
import com.css.fingerprint.dialog.BaseFingerDialog;
import com.css.fingerprint.dialog.DefaultFingerDialog;
import com.css.fingerprint.interfaces.IBiometricPrompt;
import com.css.fingerprint.interfaces.IFingerCallback;

import javax.crypto.Cipher;

/**
 * Android 6.0及以上指纹认证实现
 *
 * 指纹识别api的简介：https://blog.csdn.net/hailong0529/article/details/95406183
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BiometricPromptImpl23 implements IBiometricPrompt {

    private AppCompatActivity mActivity;

    private Cipher mCipher;

    private boolean mSelfCanceled;//用户主动取消指纹识别

    private BaseFingerDialog mFingerDialog;

    private IFingerCallback mFingerCallback;

    private static final String SECRET_MESSAGE = "Very secret message";

    public BiometricPromptImpl23(AppCompatActivity activity, BaseFingerDialog fingerDialog,
                                 FingerManagerBuilder fingerManagerController) {
        this.mActivity = activity;
        this.mCipher = CipherHelper.getInstance().createCipher();
        this.mFingerCallback = fingerManagerController.getFingerCallback();
        this.mFingerDialog = fingerDialog == null ? DefaultFingerDialog.newInstance(fingerManagerController) : fingerDialog;
    }

    /**
     * 开始指纹认证
     *
     * @param cancel  用来取消指纹扫描器的扫描操作。比如在用户点击识别框上的“取消”按钮或者“密码验证”按钮后，就要及时取消扫描器的扫描操作
     */
    @Override
    public void authenticate(@NonNull final CancellationSignal cancel) {
        mSelfCanceled = false;
        //检测指纹库是否发生变化
        boolean exceptionState = CipherHelper.getInstance().initCipher(mCipher);
        boolean flag = SharePreferenceUtil.isEnableFingerDataChange(mActivity) && (exceptionState || SharePreferenceUtil.isFingerDataChange(mActivity));
        if (flag) {
            SharePreferenceUtil.saveFingerDataChange(mActivity, true);
            mFingerCallback.onChange();
            return;
        }

        mFingerDialog.setOnDismissListener(new BaseFingerDialog.IDismissListener() {

            @Override
            public void onDismiss() {
                mSelfCanceled = !cancel.isCanceled();
                if (mSelfCanceled) {
                    cancel.cancel();
                    //如果使用的是默认弹窗,就使用cancel回调,否则交给开发者自行处理
                    if (mFingerDialog.getClass() == DefaultFingerDialog.class) {
                        mFingerCallback.onCancel();
                    }
                }
            }
        });
        //Android 9.0以下显示自定义的指纹认证对话框
        if (!mFingerDialog.isAdded()) {
            mFingerDialog.show(mActivity.getSupportFragmentManager(), mFingerDialog.getClass().getSimpleName());
        }
        //开始指纹认证
        FingerprintManager fingerprintManager = (FingerprintManager) mActivity.getSystemService(FingerprintManager.class);
       //FingerprintManager.CryptoObject 指纹加密
        // flags 可选标志，暂无用处，传 0 即可。只用于 Android 6.0
        fingerprintManager.authenticate(new FingerprintManager.CryptoObject(mCipher), cancel, 0,
                //指纹识别结果的回调接口
                new FingerprintManager.AuthenticationCallback() {

                    @Override
                    public void onAuthenticationError(int errMsgId, CharSequence errString) {
                        super.onAuthenticationError(errMsgId, errString);
                        Toast.makeText(mActivity, errString, Toast.LENGTH_SHORT).show();
                        //指纹认证失败五次会报错，会停留几秒钟后才可以重试
                        cancel.cancel();
                        if (!mSelfCanceled) {
                            mFingerDialog.onError(errString.toString());
                            mFingerCallback.onError(errString.toString());
                        }
                    }

                    @Override
                    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                        super.onAuthenticationHelp(helpMsgId, helpString);
                        mFingerDialog.onHelp(helpString.toString());
                        mFingerCallback.onHelp(helpString.toString());
                    }

                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
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
                                    //获取加密数据
                                    cipher.doFinal(SECRET_MESSAGE.getBytes());
                                }

                                cancel.cancel();
                                mFingerDialog.onSucceed();
                                mFingerCallback.onSucceed();
                                //开启监听设备指纹数据变化
                                SharePreferenceUtil.saveEnableFingerDataChange(mActivity, true);
                            } catch (Exception e) {
                                e.printStackTrace();
                                cancel.cancel();
                                mFingerDialog.onError("");

                                SharePreferenceUtil.saveFingerDataChange(mActivity, true);
                                mFingerCallback.onChange();
                            }
                        }
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        //指纹不匹配
                        mFingerDialog.onFailed();
                        mFingerCallback.onFailed();
                    }
                }, null);
        //Handler handler这个参数用于 Android 6.0，是 @Nullable 的，作用是告诉系统使用这个 Handler
        // 的 Looper 处理指纹识别的 Message。默认就是交给主线程的 Looper 处理，传 null 即可

    }
}
