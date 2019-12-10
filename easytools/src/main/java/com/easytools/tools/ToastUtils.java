package com.easytools.tools;

import android.widget.Toast;

/**
 * package: com.easytools.tools.ToastUtils
 * author: gyc
 * description:Toast简单工具类
 * time: create at 2016/10/17 15:56
 */

public class ToastUtils {

    private static Toast toast = null;

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(Utils.getApp(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        if (toast.getView().isShown()) return;
        toast.show();
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
