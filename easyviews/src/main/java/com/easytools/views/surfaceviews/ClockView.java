package com.easytools.views.surfaceviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Calendar;

/**
 * package: com.easytools.views.surfaceviews.ClockView
 * author: gyc
 * description:自定义SurfaceView，实现钟表效果
 * time: create at 2017/5/15 22:54
 */

public class ClockView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //钟表默认半径
    private static final int DEFAULT_RADIUS = 200;

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean flag;

    private OnTimeChangeListener onTimeChangeListener;

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener;
    }

    //圆和刻度的画笔
    private Paint mPaint;
    //指针画笔
    private Paint mPointerPaint;

    //画布的宽高
    private int mCanvasWidth, mCanvasHeight;
    //时钟半径
    private int mRadius = DEFAULT_RADIUS;
    //秒针的长度
    private int mSecondPointerLength;
    //分针的长度
    private int mMinutePointerLength;
    //时针的长度
    private int mHourPointerLength;
    //时刻度的长度
    private int mHourDegreeLength;
    //秒刻度的长度
    private int mSecondDegreeLength;

    //时钟显示的时、分、秒
    private int mHour, mMinute, mSecond;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mSecond = Calendar.getInstance().get(Calendar.SECOND);

        mHolder = getHolder();
        mHolder.addCallback(this);

        mPaint = new Paint();
        mPointerPaint = new Paint();

        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mPointerPaint.setColor(Color.BLACK);
        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointerPaint.setTextSize(22);
        mPointerPaint.setTextAlign(Paint.Align.CENTER);

        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = measureSize(widthMeasureSpec);
        int desiredHeight = measureSize(heightMeasureSpec);

        //+4是为了设置默认的2px的内边距，因为绘制时钟的圆的画笔设置的宽度是2px
        setMeasuredDimension(mCanvasWidth = desiredWidth + 4, mCanvasHeight = desiredHeight + 4);
        mRadius = (int) (Math.min(desiredWidth - getPaddingLeft() - getPaddingRight(),
                desiredHeight - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);
        calculateLengths();
    }

    private int measureSize(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mRadius * 2 + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 计算指针和刻度长度
     */
    private void calculateLengths() {
        mHourDegreeLength = (int) (mRadius * 1.0f / 7);
        mSecondDegreeLength = (int) (mHourDegreeLength * 1.0f / 2);

        // hour : minute : second = 1 : 1.25 : 1.5
        mHourPointerLength = (int) (mRadius * 1.0f / 2);
        mMinutePointerLength = (int) (mHourPointerLength * 1.25f);
        mSecondPointerLength = (int) (mHourPointerLength * 1.5f);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flag = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }

    @Override
    public void run() {
        long start, end;
        while (flag) {
            start = System.currentTimeMillis();
            handler.sendEmptyMessage(0);
            draw();
            logic();
            end = System.currentTimeMillis();

            try {
                if (end - start < 1000) {
                    Thread.sleep(1000 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (onTimeChangeListener != null) {
                onTimeChangeListener.onTimeChange(ClockView.this, mHour, mMinute, mSecond);
            }
            return false;
        }
    });

    /**
     * 绘制
     */
    private void draw(){
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //刷屏
                mCanvas.drawColor(Color.WHITE);
                //将坐标系原点移至去除内边距后的画布中心
                mCanvas.translate(mCanvasWidth * 1.0f / 2 + getPaddingLeft() - getPaddingRight(),
                        mCanvasHeight * 1.0f / 2 + getPaddingTop() - getPaddingBottom());
                //绘制圆盘
                mPaint.setStrokeWidth(2f);
                mCanvas.drawCircle(0, 0, mRadius, mPaint);
                //绘制时刻度
                for (int i = 0; i < 12; i++) {
                    mCanvas.drawLine(0, mRadius, 0, mRadius - mHourDegreeLength, mPaint);
                    mCanvas.rotate(30);
                }
                //绘制秒刻度
                mPaint.setStrokeWidth(1.5f);
                for (int i = 0; i < 60; i++) {
                    //时刻度绘制过的区域不再绘制
                    if (i % 5 != 0) {
                        mCanvas.drawLine(0, mRadius, 0, mRadius - mSecondDegreeLength, mPaint);
                    }
                    mCanvas.rotate(6);
                }

                //绘制数字
                mPointerPaint.setColor(Color.BLACK);
                for (int i = 0; i < 12; i++) {
                    String number = 6 + i < 12 ? String.valueOf(6 + i) : (6 + i) > 12 ? String
                            .valueOf(i - 6) : "12";
                    mCanvas.save();
                    mCanvas.translate(0, mRadius * 5.5f / 7);
                    mCanvas.rotate(-i * 30);
                    mCanvas.drawText(number, 0, 0, mPointerPaint);
                    mCanvas.restore();
                    mCanvas.rotate(30);
                }

                //绘制上下午
                mCanvas.drawText(mHour < 12 ? "AM" : "PM", 0, mRadius * 1.5f / 4, mPointerPaint);
                //绘制时针
                Path path = new Path();
                path.moveTo(0, 0);
                int[] hourPointerCoordinates = getPointerCoordinates(mHourPointerLength);
                path.lineTo(hourPointerCoordinates[0], hourPointerCoordinates[1]);
                path.lineTo(hourPointerCoordinates[2], hourPointerCoordinates[3]);
                path.lineTo(hourPointerCoordinates[4], hourPointerCoordinates[5]);
                path.close();
                mCanvas.save();
                mCanvas.rotate(180 + mHour % 12 * 30 + mMinute * 1.0f / 60 * 30);
                mCanvas.drawPath(path, mPointerPaint);
                mCanvas.restore();

                //绘制分针
                path.reset();
                path.moveTo(0, 0);
                int[] minutePointerCoordinates = getPointerCoordinates(mMinutePointerLength);
                path.lineTo(minutePointerCoordinates[0], minutePointerCoordinates[1]);
                path.lineTo(minutePointerCoordinates[2], minutePointerCoordinates[3]);
                path.lineTo(minutePointerCoordinates[4], minutePointerCoordinates[5]);
                path.close();
                mCanvas.save();
                mCanvas.rotate(180 + mMinute * 6);
                mCanvas.drawPath(path, mPointerPaint);
                mCanvas.restore();

                //绘制秒针
                mPointerPaint.setColor(Color.RED);
                path.reset();
                path.moveTo(0, 0);
                int[] secondPointerCoordinates = getPointerCoordinates(mSecondPointerLength);
                path.lineTo(secondPointerCoordinates[0], secondPointerCoordinates[1]);
                path.lineTo(secondPointerCoordinates[2], secondPointerCoordinates[3]);
                path.lineTo(secondPointerCoordinates[4], secondPointerCoordinates[5]);
                path.close();
                mCanvas.save();
                mCanvas.rotate(180 + mSecond * 6);
                mCanvas.drawPath(path, mPointerPaint);
                mCanvas.restore();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    /**
     * 获取指针坐标
     *
     * @param pointerLength 指针长度
     * @return int[]{x1,y1,x2,y2,x3,y3}
     */
    private int[] getPointerCoordinates(int pointerLength){
        int y = (int) (pointerLength * 3.0f / 4);
        int x = (int) (y * Math.tan(Math.PI / 180 * 5));
        return new int[]{-x, y, 0, pointerLength, x, y};
    }

    /**
     * 逻辑
     */
    private void logic() {
        mSecond++;
        if (mSecond == 60) {
            mSecond = 0;
            mMinute++;
            if (mMinute == 60) {
                mMinute = 0;
                mHour++;
                if (mHour == 24) {
                    mHour = 0;
                }
            }
        }
    }

    //-----------------Setter and Getter start-----------------//
    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = Math.abs(hour) % 24;
        if (onTimeChangeListener != null) {
            onTimeChangeListener.onTimeChange(this, mHour, mMinute, mSecond);
        }
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = Math.abs(minute) % 60;
        if (onTimeChangeListener != null) {
            onTimeChangeListener.onTimeChange(this, mHour, mMinute, mSecond);
        }
    }

    public int getSecond() {
        return mSecond;
    }

    public void setSecond(int second) {
        mSecond = Math.abs(second) % 60;
        if (onTimeChangeListener != null) {
            onTimeChangeListener.onTimeChange(this, mHour, mMinute, mSecond);
        }
    }

    public void setTime(Integer... time) {
        if (time.length > 3) {
            throw new IllegalArgumentException("the length of argument should bo less than 3");
        }
        if (time.length > 2)
            setSecond(time[2]);
        if (time.length > 1)
            setMinute(time[1]);
        if (time.length > 0)
            setHour(time[0]);
    }
    //-----------------Setter and Getter end-------------------//


    /**
     * 当时间改变的时候提供回调的接口
     */
    public interface OnTimeChangeListener {
        /**
         * 事件发生改变时调用
         *
         * @param view    时间正在改变的view
         * @param hour    改变后的小时时刻
         * @param minute  改变后的分钟时刻
         * @param second  改变后的秒时刻
         */
        void onTimeChange(View view, int hour, int minute, int second);
    }

}
