package com.yibogame.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by parcool on 2016/8/30.
 */

public class InterceptTouchEventLinearLayout extends LinearLayout {

    public InterceptTouchEventLinearLayout(Context context) {
        super(context);
    }

    public InterceptTouchEventLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptTouchEventLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
