package com.yibogame.ui.widget;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by parcool on 2016/11/29.
 */

public class ObjectAnimWrapper {
    private View mView;

    public ObjectAnimWrapper(View view) {
        this.mView = view;
    }

    public void setHeight(int height) {
        mView.getLayoutParams().height = height;
        mView.requestLayout();
    }

    public int getHeight() {
        return mView.getLayoutParams().height;
    }

    public void setWidth(int width) {
        mView.getLayoutParams().width = width;
        mView.requestLayout();
    }

    public int getWidth() {
        return mView.getLayoutParams().width;
    }

    public int getTopMargin() {
        if (mView.getParent() instanceof RelativeLayout) {
            return ((RelativeLayout.LayoutParams) mView.getLayoutParams()).topMargin;
        } else if (mView.getParent() instanceof LinearLayout) {
            return ((LinearLayout.LayoutParams) mView.getLayoutParams()).topMargin;
        } else if (mView.getParent() instanceof FrameLayout) {
            return ((FrameLayout.LayoutParams) mView.getLayoutParams()).topMargin;
        } else {
            return 0;
        }
    }

    public void setTopMargin(int topMargin) {
        if (mView.getParent() instanceof RelativeLayout) {
            ((RelativeLayout.LayoutParams) mView.getLayoutParams()).topMargin = topMargin;
            mView.requestLayout();
        } else if (mView.getParent() instanceof LinearLayout) {
            ((LinearLayout.LayoutParams) mView.getLayoutParams()).topMargin = topMargin;
            mView.requestLayout();
        } else if (mView.getParent() instanceof FrameLayout) {
            ((FrameLayout.LayoutParams) mView.getLayoutParams()).topMargin = topMargin;
            mView.requestLayout();
        } else {
            //do nothing!
        }
    }


    public int getLeftMargin() {
        if (mView.getParent() instanceof RelativeLayout) {
            return ((RelativeLayout.LayoutParams) mView.getLayoutParams()).leftMargin;
        } else if (mView.getParent() instanceof LinearLayout) {
            return ((LinearLayout.LayoutParams) mView.getLayoutParams()).leftMargin;
        } else if (mView.getParent() instanceof FrameLayout) {
            return ((FrameLayout.LayoutParams) mView.getLayoutParams()).leftMargin;
        } else {
            return 0;
        }
    }

    public void setLeftMargin(int leftMargin) {
        if (mView.getParent() instanceof RelativeLayout) {
            ((RelativeLayout.LayoutParams) mView.getLayoutParams()).leftMargin = leftMargin;
            mView.requestLayout();
        } else if (mView.getParent() instanceof LinearLayout) {
            ((LinearLayout.LayoutParams) mView.getLayoutParams()).leftMargin = leftMargin;
            mView.requestLayout();
        } else if (mView.getParent() instanceof FrameLayout) {
            ((FrameLayout.LayoutParams) mView.getLayoutParams()).leftMargin = leftMargin;
            mView.requestLayout();
        } else {
            //do nothing!
        }
    }


    public int getRightMargin() {
        if (mView.getParent() instanceof RelativeLayout) {
            return ((RelativeLayout.LayoutParams) mView.getLayoutParams()).rightMargin;
        } else if (mView.getParent() instanceof LinearLayout) {
            return ((LinearLayout.LayoutParams) mView.getLayoutParams()).rightMargin;
        } else if (mView.getParent() instanceof FrameLayout) {
            return ((FrameLayout.LayoutParams) mView.getLayoutParams()).rightMargin;
        } else {
            return 0;
        }
    }

    public void setRightMargin(int rightMargin) {
        if (mView.getParent() instanceof RelativeLayout) {
            ((RelativeLayout.LayoutParams) mView.getLayoutParams()).rightMargin = rightMargin;
            mView.requestLayout();
        } else if (mView.getParent() instanceof LinearLayout) {
            ((LinearLayout.LayoutParams) mView.getLayoutParams()).rightMargin = rightMargin;
            mView.requestLayout();
        } else if (mView.getParent() instanceof FrameLayout) {
            ((FrameLayout.LayoutParams) mView.getLayoutParams()).rightMargin = rightMargin;
            mView.requestLayout();
        } else {
            //do nothing!
        }
    }


    public int getBottomMargin(){
        if (mView.getParent() instanceof RelativeLayout) {
            return ((RelativeLayout.LayoutParams) mView.getLayoutParams()).bottomMargin;
        } else if (mView.getParent() instanceof LinearLayout) {
            return ((LinearLayout.LayoutParams) mView.getLayoutParams()).bottomMargin;
        } else if (mView.getParent() instanceof FrameLayout) {
            return ((FrameLayout.LayoutParams) mView.getLayoutParams()).bottomMargin;
        } else {
            return 0;
        }
    }

    public void setBottomMargin(int bottomMargin){
        if (mView.getParent() instanceof RelativeLayout) {
            ((RelativeLayout.LayoutParams) mView.getLayoutParams()).bottomMargin = bottomMargin;
            mView.requestLayout();
        } else if (mView.getParent() instanceof LinearLayout) {
            ((LinearLayout.LayoutParams) mView.getLayoutParams()).bottomMargin = bottomMargin;
            mView.requestLayout();
        } else if (mView.getParent() instanceof FrameLayout) {
            ((FrameLayout.LayoutParams) mView.getLayoutParams()).bottomMargin = bottomMargin;
            mView.requestLayout();
        } else {
            //do nothing!
        }
    }
}
