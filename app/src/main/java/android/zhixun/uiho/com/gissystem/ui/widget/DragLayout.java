package android.zhixun.uiho.com.gissystem.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by simple on 2018/1/13.
 */

public class DragLayout extends LinearLayout {

    private ViewDragHelper mDragHelper;
    private View mDragView;

    public DragLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        mDragHelper = ViewDragHelper.create(this, callback);
    }

    private boolean setPadding = false;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!setPadding) {
            exit();
            setPadding = true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View topChildUnder = mDragHelper
                .findTopChildUnder((int) event.getX(), (int) event.getY());
        if (topChildUnder == mDragView) {
            mDragHelper.processTouchEvent(event);
            return true;
        }
        return false;
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mDragView;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (top <= 0) {
                top = 0;
            }
            if (top >= getHeight() - mDragView.getMeasuredHeight()) {
                top = getHeight() - mDragView.getMeasuredHeight();
            }
            return top;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top,
                                          int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            setPadding(0, top, 0, 0);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.d("simple", "onViewReleased");
            if (getPaddingTop() >= getHeight() / 3 * 2) {//to bottom
//                Log.d("simple", "to bottom");
                animaTo(getPaddingTop(), getHeight() - mDragView.getMeasuredHeight());
            } else if (getPaddingTop() <= getHeight() / 3) {//to top
                animaTo(getPaddingTop(), 0);
            } else {
                animaToCenter();
            }
        }
    };

    private void animaTo(int curPaddingTop, int toPaddingTop) {

        ValueAnimator animator = ValueAnimator.ofInt(curPaddingTop, toPaddingTop);
        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            int padding = (int) animation.getAnimatedValue();
            setPadding(0, padding, 0, 0);
        });
        animator.start();
    }

    public void animaToCenter() {
        mDragView.setVisibility(VISIBLE);
        animaTo(getPaddingTop(), getHeight() / 2);
    }

    public void exit() {
        mDragView.setVisibility(GONE);
        setPadding(0, getHeight(), 0, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
    }


}
