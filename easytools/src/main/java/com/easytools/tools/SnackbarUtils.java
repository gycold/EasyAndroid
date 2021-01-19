package com.easytools.tools;

import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * package: com.easytools.tools.SnackbarUtils
 * author: gyc
 * description:Snackbar 相关工具类
 * <p>
 * with           : 设置 snackbar 依赖 view
 * setMessage     : 设置消息
 * setMessageColor: 设置消息颜色
 * setBgColor     : 设置背景色
 * setBgResource  : 设置背景资源
 * setDuration    : 设置显示时长
 * setAction      : 设置行为
 * setBottomMargin: 设置底边距
 * show           : 显示 snackbar
 * showSuccess    : 显示预设成功的 snackbar
 * showWarning    : 显示预设警告的 snackbar
 * showError      : 显示预设错误的 snackbar
 * dismiss        : 消失 snackbar
 * getView        : 获取 snackbar 视图
 * addView        : 添加 snackbar 视图
 * <p>
 * time: create at 2018/1/20 0020 09:35
 */

public class SnackbarUtils {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final int COLOR_SUCCESS = 0xFF2BB600;
    private static final int COLOR_WARNING = 0xFFFFC100;
    private static final int COLOR_ERROR = 0xFFFF0000;
    private static final int COLOR_MESSAGE = 0xFFFFFFFF;
    private static WeakReference<Snackbar> sReference;

    private View view;
    private CharSequence message;
    private int messageColor;
    private int bgColor;
    private int bgResource;
    private int duration;
    private CharSequence actionText;
    private int actionTextColor;
    private View.OnClickListener actionListener;
    private int bottomMargin;

    private SnackbarUtils(final View view) {
        setDefault();
        this.view = view;
    }

    private void setDefault() {
        message = "";
        messageColor = COLOR_DEFAULT;
        bgColor = COLOR_DEFAULT;
        bgResource = -1;
        duration = LENGTH_SHORT;
        actionText = "";
        actionTextColor = COLOR_DEFAULT;
        bottomMargin = 0;
    }

    /**
     * 设置 snackbar 依赖 view
     *
     * @param parent 依赖 view
     * @return {@link SnackbarUtils}
     */
    public static SnackbarUtils with(@NonNull final View parent) {
        return new SnackbarUtils(parent);
    }

    /**
     * 设置消息
     *
     * @param msg 消息
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setMessage(@NonNull final CharSequence msg) {
        this.message = msg;
        return this;
    }

    /**
     * 设置消息颜色
     *
     * @param color 颜色
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setMessageColor(@ColorInt final int color) {
        this.messageColor = color;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color 背景色
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setBgColor(@ColorInt final int color) {
        this.bgColor = color;
        return this;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setBgResource(@DrawableRes final int bgResource) {
        this.bgResource = bgResource;
        return this;
    }

    /**
     * 设置显示时长
     *
     * @param duration 时长
     *                 <ul>
     *                 <li>{@link Duration#LENGTH_INDEFINITE}永久</li>
     *                 <li>{@link Duration#LENGTH_SHORT}短时</li>
     *                 <li>{@link Duration#LENGTH_LONG}长时</li>
     *                 </ul>
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setDuration(@Duration final int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 设置行为
     *
     * @param text     文本
     * @param listener 事件
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAction(@NonNull final CharSequence text, @NonNull final View.OnClickListener listener) {
        return setAction(text, COLOR_DEFAULT, listener);
    }

    /**
     * 设置行为
     *
     * @param text     文本
     * @param color    文本颜色
     * @param listener 事件
     * @return {@link SnackbarUtils}
     */

    public SnackbarUtils setAction(@NonNull final CharSequence text, @ColorInt final int color, @NonNull final View.OnClickListener listener) {
        this.actionText = text;
        this.actionTextColor = color;
        this.actionListener = listener;
        return this;
    }

    /**
     * 设置底边距
     *
     * @param bottomMargin 底边距
     */
    public SnackbarUtils setBottomMargin(@IntRange(from = 1) final int bottomMargin) {
        this.bottomMargin = bottomMargin;
        return this;
    }

    public Snackbar show() {
        return show(false);
    }

