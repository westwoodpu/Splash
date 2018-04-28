package com.westwoodpu.splash.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.jar.Attributes;

import javax.annotation.Nullable;

/**
 * Created by nxa16819 on 4/18/2018.
 */

public class SquareImage extends ImageView {
    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // This function has 2 params are width and height.
        // And now both of them equal width of phone
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
