package com.css.fingerprint.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

/**
 * Android 6.0到9.0之间使用的指纹对话框基类，可以继承该类自定义实现自己的指纹识别对话框
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public abstract class BaseFingerDialog extends DialogFragment {

    private IDismissListener mDismissListener;

    public BaseFingerDialog() {
        super();
        setCancelable(false);
    }

    /**
     * 监听返回键
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                } else {
                    return false;
                }
            }
        });
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        dismissAllowingStateLoss();
    }

    public void setOnDismissListener(IDismissListener dismissListener) {
        this.mDismissListener = dismissListener;
    }

    public interface IDismissListener {
        void onDismiss();
    }

    /**
     * 指纹识别重试多次彻底失败,不能继续验证
     */
    public abstract void onError(String error);

    /**
     * 指纹识别不对,会提示,手指不要大范围移动等信息,可以继续验证
     */
    public abstract void onHelp(String help);

    /**
     * 当识别成功时回调
     */
    public abstract void onSucceed();

    /**
     * 当识别的手指没有注册不匹配时回调,但是可以继续验证
     */
    public abstract void onFailed();

}
