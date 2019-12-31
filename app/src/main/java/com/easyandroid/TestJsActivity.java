package com.easyandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

/**
 * package: com.easyandroid.TestJsActivity
 * author: gyc
 * description:
 * time: create at 2019/12/16 11:51
 */
public class TestJsActivity extends AppCompatActivity {

    TextView tvJs, tvShowmsg;
    BridgeWebView webview;

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

    }

    private void callJs(){
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
