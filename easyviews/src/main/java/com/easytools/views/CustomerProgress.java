package com.easytools.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.easytools.views.animation.JumpingBeans;

/**
 * package: com.easytools.views.CustomerProgress
 * author: gyc
 * description:加载中dialog
 * time: create at 2017/5/15 23:13
 */

public class CustomerProgress extends ProgressDialog {
    private String mMsg;
    private TextView mTvMsg;
    private JumpingBeans jumpingBeans;
    private ProgressWheel progressWheel;

    public CustomerProgress(Context context, String msg) {
        super(context);
        mMsg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        mTvMsg = (TextView) findViewById(R.id.tv_pro);
        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        if (!mMsg.equals("") && mMsg != null) {
            mTvMsg.setText(mMsg);
        }
        jumpingBeans = new JumpingBeans.Builder().appendJumpingDots(
                mTvMsg).build();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        jumpingBeans.stopJumping();
        progressWheel.stopSpinning();
    }
}
