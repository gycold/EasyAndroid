package com.easyandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * package: com.easyandroid.KeyBoardActivity
 * author: gyc
 * description:
 * time: create at 2018/10/10 11:43
 */
public class KeyBoardActivity extends AppCompatActivity {
    EditText edittext1;
    PopupKeyboardUtil smallKeyboardUtil;
    private View viewContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        edittext1 = findViewById(R.id.edittext1);

        smallKeyboardUtil = new PopupKeyboardUtil(self());
        smallKeyboardUtil.attachTo(edittext1, false);
    }

    public void onClickView(View view) {
        if (view.getId() == R.id.btn1)
            smallKeyboardUtil.showSoftKeyboard();
        if (view.getId() == R.id.btn2)
            smallKeyboardUtil.hideSoftKeyboard();

    }

    private Activity self() {
        return this;
    }
}
