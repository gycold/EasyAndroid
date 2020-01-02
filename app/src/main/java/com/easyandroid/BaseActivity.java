package com.easyandroid;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
