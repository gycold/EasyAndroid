package com.easytools.tools;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.EditText;

import java.util.Locale;

import androidx.annotation.LayoutRes;

/**
 * package: com.easytools.tools.ViewUtils
 * author: gyc
 * description:视图工具
 * <p>
 * forceGetViewSize : 在 onCreate 中获取视图的尺寸
 * measureView      : 测量视图尺寸
 * getMeasuredWidth : 获取测量视图宽度
 * getMeasuredHeight: 获取测量视图高度
 * <p>
 * time: create at 2017/1/19 10:18
 */

public class ViewUtils {

    public static final void setEditTextSelectionToEnd(EditText editText) {
        Editable editable = editText.getEditableText();
        Selection.setSelection(editable, editable.toString().length());
    }

    /**
     * Force get the size of view.
     * <p>e.g.</p>
     * <pre>
     * SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
     *     Override
     *     public void onGetSize(final View view) {
     *         view.getWidth();
     *     }
     * });
     * </pre>
     *
     * @param view     The view.
     * @param listener The get size listener.
     */
    public static void forceGetViewSize(final View view, final onGetSizeListener listener) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onGetSize(view);
                }
            }
        });
    }

    /**
     * Return the width of view.
     *
     * @param view The view.
     * @return the width of view
     */
    public static int getMeasuredWidth(final View view) {
        return measureView(view)[0];
    }

    /**
     * Return the height of view.
     *
     * @param view The view.
     * @return the height of view
     */
    public static int getMeasuredHeight(final View view) {
        return measureView(view)[1];
    }

    /**
     * Measure the view.
     *
     * @param view The view.
     * @return arr[0]: view's width, arr[1]: view's height
     */
    public static int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    /**
     * 设置控件所在的位置X，并且不改变宽高，X为绝对位置，此时Y可能归0
     *
     * @param view
     * @param x
     */
    public static void setLayoutX(View view, int x) {
        MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, margin.topMargin, x + margin.width, margin.bottomMargin);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置控件所在的位置Y，并且不改变宽高，Y为绝对位置，此时X可能归0
     *
     * @param view
     * @param y
     */
    public static void setLayoutY(View view, int y) {
        MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(margin.leftMargin, y, margin.rightMargin, y + margin.height);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置控件所在的位置YY，并且不改变宽高，XY为绝对位置
     *
     * @param view
     * @param x
     * @param y
     */
    public static void setLayout(View view, int x, int y) {
        MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, x + margin.width, y + margin.height);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /**
     * Return whether horizontal layout direction of views are from Right to Left.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLayoutRtl() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Locale primaryLocale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                primaryLocale = Utils.getApp().getResources().getConfiguration().getLocales().get(0);
            } else {
                primaryLocale = Utils.getApp().getResources().getConfiguration().locale;
            }
            return TextUtils.getLayoutDirectionFromLocale(primaryLocale) == View.LAYOUT_DIRECTION_RTL;
        }
        return false;
    }


    /**
     * Fix the problem of topping the ScrollView nested ListView/GridView/WebView/RecyclerView.
     *
     * @param view The root view inner of ScrollView.
     */
    public static void fixScrollViewTopping(View view) {
        view.setFocusable(false);
        ViewGroup viewGroup = null;
        if (view instanceof ViewGroup) {
            viewGroup = (ViewGroup) view;
        }
        if (viewGroup == null) {
            return;
        }
        for (int i = 0, n = viewGroup.getChildCount(); i < n; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setFocusable(false);
            if (childAt instanceof ViewGroup) {
                fixScrollViewTopping(childAt);
            }
        }
    }

    public static View layoutId2View(@LayoutRes final int layoutId) {
        LayoutInflater inflate = (LayoutInflater) Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflate.inflate(layoutId, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface onGetSizeListener {
        void onGetSize(View view);
    }
}
