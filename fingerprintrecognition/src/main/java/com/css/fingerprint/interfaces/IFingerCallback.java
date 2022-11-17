package com.css.fingerprint.interfaces;

/**
 * 指纹识别状态接口
 */
public interface IFingerCallback {

    void onError(String error);

    void onHelp(String help);

    void onSucceed();

    void onFailed();

    void onCancel();
    //监听指纹变化
    void onChange();
}
