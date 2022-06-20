package com.easytools.tools;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * package: com.easytools.tools.MyTypefaceSpan
 * author: gyc
 * description:
 * 系统原生的TypefaceSpan只能使用原生的默认字体
 * 如果使用自定义的字体，通过这个来实现
 * time: create at 2022/6/15 18:31
 */
public class MyTypefaceSpan extends MetricAffectingSpan {
    private final Typeface typeface;

    public MyTypefaceSpan(final Typeface typeface) {
        this.typeface = typeface;
    }

    @Override
    public void updateDrawState(final TextPaint drawState) {
        apply(drawState);
    }

    @Override
    public void updateMeasureState(final TextPaint paint) {
        apply(paint);
    }

    private void apply(final Paint paint) {
        final Typeface oldTypeface = paint.getTypeface();
        final int oldStyle = oldTypeface != null ? oldTypeface.getStyle() : 0;
        int fakeStyle = oldStyle & ~typeface.getStyle();
        if ((fakeStyle & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((fakeStyle & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(typeface);
    }
}