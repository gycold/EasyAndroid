package com.easytools.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.text.DecimalFormat;

/**
 * package: com.easytools.views.HorizontalProgressBar
 * author: gyc
 * description:带文字的水平进度条
 * time: create at 2017/6/5 23:10
 */

public class HorizontalProgressBar extends View {
    private Paint bgPaint;
    private Paint progressPaint;

    private Paint tipPaint;
    private Paint textPaint;

    private int mWidth;
    private int mHeight;
    private int mViewHeight;

    private float mProgress;
    private float mCurrentProgress;

    private ValueAnimator mProgressAnimator;
    private int duration = 5000;//动画执行时间
    private int startDelay = 500;//动画延迟时间

    private int progressPaintWidth;//进度条画笔的宽度
    private int tipPaintWidth;//百分比提示框画笔的宽度
    private int tipHeight;//百分比提示框的高度
    private int tipWidth;//百分比提示框的宽度

    private Path path = new Path();//画三角形的path
    private int triangleHeight;//三角形的高
    private int progressMarginTop;//进度条距离提示框的高度

    private float moveDistance;//提示文字边框移动的距离
    private Rect textRect = new Rect();
    private String textString = "0";
    private int textPaintSize;//文字字体大小

    private int bgColor = 0xFFe1e5e8;//进度条背景颜色
    private int progressColor = 0xFFf66b12;//进度条颜色

    private RectF rectF = new RectF();//绘制提示框的矩形
    private int roundRectRadius;//圆角矩形的圆角半径

    private ProgressListener progressListener;

    public HorizontalProgressBar setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initPaint();
    }

    /**
     * 初始化画笔宽度及view大小
     */
    private void init() {
        progressPaintWidth = dp2px(4);
        tipHeight = dp2px(15);
        tipWidth = dp2px(30);
        tipPaintWidth = dp2px(1);
        triangleHeight = dp2px(3);
        roundRectRadius = dp2px(2);
        textPaintSize = sp2px(10);
        progressMarginTop = dp2px(8);

        //view真实的高度
        mViewHeight = tipHeight + tipPaintWidth + triangleHeight + progressPaintWidth + progressMarginTop;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        bgPaint = getPaint(progressPaintWidth, bgColor, Paint.Style.STROKE);
        progressPaint = getPaint(progressPaintWidth, progressColor, Paint.Style.STROKE);
        tipPaint = getPaint(tipPaintWidth, progressColor, Paint.Style.FILL);

        initTextPaint();
    }

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textPaintSize);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    /**
     * 统一处理paint
     *
     * @param strokeWidth
     * @param color
     * @param style
     * @return
     */
    private Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height));
    }

    /**
     * 测量宽度
     * @param mode
     * @param width
     * @return
     */
    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth = width;
                break;
        }
        return mWidth;
    }

    /**
     * 测量高度
     * @param mode
     * @param height
     * @return
     */
    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                mHeight = mViewHeight;
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制背景
        canvas.drawLine(getPaddingLeft(),
                tipHeight + progressMarginTop,
                getWidth(),
                tipHeight + progressMarginTop,
                bgPaint);

        //绘制真实进度
        canvas.drawLine(getPaddingLeft(),
                tipHeight + progressMarginTop,
                mCurrentProgress,
                tipHeight + progressMarginTop,
                progressPaint);

        drawTipView(canvas);
        drawText(canvas, textString);

    }

    /**
     * 绘制进度上边提示百分比的view
     *
     * @param canvas
     */
    private void drawTipView(Canvas canvas) {
        drawRoundRect(canvas);
        drawTriangle(canvas);
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        rectF.set(moveDistance, 0, tipWidth + moveDistance, tipHeight);
        canvas.drawRoundRect(rectF, roundRectRadius, roundRectRadius, tipPaint);
    }

    /**
     * 绘制三角形
     *
     * @param canvas
     */
    private void drawTriangle(Canvas canvas) {
        path.moveTo(tipWidth / 2 - triangleHeight + moveDistance, tipHeight);
        path.lineTo(tipWidth / 2 + moveDistance, tipHeight + triangleHeight);
        path.lineTo(tipWidth / 2 + triangleHeight + moveDistance, tipHeight);
        canvas.drawPath(path, tipPaint);
        path.reset();
    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas, String textString) {
        textRect.left = (int) moveDistance;
        textRect.top = 0;
        textRect.right = (int) (tipWidth + moveDistance);
        textRect.bottom = tipHeight;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString + "%", textRect.centerX(), baseline, textPaint);
    }

    /**
     * 进度移动动画，通过插值的方式改变移动的距离
     */
    private void initAnimation() {
        mProgressAnimator = ValueAnimator.ofFloat(0, mProgress);
        mProgressAnimator.setDuration(duration);
        mProgressAnimator.setStartDelay(startDelay);
        mProgressAnimator.setInterpolator(new LinearInterpolator());
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) mProgressAnimator.getAnimatedValue();
                //进度数值只显示整数
                textString = formatNum(format2Int(value));
                //把当前百分比进度转化成view宽度对应的比例
                mCurrentProgress = value * mWidth / 100;
                //进度回调方法
                if (progressListener != null) {
                    progressListener.currentProgressListener(value);
                }

                //移动百分比提示框，只有当进度到达提示框中间位置之后才开始移动，
                //当进度框移动到最右边的时候停止移动，但是进度条还可以继续移动
                //moveDistance是tip框移动的距离
                if (mCurrentProgress >= (tipWidth / 2) && mCurrentProgress <= (mWidth - tipWidth / 2)) {
                    moveDistance = mCurrentProgress - tipWidth / 2;
                }
                invalidate();
            }
        });
        mProgressAnimator.start();
    }

    public HorizontalProgressBar setProgress(float progress){
        mProgress = progress;
        initAnimation();
        return this;
    }

    /**
     * 开启动画
     */
    public void startProgressAnimation() {
        if (mProgressAnimator != null && !mProgressAnimator.isRunning() && !mProgressAnimator.isStarted())
            mProgressAnimator.start();
    }

    /**
     * 暂停动画
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pauseProgressAnimation() {
        if (mProgressAnimator != null) {
            mProgressAnimator.pause();
        }
    }

    /**
     * 恢复动画
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeProgressAnimation() {
        if (mProgressAnimator != null)
            mProgressAnimator.resume();
    }

    /**
     * 停止动画
     */
    public void stopProgressAnimation() {
        if (mProgressAnimator != null) {
            mProgressAnimator.end();
        }
    }


    public interface ProgressListener{
        void currentProgressListener(float currentProgress);
    }


    /**
     * 格式化数字(保留两位小数)
     *
     * @param money
     * @return
     */
    public static String formatNumTwo(double money) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money);
    }

    /**
     * 格式化数字(保留一位小数)
     *
     * @param money
     * @return
     */
    public static String formatNum(int money) {
        DecimalFormat format = new DecimalFormat("0");
        return format.format(money);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    public static int format2Int(double i) {
        return (int) i;
    }
}
