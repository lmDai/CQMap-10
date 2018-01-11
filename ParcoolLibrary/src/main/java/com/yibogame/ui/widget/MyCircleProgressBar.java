package com.yibogame.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.yibogame.R;


/**
 * Created by parcool on 2016/5/3.
 */
public class MyCircleProgressBar extends ProgressBar {

    public MyCircleProgressBar(Context context) {
        this(context, null);
    }

    public MyCircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }


    private void init(Context context) {
//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        Drawable drawable;
        try {
            drawable = getResources().getDrawable(R.drawable.ic_sync_black_24dp);
            setIndeterminateDrawable(drawable);
        }catch (Resources.NotFoundException e){
//            drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        }


//        Drawable drawable = new MyCircleProgressBarDrawable(context);

    }


}
