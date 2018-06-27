package com.easyandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.easytools.tools.DisplayUtil;


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
        Log.d("****getRealScreenHeightPixels", DisplayUtil.getRealScreenHeightPixels(this) + "");
        Log.d("****getRealScreenWidthPixels", DisplayUtil.getRealScreenWidthPixels(this) + "");
        Log.d("****getScreenWidthPixels", DisplayUtil.getScreenWidthPixels(this) + "");
        Log.d("****getScreenHeightPixels", DisplayUtil.getScreenHeightPixels(this) + "");
        Log.d("****getNavigationBarHeight", DisplayUtil.getNavigationBarHeight(this) + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}