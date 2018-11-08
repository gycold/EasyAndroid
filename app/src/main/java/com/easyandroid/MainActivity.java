package com.easyandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.easytools.tools.EncodeUtils;
import com.easytools.tools.EncryptUtils;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView tv1, tv2, tv3;
    String s1 = "编码*8s8deqc3///\\\1dg,,,...5-_.=";
    String s2 = "timestamp=1488183939WangDaiJuHe2017!@#$";
    String s3 = "*";
    byte[] data = s1.getBytes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);

        tv1.setText(EncodeUtils.urlEncode(s1));
        tv2.setText(EncodeUtils.urlEncode(s2));
        tv3.setText(EncodeUtils.urlEncode(s3));

        String s = EncodeUtils.urlEncodeWithRFC3986(s2);
        System.out.println(s);
        System.out.println(EncryptUtils.encryptSHA1ToString(s));
    }
}