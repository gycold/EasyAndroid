package com.easytools.views.alertview;

import android.view.Gravity;

import com.easytools.views.R;

/**
 * package: com.easytools.views.alertview.AlertAnimateUtil
 * author: gyc
 * description:底部弹出dialog动画
 * time: create at 2017/3/6 22:29
 */

public class AlertAnimateUtil {

    private static final int INVALID = -1;
    /**
     * Get default animation resource when not defined by the user
     *
     * @param gravity       the gravity of the dialog
     * @param isInAnimation determine if is in or out animation. true when is is
     * @return the id of the animation resource
     */
    static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.slide_in_bottom : R.anim.slide_out_bottom;
            case Gravity.CENTER:
                return isInAnimation ? R.anim.fade_in_center : R.anim.fade_out_center;
        }
        return INVALID;
    }
}
