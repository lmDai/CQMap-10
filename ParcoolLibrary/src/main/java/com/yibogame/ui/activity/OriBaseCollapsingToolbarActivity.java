package com.yibogame.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yibogame.R;
import com.yibogame.util.DeviceUtil;


/**
 * Created by parcool on 2016/7/25.
 */

public abstract class OriBaseCollapsingToolbarActivity extends OriBaseToolbarActivity {


    @Override
    protected void onToolBarCreateView(CoordinatorLayout coordinatorLayout, Bundle savedInstanceState) {
        initToolBar();
        onToolBarCreateView(coordinatorLayout, savedInstanceState);
    }


    @Override
    protected int getContentViewResource() {
        return R.layout.activity_base_toolbar_collapsing;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
////            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
////                //将侧边栏顶部延伸至status bar
////                mDrawerLayout.setFitsSystemWindows(true);
////                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
////                mDrawerLayout.setClipToPadding(false);
////            }
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    protected void initToolBar() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        layoutParams.height = DeviceUtil.dip2px(this, 256);
        appBarLayout.setLayoutParams(layoutParams);
        // This example uses decor view, but you can use any visible view.
        View decorView = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setSystemUiVisibility(0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT > 16) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }

}
