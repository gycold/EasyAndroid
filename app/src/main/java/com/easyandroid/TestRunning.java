package com.easyandroid;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;
import com.jaredrummler.android.processes.models.Statm;

import java.util.List;

/**
 * package: com.easyandroid.TestRunning
 * author: gyc
 * description:
 * time: create at 2019/12/12 10:23
 */
public class TestRunning extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testrunning);

        TextView tv = findViewById(R.id.tv);

        StringBuilder sb = new StringBuilder();

        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();

        for (AndroidAppProcess process : processes) {
            // Get some information about the process
            String processName = process.name;

            try {
                Stat stat = process.stat();
                int pid = stat.getPid();
                int parentProcessId = stat.ppid();
                long startTime = stat.stime();
                int policy = stat.policy();
                char state = stat.state();

                Statm statm = process.statm();
                long totalSizeOfProcess = statm.getSize();
                long residentSetSize = statm.getResidentSetSize();

                PackageManager pm = getPackageManager();
                PackageInfo packageInfo = process.getPackageInfo(this, 0);
                String appName = packageInfo.applicationInfo.loadLabel(pm).toString();

                sb.append("processName:" + processName + "," + "appName:" + appName + "\n");

            } catch (Exception e) {
            }

            tv.setText(sb.toString());
        }
    }
}
