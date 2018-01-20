package com.easyandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * package: com.easyandroid.BaseActivity
 * author: gyc
 * description:
 * time: create at 2017/2/13 10:52
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("******************Super", "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("******************Super", "onDestroy");
    }
}
