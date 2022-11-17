package com.css.fingerprint.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.css.fingerprint.R;


/**
 *  CommonTipDialog
 */
public class CommonTipDialog extends Dialog {

    private Context mContext;
    private TextView tv_kindly_reminder;
    private TextView tv_tip_content;
    private TextView tv_cancel;
    private TextView tv_confirm;
    private LinearLayout ll_double_button_container;
    private TextView tv_single_button_confirm;


    public CommonTipDialog(Context context) {
        super(context, R.style.TransparentDialogStyle);
        setContentView(R.layout.common_tip_dialog);
        setCanceledOnTouchOutside(false);
        this.mContext = context;
        tv_kindly_reminder = (TextView) findViewById(R.id.tv_kindly_reminder);
        tv_tip_content = (TextView) findViewById(R.id.tv_tip_content);
        tv_tip_content.setMovementMethod(new ScrollingMovementMethod());
        ll_double_button_container = (LinearLayout) findViewById(R.id.ll_double_button_container);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        tv_single_button_confirm = (TextView) findViewById(R.id.tv_single_button_confirm);
        windowAnim();
    }

    public void setTitle(String title) {
        tv_kindly_reminder.setText(title);
    }

    public void setTitleTextSize(float size) {
        tv_kindly_reminder.setTextSize(size);
    }

    public void setTitleTextColor(int color) {
        tv_kindly_reminder.setTextColor(color);
    }

    /**
     * @description 提示文本内容设置
     * @author HaganWu
     * @date 2019/1/29-13:57
     */
    public void setContentText(String content) {
        tv_tip_content.setText(content);
    }

    public void setContentHtmlText(String content) {
        tv_tip_content.setText(Html.fromHtml(content));
    }

    public void setTitleBold() {
        TextPaint tp = tv_kindly_reminder.getPaint();
        tp.setFakeBoldText(true);
    }

    public void setContentTextSize(float size) {
        tv_tip_content.setTextSize(size);
    }

    public void setContentTextColor(int color) {
        tv_tip_content.setTextColor(color);
    }

    public void setCancelTextColor(int color) {
        tv_cancel.setTextColor(color);
    }

    public void setSingleButton(boolean singleButton) {
        if (singleButton) {
            ll_double_button_container.setVisibility(View.GONE);
            tv_single_button_confirm.setVisibility(View.VISIBLE);
        } else {
            ll_double_button_container.setVisibility(View.VISIBLE);
            tv_single_button_confirm.setVisibility(View.GONE);
        }
    }

    public void setSingleButtonText(String s) {
        tv_single_button_confirm.setText(s);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.dismiss();
    }

    public void setOnDialogButtonsClickListener(final OnDialogButtonsClickListener onDialogButtonsClickListener) {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogButtonsClickListener.onCancelClick(v);
                if (CommonTipDialog.this.isShowing()) {
                    CommonTipDialog.this.dismiss();
                }

            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogButtonsClickListener.onConfirmClick(v);
                if (CommonTipDialog.this.isShowing()) {
                    CommonTipDialog.this.dismiss();
                }
            }
        });

    }

    public void setOnSingleConfirmButtonClickListener(final OnDialogSingleConfirmButtonClickListener onDialogSingleConfirmButtonClickListener) {
        tv_single_button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogSingleConfirmButtonClickListener.onConfirmClick(v);
                if (CommonTipDialog.this.isShowing()) {
                    CommonTipDialog.this.dismiss();
                }
            }
        });

    }


    /**
     * @author HaganWu
     * @description 提示弹窗按钮点击监听
     * @date 2019/1/29-13:57
     */
    public interface OnDialogButtonsClickListener {

        /**
         * @description 取消按钮点击回调
         * @author HaganWu
         * @date 2019/1/29-13:57
         */
        void onCancelClick(View v);

        /**
         * @description 确认按钮点击回调
         * @author HaganWu
         * @date 2019/1/29-13:58
         */
        void onConfirmClick(View v);
    }

    /**
     * @author HaganWu
     * @description 单按钮确定点击回调监听
     * @date 2019/1/29-13:58
     */
    public interface OnDialogSingleConfirmButtonClickListener {

        /**
         * @description 单按钮确定点击回调
         * @author HaganWu
         * @date 2019/1/29-13:58
         */
        void onConfirmClick(View v);
    }

    //设置窗口显示
    public void windowAnim() {
        Window window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setConfirmButtonTextColor(int colorId) {
        if (tv_confirm != null) {
            tv_confirm.setTextColor(colorId);
        }
        if (tv_single_button_confirm != null) {
            tv_single_button_confirm.setTextColor(colorId);
        }
    }

    public void setButtonsText(String leftText, String rightText) {
        if (tv_cancel != null) {
            tv_cancel.setText(leftText);
        }
        if (tv_confirm != null) {
            tv_confirm.setText(rightText);
        }
    }
}

