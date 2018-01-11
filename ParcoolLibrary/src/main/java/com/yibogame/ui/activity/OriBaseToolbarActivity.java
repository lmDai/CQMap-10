package com.yibogame.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yibogame.R;

/**
 * Created by parcool on 2016/8/30.
 */

public abstract class OriBaseToolbarActivity extends OriBaseActivity {

    protected Toolbar toolbar;
    protected CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        onToolBarCreateView(mCoordinatorLayout, savedInstanceState);
    }

    protected abstract void onToolBarCreateView(CoordinatorLayout coordinatorLayout, Bundle savedInstanceState);

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_base_toolbar_collapsing;
    }

    /***
     * 清除ToolbarScrollFlags
     */
    protected void clearToolbarScrollFlags(){
//        AppBarLayout appbar = (AppBarLayout)findViewById(R.id.app_bar_layout);
        AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

        mParams.setScrollFlags(0);

//        mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
    }

    /***
     * 初始化toolBar
     */
    @Override
    protected void initToolBar() {
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /***
     * 手动设置返回键的图标
     *
     * @param resId
     */
    protected void setToolBarIcon(int resId) {
        toolbar.setNavigationIcon(resId);
    }

    /***
     * 设置标题
     *
     * @param titleStr
     */
    protected void setTitleStr(String titleStr) {
        if (toolbar == null) {
            return;
        }
        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        if (mTitle == null) {
            return;
        }
        mTitle.setText(titleStr);
    }

    /***
     * 设置小号文字的标题
     *
     * @param titleStrSmall
     */
    protected void setTitleStrSmall(String titleStrSmall) {
        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setTextSize(16);
        setTitleStr(titleStrSmall);
    }

}
