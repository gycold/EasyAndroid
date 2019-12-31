package com.easyandroid;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.easytools.tools.DeviceUuidFactory;
import com.easytools.tools.RandomUtils;

import java.util.List;
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

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(MainActivity.this, "" + RandomUtils.getRandom(100));
            }
        });

        getTopApp(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private String getForegroundApp(Context context) {
        /**
         * 此功能需要在设置的（允许查看使用情况的应用）里打开，在很多手机设置里没有发现这一入口。
         需要在代码里打开，注意要判断一下系统版本
         */
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//        }
        boolean isInit = true;
        UsageStatsManager usageStatsManager =
                (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long ts = System.currentTimeMillis();
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, ts);
        UsageEvents usageEvents = usageStatsManager.queryEvents(isInit ? 0 : ts-5000, ts);
        if (usageEvents == null) {
            return null;
        }

        UsageEvents.Event event = new UsageEvents.Event();
        UsageEvents.Event lastEvent = null;
        while (usageEvents.getNextEvent(event)) {
            // if from notification bar, class name will be null
            if (event.getPackageName() == null || event.getClassName() == null) {
                continue;
            }

            if (lastEvent == null || lastEvent.getTimeStamp() < event.getTimeStamp()) {
                lastEvent = event;
            }
        }

        if (lastEvent == null) {
            return null;
        }
        return lastEvent.getPackageName();
    }


    private void getTopApp(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                //获取60秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
                Log.i("Running app number", "Running app number in last 60 seconds : " + stats.size());

                String topActivity = "";

                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    topActivity = stats.get(j).getPackageName();
                }
                Log.i("top running app", "top running app is : "+topActivity);
            }
        }
    }

    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS) {
            if (!hasPermission()) {
                //若用户未开启权限，则引导用户开启“Apps with usage access”权限
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
            }
        }
    }

    private boolean isAppOnForeground(String packageName) {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}