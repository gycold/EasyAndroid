package com.easytools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.easytools.views.animation.MovingViewAnimator;

/**
 * package: com.easytools.views.MovingImageView
 * author: gyc
 * description:加载大图ImageVIew，可以自行移动的ImageView
 * 使用：
 * <com.easytools.views.MovingImageView
 * android:layout_width="match_parent"
 * android:layout_height="250dp"
 * android:src="@drawable/image"
 * app:miv_load_on_create="true"
 * app:miv_max_relative_size="3"
 * app:miv_min_relative_offset="0.2"
 * app:miv_start_delay="1000"
 * app:miv_repetitions="-1"
 * app:miv_speed="100" />
 * time: create at 2017/5/30 23:10
 */

public class MovingImageView extends ImageView {
    //视图控制相关的变量
    private float canvasWidth, canvasHeight;
    private float imageWidth, imageHeight;
    private float offsetWidth, offsetHeight;
    private int movementType;

    //用户控制的变量
    private float maxRelativeSize, minRelativeOffset;
    private int mSpeed;
    private long startDelay;
    private int mRepetitions;
    private boolean loadOnCreate;

    //自定义的动画控制的操作类
    private MovingViewAnimator mAnimator;

    public MovingImageView(Context context) {
        this(context, null);
    }

    public MovingImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovingImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MovingImageView, defStyle, 0);

        try {
            maxRelativeSize = attributes.getFloat(R.styleable.MovingImageView_miv_max_relative_size, 3.0f);
            minRelativeOffset = attributes.getFloat(R.styleable.MovingImageView_miv_min_relative_offset, 0.2f);
            mSpeed = attributes.getInt(R.styleable.MovingImageView_miv_speed, 50);
            mRepetitions = attributes.getInt(R.styleable.MovingImageView_miv_repetitions, -1);
            startDelay = attributes.getInt(R.styleable.MovingImageView_miv_start_delay, 0);
            loadOnCreate = attributes.getBoolean(R.styleable.MovingImageView_miv_load_on_create, true);
        } finally {
            attributes.recycle();
        }
        init();
    }

    private void init() {
        //强制设置ImageView的ScaleType为矩阵模式，即不改变原图的大小，从ImageView的左上角开始绘制原图
        super.setScaleType(ScaleType.MATRIX);
        mAnimator = new MovingViewAnimator(this);
    }

    /**
     * 更新canvas大小, 包括边缘的padding.
     *
     * @param w 新的width.
     * @param h 新的height.
     * @param oldW 旧的width.
     * @param oldH 旧的height.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        //update canvas size
        canvasWidth = (float) w - (float) (getPaddingLeft() + getPaddingRight());
        canvasHeight = (float) h - (float) (getPaddingTop() + getPaddingBottom());
        //after canvas changes need an update
        updateAll();
    }

    private void updateAll() {
        if (getDrawable() != null) {
            updateImageSize();
            updateOffsets();
            updateAnimator();
        }
    }

    private void updateImageSize() {
        imageWidth = getDrawable().getIntrinsicWidth();//取得Drawable的固有的宽度
        imageHeight = getDrawable().getIntrinsicHeight();//取得Drawable的固有的高度
    }

    /**
     * 计算画布和View的间距，用以决定是否可以移动，以及移动的路径是什么
     */
    private void updateOffsets() {
        float minSizeX = imageWidth * minRelativeOffset;
        float minSizeY = imageHeight * minRelativeOffset;
        offsetWidth = (imageWidth - canvasWidth - minSizeX) > 0 ? imageWidth - canvasWidth : 0;
        offsetHeight = (imageHeight - canvasHeight - minSizeY) > 0 ? imageHeight - canvasHeight : 0;
    }

    /**
     * 更新动画
     */
    private void updateAnimator() {
        if (canvasHeight == 0 && canvasWidth == 0)
            return;

        float scale = calculateTypeAndScale();
        if (scale == 0)
            return;

        float w = (imageWidth * scale) - canvasWidth;
        float h = (imageHeight * scale) - canvasHeight;

        mAnimator.updateValues(movementType, w, h);
        mAnimator.setStartDelay(startDelay);
        mAnimator.setSpeed(mSpeed);
        mAnimator.setRepetition(mRepetitions);

        if (loadOnCreate)
            mAnimator.start();
    }

    /**
     * 设置自动移动的最佳方式
     *
     * @return image scale.
     */
    private float calculateTypeAndScale() {
        movementType = MovingViewAnimator.AUTO_MOVE;
        float scale = 1f;
        float scaleByImage = Math.max(imageWidth / canvasWidth, imageHeight / canvasHeight);
        Matrix m = new Matrix();

        //Image is too small to performs any animation, needs a scale
        if (offsetWidth == 0 && offsetHeight == 0) {
            float sW = canvasWidth / imageWidth;
            float sH = canvasHeight / imageHeight;

            if (sW > sH) {
                scale = Math.min(sW, maxRelativeSize);
                m.setTranslate((canvasWidth - imageWidth * scale) / 2f, 0);
                movementType = MovingViewAnimator.VERTICAL_MOVE;

            } else if (sW < sH) {
                scale = Math.min(sH, maxRelativeSize);
                m.setTranslate(0, (canvasHeight - imageHeight * scale) / 2f);
                movementType = MovingViewAnimator.HORIZONTAL_MOVE;

            } else {
                scale = Math.max(sW, maxRelativeSize);
                movementType = (scale == sW) ? MovingViewAnimator.NONE_MOVE :
                        MovingViewAnimator.DIAGONAL_MOVE;
            }

            //Width too small to perform any horizontal animation, scale to width
        } else if (offsetWidth == 0) {
            scale = canvasWidth / imageWidth;
            movementType = MovingViewAnimator.VERTICAL_MOVE;

            //Height too small to perform any vertical animation, scale to height
        } else if (offsetHeight == 0) {
            scale = canvasHeight / imageHeight;
            movementType = MovingViewAnimator.HORIZONTAL_MOVE;

            //Enough size but too big, resize down
        } else if (scaleByImage > maxRelativeSize) {
            scale = maxRelativeSize / scaleByImage;
            if(imageWidth * scale < canvasWidth || imageHeight * scale < canvasHeight) {
                scale = Math.max(canvasWidth / imageWidth, canvasHeight / imageHeight);
            }
        }

        m.preScale(scale, scale);
        setImageMatrix(m);
        return scale;
    }

    /**
     * 不要设置ImageView的ScaleType！！！
     *
     * @param scaleType deprecated for force setup to ScaleType.Matrix
     */
    @Override
    @Deprecated
    public void setScaleType(ScaleType scaleType) {
        //super.setScaleType(scaleType);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        updateAll();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        updateAll();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        updateAll();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        updateAll();
    }

    /**
     * Returns the animator.
     *
     * @return Moving Animator.
     */
    public MovingViewAnimator getMovingAnimator() {
        return mAnimator;
    }

    public float getMaxRelativeSize() {
        return maxRelativeSize;
    }

    public void setMaxRelativeSize(float max) {
        maxRelativeSize = max;
        updateAnimator();
    }

    public float getMinRelativeOffset() {
        return minRelativeOffset;
    }

    public void setMinRelativeOffset(float min) {
        minRelativeOffset = min;
        updateAnimator();
    }

    public boolean isLoadOnCreate() {
        return loadOnCreate;
    }

    public void setLoadOnCreate(boolean loadOnCreate) {
        this.loadOnCreate = loadOnCreate;
    }
}
