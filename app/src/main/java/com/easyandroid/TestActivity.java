package com.easyandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.easytools.tools.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.easyandroid.R.id.btn;


/**
 * package: com.easyandroid.TestActivity
 * author: gyc
 * description:
 * time: create at 2017/1/8 21:06
 */

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        Log.d("******************Child", "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("******************Child", "onDestroy");
    }

}