    /**
     * 显示 snackbar
     *
     * @param isShowTop
     * @return
     */
    public Snackbar show(boolean isShowTop) {
        View view = this.view;
        if (view == null) return null;
        if (isShowTop) {
            ViewGroup suitableParent = findSuitableParentCopyFromSnackbar(view);
            View topSnackBarContainer = suitableParent.findViewWithTag("topSnackBarCoordinatorLayout");
            if (topSnackBarContainer == null) {
                CoordinatorLayout topSnackBarCoordinatorLayout = new CoordinatorLayout(view.getContext());
                topSnackBarCoordinatorLayout.setTag("topSnackBarCoordinatorLayout");
                topSnackBarCoordinatorLayout.setRotation(180);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // bring to front
                    topSnackBarCoordinatorLayout.setElevation(100);
                }
                suitableParent.addView(topSnackBarCoordinatorLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                topSnackBarContainer = topSnackBarCoordinatorLayout;
            }
            view = topSnackBarContainer;
        }
        if (messageColor != COLOR_DEFAULT) {
            SpannableString spannableString = new SpannableString(message);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(messageColor);
            spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sReference = new WeakReference<>(Snackbar.make(view, spannableString, duration));
        } else {
            sReference = new WeakReference<>(Snackbar.make(view, message, duration));
        }
        final Snackbar snackbar = sReference.get();
        final Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        if (isShowTop) {
            for (int i = 0; i < snackbarView.getChildCount(); i++) {
                View child = snackbarView.getChildAt(i);
                child.setRotation(180);
            }
        }
        if (bgResource != -1) {
            snackbarView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            snackbarView.setBackgroundColor(bgColor);
        }
        if (bottomMargin != 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snackbarView.getLayoutParams();
            params.bottomMargin = bottomMargin;
        }
        if (actionText.length() > 0 && actionListener != null) {
            if (actionTextColor != COLOR_DEFAULT) {
                snackbar.setActionTextColor(actionTextColor);
            }
            snackbar.setAction(actionText, actionListener);
        }
        snackbar.show();
        return snackbar;
    }

    /**
     * 显示预设成功的 snackbar
     */
    public void showSuccess() {
        showSuccess(false);
    }

    /**
     * 显示预设成功的 snackbar
     *
     * @param isShowTop True to show the snackbar on the top, false otherwise.
     */
    public void showSuccess(boolean isShowTop) {
        bgColor = COLOR_SUCCESS;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show(isShowTop);
    }

    /**
     * 显示预设警告的 snackbar
     */
    public void showWarning() {
        showWarning(false);
    }

    /**
     * 显示预设警告的 snackbar
     *
     * @param isShowTop True to show the snackbar on the top, false otherwise.
     */
    public void showWarning(boolean isShowTop) {
        bgColor = COLOR_WARNING;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show(isShowTop);
    }

    /**
     * 显示预设错误的 snackbar
     */
    public void showError() {
        showError(false);
    }

    /**
     * 显示预设错误的 snackbar
     *
     * @param isShowTop True to show the snackbar on the top, false otherwise.
     */
    public void showError(boolean isShowTop) {
        bgColor = COLOR_ERROR;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show(isShowTop);
    }

    /**
     * 消失 snackbar
     */
    public static void dismiss() {
        if (sReference != null && sReference.get() != null) {
            sReference.get().dismiss();
            sReference = null;
        }
    }

    /**
     * 获取 snackbar 视图
     *
     * @return snackbar 视图
     */
    public static View getView() {
        Snackbar snackbar = sReference.get();
        if (snackbar == null) return null;
        return snackbar.getView();
    }

    /**
     * 添加 snackbar 视图
     * <p>在{@link #show()}之后调用</p>
     *
     * @param layoutId 布局文件
     * @param params   布局参数
     */
    public static void addView(@LayoutRes final int layoutId, @NonNull final ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
            layout.addView(child, -1, params);
        }
    }

    /**
     * 添加 snackbar 视图
     * <p>在{@link #show()}之后调用</p>
     *
     * @param child  要添加的 view
     * @param params 布局参数
     */
    public static void addView(@NonNull final View child, @NonNull final ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            layout.addView(child, params);
        }
    }

    private static ViewGroup findSuitableParentCopyFromSnackbar(View view) {
        ViewGroup fallback = null;

        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup) view;
            }

            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                }

                fallback = (ViewGroup) view;
            }

            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        return fallback;
    }
}
