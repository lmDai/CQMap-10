package android.zhixun.uiho.com.gissystem.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;

/**
 * Created by parcool on 2016/11/29.
 */

public abstract class BaseActivityWithTitle extends BaseActivityWithStatusBar {

    private AppCompatImageView aivBack;
    private TextView tvTitle;
    private LinearLayout llMenu, mLLTitle;

    @Override
    protected void onPreCreateActivity(@Nullable Bundle savedInstanceState) {
        //do not write super.onCreate(),because it will call as recycle!
//        super.onCreate(savedInstanceState);
        mLLTitle = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_title, mRootLayout, false);
        aivBack = (AppCompatImageView) mLLTitle.findViewById(R.id.aiv_back);
        aivBack.setOnClickListener(view -> finish());
        tvTitle = (TextView) mLLTitle.findViewById(R.id.toolbar_title);
        llMenu = (LinearLayout) mLLTitle.findViewById(R.id.ll_menu);
        mRootLayout.addView(mLLTitle, 0);
    }

    /***
     * 设置标题
     *
     * @param charSequence 文字
     */
    protected void setTitleText(CharSequence charSequence) {
        tvTitle.setText(charSequence);
    }

    /***
     * 设置标题
     *
     * @param titleRes 文字的资源
     */
    protected void setTitleRes(int titleRes) {
        tvTitle.setText(getResources().getText(titleRes));
    }

    /***
     * 设置标题栏的高度
     *
     * @param heightPixel
     */
    protected void setLLTitleHeight(int heightPixel) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLLTitle.getLayoutParams();
        layoutParams.height = heightPixel;
        mLLTitle.requestLayout();
    }

    /***
     * 设置标题栏的alpha
     *
     * @param alpha from 0 to 1
     */
    protected void setLLTitleAlpha(float alpha) {
        mLLTitle.setAlpha(alpha);
    }

    /***
     * 添加'菜单'
     *
     * @param drawable
     * @param onClickListener
     */
    protected void addMenu(Drawable drawable, View.OnClickListener onClickListener) {
        AppCompatImageView imageView = new AppCompatImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(drawable);
        imageView.setOnClickListener(onClickListener);
        llMenu.addView(imageView);
    }

    protected void addMenuString(String string, View.OnClickListener onClickListener) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        textView.setText(string);
        textView.setTextColor(Color.WHITE);
        textView.setOnClickListener(onClickListener);
        llMenu.addView(textView);
    }

}
