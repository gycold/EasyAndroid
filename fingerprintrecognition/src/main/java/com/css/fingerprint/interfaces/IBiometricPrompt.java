package com.css.fingerprint.interfaces;

import android.os.CancellationSignal;

import androidx.annotation.NonNull;

/**
 * 指纹认证接口
 */
public interface IBiometricPrompt {
    /**
     * 开启指纹识别
     * @param cancel  用于取消扫描器的扫描动作
     */
    void authenticate(@NonNull CancellationSignal cancel);
}
