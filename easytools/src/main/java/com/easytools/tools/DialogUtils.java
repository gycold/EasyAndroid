package com.easytools.tools;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easytools.R;

/**
 * package: com.easytools.tools.DialogUtils
 * author: gyc
 * description:
 * time: create at 2016/10/17 15:56
 */

public class DialogUtils {

    private DialogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(msg);
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, int title, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) {
            builder.setView(view);
        }
        if (title > 0) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, int titleResId, int msgResId) {
        String title = titleResId > 0 ? context.getResources().getString(titleResId) : null;
        String msg = msgResId > 0 ? context.getResources().getString(msgResId) : null;
        return dialogBuilder(context, title, msg);
    }

    /**
     * 显示一个有三个按钮“确认”、“取消”和“查看详情”的对话框
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title 标题
     * @param msg 消息
     * @param confirmButton 确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param centerButton 中间按钮
     * @param centerButtonClickListener 中间按钮点击监听器
     * @param cancelButton 取消按钮
     * @param cancelButtonClickListener 取消按钮点击监听器
     * @param onShowListener 显示监听器
     * @param cancelable 是否允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
     * @param onCancelListener 取消监听器
     * @param onDismissListener 销毁监听器
     * @return 对话框
     */
    public static AlertDialog showDialog(Context context, String title, String msg, String confirmButton,
            DialogInterface.OnClickListener confirmButtonClickListener, String centerButton,
            DialogInterface.OnClickListener centerButtonClickListener, String cancelButton,
            DialogInterface.OnClickListener cancelButtonClickListener,
            DialogInterface.OnShowListener onShowListener,
            boolean cancelable, DialogInterface.OnCancelListener onCancelListener,
            DialogInterface.OnDismissListener onDismissListener) {
        AlertDialog.Builder promptBuilder = dialogBuilder(context, title, msg);
        if (confirmButton != null) {
            promptBuilder.setPositiveButton(confirmButton,
                    confirmButtonClickListener);
        }
        if (centerButton != null) {
            promptBuilder.setNeutralButton(centerButton,
                    centerButtonClickListener);
        }
        if (cancelButton != null) {
            promptBuilder.setNegativeButton(cancelButton,
                    cancelButtonClickListener);
        }
        promptBuilder.setCancelable(cancelable);
        if (cancelable) {
            promptBuilder.setOnCancelListener(onCancelListener);
        }

        AlertDialog alertDialog = promptBuilder.create();
        if (!(context instanceof Activity)) {
            alertDialog.getWindow()
                    .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        alertDialog.setOnDismissListener(onDismissListener);
        alertDialog.setOnShowListener(onShowListener);
        alertDialog.show();
        return alertDialog;
    }

    /**
     * 显示一个有两个按钮“确认”和“取消”的对话框
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title 标题
     * @param message 消息
     * @param confirmButton 确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param cancelButton 取消按钮
     * @param cancelButtonClickListener 取消按钮点击监听器
     * @return 对话框
     */
    public static AlertDialog showDialog(Context context, String title, String message, String confirmButton, DialogInterface.OnClickListener confirmButtonClickListener, String cancelButton, DialogInterface.OnClickListener cancelButtonClickListener) {
        return showDialog(context, title, message, confirmButton,
                confirmButtonClickListener, null, null, cancelButton,
                cancelButtonClickListener, null, true, null, null);
    }

    /**
     * 显示一个有一个确认按钮的提示框
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param message 提示的消息
     * @param confirmButton 确定按钮的名字
     */
    public static AlertDialog showDialog(Context context, String message, String confirmButton) {
        return showDialog(context, null, message, confirmButton, null, null,
                null, null, null, null, true, null, null);
    }

    /**
     * 显示一个按钮的提示框，按钮默认“OK”
     *
     * @param context 上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param message 提示的消息
     */
    public static AlertDialog showDialog(Context context, String message) {
        return showDialog(context, null, message, "OK", null, null, null, null,
                null, null, true, null, null);
    }

    /**
     * 显示一个带列表的对话框
     * @param context 上下文
     * @param title 标题
     * @param items 列表内容
     * @param listener
     */
    public static void showDialogWithItem(Context context, String title, String[] items,
                                          DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context).setTitle(title).setItems(items, listener).show();
    }

    /**
     * 创建网络请求的Dialog
     * @param context
     * @param msg
     * @return
     */
    public static Dialog showLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        TextView tvMsg = (TextView) v.findViewById(R.id.tv_dialog_text);
        if (StringUtils.isEmpty(msg)) {
        } else {
            tvMsg.setText(msg);
        }
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context,R.animator.loading);
        animator.setTarget(spaceshipImage);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }

}
