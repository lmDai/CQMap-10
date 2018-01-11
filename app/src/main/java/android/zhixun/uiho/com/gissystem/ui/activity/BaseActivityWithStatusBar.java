package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.zhixun.uiho.com.gissystem.R;

import com.yibogame.ui.activity.BaseActivity;
import com.yibogame.util.DeviceUtil;
import com.yibogame.util.LogUtil;

/**
 * Created by parcool on 2016/11/25.
 */

public abstract class BaseActivityWithStatusBar extends BaseActivity {

    protected FrameLayout mStatusBar;
    protected LinearLayout mRootLayout;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootLayout = new LinearLayout(this);
        mRootLayout.setOrientation(LinearLayout.VERTICAL);
        mRootLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

//        if (Build.VERSION.SDK_INT < 19) {
////            mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 0));
//            //如果小于19，因为状态栏一定是黑色(或其他颜色)，所以没必要为状态栏留出位置
//        } else {
//            mStatusBar = new FrameLayout(this);
//            int statusBarHeight = DeviceUtil.getStatusHeight(this);
//            mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    statusBarHeight));
//            mStatusBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//            mRootLayout.addView(mStatusBar);
//        }
        mRootLayout.addView(getLayoutInflater().inflate(getLayoutRes(), mRootLayout, false));
        setContentView(mRootLayout);
        onPreCreateActivity(savedInstanceState);
        onCreateActivity(savedInstanceState);
    }

    protected void onPreCreateActivity(@Nullable Bundle savedInstanceState){}
    protected abstract int getLayoutRes();
    protected abstract void onCreateActivity(@Nullable Bundle savedInstanceState);

    /***
     * 设置状态栏高度
     *
     * @param height
     */
    protected void setStatusBarHeight(int height) {
        if (mStatusBar == null) {
            LogUtil.w(BaseActivityWithStatusBar.this.getClass().getSimpleName(), "because the build.version less than 19,so the statusBar always is black(or any other color),so mStatusBar is null!");
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mStatusBar.getLayoutParams();
        layoutParams.height = height;
        mStatusBar.requestLayout();
    }

    /***
     * 设置状态栏背景颜色
     *
     * @param colorRes
     */
    protected void setStatusBarBackgroundColor(int colorRes) {
        mStatusBar.setBackgroundColor(getResources().getColor(colorRes));
    }

    /***
     * 设置状态栏背景图片
     *
     * @param drawableRes
     */
    protected void setStatusBarBackground(int drawableRes) {
        mStatusBar.setBackground(getResources().getDrawable(drawableRes));
    }
}
