package com.easytools.views.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * package: com.easytools.views.animation.MovingViewAnimator
 * author: gyc
 * description:视图移动的动画操作类
 * time: create at 2017/5/30 23:14
 */

public class MovingViewAnimator {
    //移动类型常量
    public static final int HORIZONTAL_MOVE = 1;
    public static final int VERTICAL_MOVE = 2;
    public static final int DIAGONAL_MOVE = 3;
    public static final int AUTO_MOVE = 0;
    public static final int NONE_MOVE = -1;

    //属性动画集合
    private AnimatorSet mAnimatorSet;
    private Animator.AnimatorListener animatorListener;
    private View mView;

    //状态标志
    private boolean isRunning;
    private int currentLoop;//当前执行到第几次了
    private boolean infiniteRepetition = true;//是否无限重复
    private ArrayList<Float> pathDistances;//路径距离

    //其他变量
    private int loopCount = -1;//循环次数
    private int movementType;//循环类型，上下左右等
    private float offsetWidth, offsetHeight;
    private int mSpeed = 50;
    private long mDelay = 0;
    private Interpolator mInterpolator;

    private Animator.AnimatorListener repeatAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(final Animator animation) {
            //super.onAnimationEnd(animation);
            //通知UI更新视图
            mView.post((new Runnable() {
                public void run() {
                    if (isRunning) {
                        if (infiniteRepetition) {
                            mAnimatorSet.start();
                            if (animatorListener != null)
                                animatorListener.onAnimationRepeat(animation);
                        } else {
                            currentLoop--;
                            if (currentLoop > 0) {
                                mAnimatorSet.start();
                                if (animatorListener != null)
                                    animatorListener.onAnimationRepeat(animation);
                            }
                        }
                    }
                }
            }));
        }
    };


    public MovingViewAnimator(View imgView) {
        mView = imgView;
        isRunning = false;
        mAnimatorSet = new AnimatorSet();
        pathDistances = new ArrayList<>();
        mInterpolator = new AccelerateDecelerateInterpolator();
    }

    public MovingViewAnimator(View imgView, int type, float width, float height) {
        this(imgView);
        updateValues(type, width, height);
    }

    private void init() {
        setUpAnimator();
        updateListener();
        setUpValues();
    }

    /**
     * 设置动画移动的类型
     */
    private void setUpAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        pathDistances.clear();

        switch (movementType) {
            case HORIZONTAL_MOVE:
                animatorSet.playSequentially(createHorizontalAnimator(0, offsetWidth),
                        createHorizontalAnimator(offsetWidth, 0));
                break;
            case VERTICAL_MOVE:
                animatorSet.playSequentially(createVerticalAnimator(0, offsetHeight),
                        createVerticalAnimator(offsetHeight, 0));
                break;
            case DIAGONAL_MOVE:
                animatorSet.playSequentially(createDiagonalAnimator(0, offsetWidth, 0, offsetHeight),
                        createDiagonalAnimator(offsetWidth, 0, offsetHeight, 0));
                break;
            case AUTO_MOVE:
                animatorSet.playSequentially(
                        createVerticalAnimator(0, offsetHeight),
                        createDiagonalAnimator(0, offsetWidth, offsetHeight, 0),
                        createHorizontalAnimator(offsetWidth, 0),
                        createDiagonalAnimator(0, offsetWidth, 0, offsetHeight),
                        createHorizontalAnimator(offsetWidth, 0),
                        createVerticalAnimator(offsetHeight, 0));
        }

        if (mAnimatorSet != null) {
            mAnimatorSet.removeAllListeners();
            stop();
        }
        mAnimatorSet = animatorSet;
    }

    private void setUpValues() {
        addListener(animatorListener);
        setSpeed(mSpeed);
        setStartDelay(mDelay);
        setRepetition(loopCount);
        setInterpolator(mInterpolator);
    }

    /**
     * 更新监听
     */
    private void updateListener() {
        mAnimatorSet.addListener(repeatAnimatorListener);
    }

    /**
     * 更新动画属性值
     *
     * @param type 移动类型
     * @param w    宽度
     * @param h    高度
     */

    public void updateValues(int type, float w, float h) {
        this.movementType = type;
        this.offsetWidth = w;
        this.offsetHeight = h;
        init();
    }

    public void setMovementType(int type) {
        updateValues(type, offsetWidth, offsetHeight);
    }

    public void setOffsets(float w, float h) {
        updateValues(movementType, w, h);
    }

    public void start() {
        if (movementType != NONE_MOVE) {
            isRunning = true;
            if (!infiniteRepetition)
                currentLoop = loopCount;
            mAnimatorSet.start();
        }
    }

    public void cancel() {
        if(isRunning) {
            mAnimatorSet.removeListener(repeatAnimatorListener);
            mAnimatorSet.cancel();
        }
    }

    @TargetApi(19)
    public void pause() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;

        if(mAnimatorSet.isStarted())
            mAnimatorSet.pause();
    }

    @TargetApi(19)
    public void resume() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;

        if(mAnimatorSet.isPaused())
            mAnimatorSet.resume();
    }

    public void stop() {
        isRunning = false;
        mAnimatorSet.removeListener(repeatAnimatorListener);
        mAnimatorSet.end();
        mView.clearAnimation();
    }

    public void setRepetition(int repetition) {
        if (repetition < 0)
            infiniteRepetition = true;
        else {
            loopCount = repetition;
            currentLoop = loopCount;
            infiniteRepetition = false;
        }
    }

    public Builder addCustomMovement() {
        return new Builder();
    }

    public void clearCustomMovement() {
        init();
        start();
    }

    public int getMovementType() {
        return movementType;
    }

    public int getRemainingRepetitions() {
        return (infiniteRepetition) ? -1 : currentLoop;
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        mAnimatorSet.setInterpolator(interpolator);
    }

    public void setStartDelay(long time) {
        mDelay = time;
        mAnimatorSet.setStartDelay(time);
    }

    /**
     * 设置动画集合里每一个动画的速度
     *
     * @param speed new speed.
     */
    public void setSpeed(int speed) {
        mSpeed = speed;
        List<Animator> listAnimator = mAnimatorSet.getChildAnimations();
        for (int i = 0; i < listAnimator.size(); i++) {
            Animator a = listAnimator.get(i);
            a.setDuration(parseSpeed(pathDistances.get(i)));
        }
    }

    public void addListener(Animator.AnimatorListener listener) {
        clearListener();
        if (listener != null) {
            animatorListener = listener;
            mAnimatorSet.addListener(animatorListener);
        }
    }

    public void clearListener() {
        if (animatorListener != null) {
            mAnimatorSet.removeListener(animatorListener);
            animatorListener = null;
        }
    }

    private long parseSpeed(float distance) {
        return (long) ((distance / (float) mSpeed) * 1000f);
    }

    private ObjectAnimator createHorizontalAnimator(float startValue, float endValue) {
        pathDistances.add(Math.abs(startValue - endValue));
        return createObjectAnimation("scrollX", startValue, endValue);
    }

    private ObjectAnimator createVerticalAnimator(float startValue, float endValue) {
        pathDistances.add(Math.abs(startValue - endValue));
        return createObjectAnimation("scrollY", startValue, endValue);
    }

    private ObjectAnimator createDiagonalAnimator(float startW, float endW, float startH, float endH) {
        float diagonal = Pythagoras(Math.abs(startW - endW), Math.abs(startH - endH));
        pathDistances.add(diagonal);
        PropertyValuesHolder pvhX = createPropertyValuesHolder("scrollX", startW, endW);
        PropertyValuesHolder pvhY = createPropertyValuesHolder("scrollY", startH, endH);
        return ObjectAnimator.ofPropertyValuesHolder(mView, pvhX, pvhY);
    }

    private ObjectAnimator createObjectAnimation(String prop, float startValue, float endValue) {
        return ObjectAnimator.ofInt(mView, prop, (int) startValue, (int) endValue);
    }

    private PropertyValuesHolder createPropertyValuesHolder(String prop, float startValue, float endValue) {
        return PropertyValuesHolder.ofInt(prop, (int) startValue, (int) endValue);
    }

    private static float Pythagoras(float a, float b) {
        return (float) Math.sqrt((a * a) + (b * b));
    }

    /**
     * 自定义移动方向的类型，可以自由搭配组合
     */
    public class Builder {

        private ArrayList<Animator> mList;

        private Builder() {
            mList = new ArrayList<>();
            pathDistances.clear();
        }

        public Builder addHorizontalMoveToRight() {
            mList.add(createHorizontalAnimator(0, offsetWidth));
            return this;
        }

        public Builder addHorizontalMoveToLeft() {
            mList.add(createHorizontalAnimator(offsetWidth, 0));
            return this;
        }

        public Builder addVerticalMoveToDown() {
            mList.add(createVerticalAnimator(0, offsetHeight));
            return this;
        }

        public Builder addVerticalMoveToUp() {
            mList.add(createVerticalAnimator(offsetHeight, 0));
            return this;
        }

        public Builder addDiagonalMoveToDownRight() {
            mList.add(createDiagonalAnimator(0, offsetWidth, 0, offsetHeight));
            return this;
        }

        public Builder addDiagonalMoveToDownLeft() {
            mList.add(createDiagonalAnimator(offsetWidth, 0, 0, offsetHeight));
            return this;
        }

        public Builder addDiagonalMoveToUpRight() {
            mList.add(createDiagonalAnimator(0, offsetWidth, offsetHeight, 0));
            return this;
        }

        public Builder addDiagonalMoveToUpLeft() {
            mList.add(createDiagonalAnimator(offsetWidth, 0, offsetHeight, 0));
            return this;
        }

        public void start() {
            mAnimatorSet.removeAllListeners();
            stop();
            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playSequentially(mList);
            updateListener();
            setUpValues();
            MovingViewAnimator.this.start();
        }

    }
}
