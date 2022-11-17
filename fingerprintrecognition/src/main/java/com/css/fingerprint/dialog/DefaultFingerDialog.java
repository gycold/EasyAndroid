package com.css.fingerprint.dialog;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.css.fingerprint.FingerManagerBuilder;
import com.css.fingerprint.R;

/**
 * Android 6.0到9.0之间如果用户未创建自己的指纹对话框，则使用该默认指纹对话框
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class DefaultFingerDialog extends BaseFingerDialog {

    private static final String TITLE = "title";

    private static final String DES = "des";

    private static final String NEGATIVE_TEXT = "negativeText";

    private TextView titleTv;

    private TextView desTv;

    private TextView cancelTv;

    private ObjectAnimator animator;

    public static DefaultFingerDialog newInstance(FingerManagerBuilder fingerManagerBuilder) {
        DefaultFingerDialog defaultFingerDialog = new DefaultFingerDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, fingerManagerBuilder.getTitle());
        bundle.putString(DES, fingerManagerBuilder.getDes());
        bundle.putString(NEGATIVE_TEXT, fingerManagerBuilder.getNegativeText());
        defaultFingerDialog.setArguments(bundle);
        return defaultFingerDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_finger, container, true);

        titleTv = view.findViewById(R.id.finger_dialog_title_tv);
        desTv = view.findViewById(R.id.finger_dialog_des_tv);
        cancelTv = view.findViewById(R.id.finger_dialog_cancel_tv);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (getArguments() != null) {
            titleTv.setText(getArguments().getString(TITLE));
            desTv.setText(getArguments().getString(DES));
            cancelTv.setText(getArguments().getString(NEGATIVE_TEXT));
        }

        animator = ObjectAnimator.ofFloat(desTv, View.TRANSLATION_X, 20, -20);
        animator.setRepeatCount(1);
        animator.setDuration(500);

        return view;
    }

    @Override
    public void onError(String error) {
        dismissAllowingStateLoss();
    }

    @Override
    public void onHelp(String help) {
        if (!TextUtils.isEmpty(help)) {
            desTv.setText(help.trim());
            if (!animator.isRunning()) {
                animator.start();
            }
        }
    }

    @Override
    public void onSucceed() {
        dismiss();
    }

    @Override
    public void onFailed() {
        titleTv.setText(getString(R.string.retry));
        desTv.setText(getString(R.string.change_finger));
        if (!animator.isRunning()) {
            animator.start();
        }
    }

}
