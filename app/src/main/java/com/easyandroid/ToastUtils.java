package com.easyandroid;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ToastUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class ToastUtils {
    private static Toast toast = null; //Toast的对象！

    public static void showToast(Context context, String msg) {
        if (toast == null) {
            View toastview = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
            TextView text = toastview.findViewById(R.id.tv_content);
            text.setText(msg);
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(toastview);
        } else {
            View toastview = toast.getView();
            TextView text = toastview.findViewById(R.id.tv_content);
            text.setText(msg);
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