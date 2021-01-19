package com.easyandroid;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.easytools.tools.PathUtils;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * package: com.easyandroid.TestJsActivity
 * author: gyc
 * description:
 * time: create at 2019/12/16 11:51
 */
public class TestJsActivity extends AppCompatActivity {

    TextView tvJs, tvShowmsg;
    BridgeWebView webview;

    FileWriter fw = null;
    BufferedWriter bw = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testjs);

        tvJs = (TextView) findViewById(R.id.tv_androidcalljs);
        tvShowmsg = (TextView) findViewById(R.id.tv_showmsg);

        webview = (BridgeWebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //与js交互必须设置
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/html.html");

        callJs();
        registerInJs();


        int type = 0;
        String logPath = PathUtils.getExternalStoragePath() + File.separator + "公文下载路径WIFI版.txt";
        try {
            fw = new FileWriter(logPath, true);
            bw = new BufferedWriter(fw);

            for (int i = 0; i < 10; i++) {
                String url = "";
                if (type == 0) {//批件
                    url = "http://www.baidu.com" + i;
                }
                bw.write(url + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void callJs() {
        tvJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.callHandler("functionInJs", "Android传递给js的数据", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        tvShowmsg.setText(data);
                    }
                });
            }
        });
    }

    private void registerInJs() {
        webview.registerHandler("functionInAndroid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                tvShowmsg.setText("js调用了Android");
                //返回给html
                function.onCallBack("Android回传给js的数据");
            }
        });
    }
}
