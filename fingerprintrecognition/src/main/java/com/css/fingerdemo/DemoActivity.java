package com.css.fingerdemo;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.css.fingerprint.FingerManager;
import com.css.fingerprint.R;
import com.css.fingerprint.callback.SimpleFingerCallback;
import com.css.fingerprint.dialog.CommonTipDialog;
import com.css.fingerprint.util.PhoneInfoCheck;

import androidx.appcompat.app.AppCompatActivity;

public class DemoActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();

            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    FingerManager.updateFingerData(DemoActivity.this);
                    showToast("已同步指纹数据,解除指纹数据变化");
                }
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (FingerManager.hasFingerprintChang(DemoActivity.this)) {
                        showToast("指纹数据已经发生变化");
                    } else {
                        showToast("指纹数据没有发生变化");
                    }
                }
            }
        });
    }

    /**
     * 验证指纹
     */
    private void check() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (FingerManager.checkSupport(DemoActivity.this)) {
                case DEVICE_UNSUPPORTED:
                    showToast("您的设备不支持指纹");
                    break;
                case SUPPORT_WITHOUT_KEYGUARD:
                    //设备支持但未处于安全保护中（你的设备必须是使用屏幕锁保护的，这个屏幕锁可以是password，PIN或者图案都行）
                    showOpenSettingDialog("您还未录屏幕锁保护，是否现在开启?");
                    break;
                case SUPPORT_WITHOUT_DATA:
                    showOpenSettingDialog("您还未录入指纹信息，是否现在录入?");
                    break;
                case SUPPORT:
                    FingerManager.build().setApplication(getApplication())
                            .setTitle("指纹验证")
                            .setDes("请按下指纹")
                            .setNegativeText("取消")
                            //                                    .setFingerDialogApi23(new MyFingerDialog())//如果你需要自定义android P 以下系统弹窗就设置,注意需要继承BaseFingerDialog，不设置会使用默认弹窗
                            .setFingerCallback(new SimpleFingerCallback() {
                                @Override
                                public void onSucceed() {

                                    showToast("验证成功");

                                }

                                @Override
                                public void onFailed() {
                                    showToast("指纹无法识别");
                                }

                                @Override
                                public void onChange() {
                                    FingerManager.updateFingerData(DemoActivity.this);
                                    check();
                                }


                            })
                            .create()
                            .startListener(DemoActivity.this);
                    break;
                default:
            }
        }
    }

    private void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }


    /**
     * 引导指纹录入
     */
    public void startFingerprint() {
        final String BRAND = android.os.Build.BRAND;
        Log.d(TAG, "BRAND:" + BRAND);
        PhoneInfoCheck.getInstance(this, BRAND).startFingerprint();
    }

    /**
     * 打开提示去录入指纹的dialog
     */
    private void showOpenSettingDialog(String msg) {
        CommonTipDialog openFingerprintTipDialog = new CommonTipDialog(this);
        openFingerprintTipDialog.setSingleButton(false);
        openFingerprintTipDialog.setContentText("您还未录入指纹信息，是否现在录入?");
        openFingerprintTipDialog.setOnDialogButtonsClickListener(new CommonTipDialog.OnDialogButtonsClickListener() {
            @Override
            public void onCancelClick(View v) {

            }

            @Override
            public void onConfirmClick(View v) {
                startFingerprint();
            }
        });
        openFingerprintTipDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}