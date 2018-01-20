package com.easytools.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * package: com.easytools.tools.ToastUtil
 * author: gyc
 * description:Toast相关工具类
 * time: create at 2016/10/17 15:56
 */

public class ToastUtil {

    private static Context mContext;
    private static ToastUtil mInstance;
    private Toast mToast;

    public ToastUtil( Context context){
        mContext=context;
    }

    public  void showToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, mContext.getResources().getString(resId), Toast
                    .LENGTH_LONG);
        } else {
            mToast.setText(mContext.getResources().getString(resId));
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

}
