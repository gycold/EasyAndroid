package com.easytools.views.basepopup;

import android.view.View;

/**
 * package: com.easytools.views.basepopup.BasePopup
 * author: gyc
 * description:定义接口，分别得到popup的view和得到需要播放动画的view
 * time: create at 2017/5/15 23:32
 */

public interface BasePopup {
    View onCreatePopupView();
    View initAnimaView();
}
