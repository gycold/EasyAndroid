package com.easyandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;

import com.easytools.tools.DeviceUuidFactory;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    String s1 = "编码*8s8deqc3///\\\1dg,,,...5-_.=";
    String s2 = "timestamp=1488183939WangDaiJuHe2017!@#$";
    String s3 = "*";
    byte[] data = s1.getBytes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);

        UUID uuid = DeviceUuidFactory.getUUID();
        tv1.setText("通过设备信息获取uuid：" + uuid.toString() + "\n\n\n");

        DeviceUuidFactory factory = new DeviceUuidFactory(this);
        String androidId = factory.getDeviceUuid().toString();
        tv2.setText("通过DeviceUuidFactory获取androidId：" + androidId + "\n\n\n");

        final SharedPreferences prefs = getSharedPreferences("device_id.xml", 0);
        final String id = prefs.getString("device_id", null);
        tv3.setText("device_id.xml：" + id + "\n\n\n");

        String androidIds = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        tv4.setText("直接获取androidId：" + androidIds + "\n\n\n");


        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            String DEVICE_ID = tm.getDeviceId();
            tv5.setText("getDeviceId：" + DEVICE_ID + "\n\n\n");
        } catch (Exception e) {
            tv5.setText(e.getMessage());
        }

        try {
            String imei = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getImei();
            tv6.setText("imei：" + imei);
        } catch (Exception e) {
            tv6.setText(e.getMessage());
        }

    }
}