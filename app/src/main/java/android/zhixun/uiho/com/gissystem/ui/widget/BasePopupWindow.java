package android.zhixun.uiho.com.gissystem.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by simpepeng on 2017/8/1.
 */

public abstract class BasePopupWindow extends PopupWindow {

    protected Context mContext;
    protected View mRootView;

    public BasePopupWindow(Context context) {
        super(context);
        this.mContext = context;

        mRootView = View.inflate(context, getLayoutId(), null);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setContentView(mRootView);
    }

    public abstract int getLayoutId();

    public <T extends View> T findViewById(int id) {
        return mRootView.findViewById(id);
    }

    @Override
    public void dismiss() {
        reset();
        super.dismiss();
    }

    protected void reset() {
        WindowManager.LayoutParams lp1 = ((Activity) mContext).getWindow().getAttributes();
        lp1.alpha = 1f;
        ((Activity) mContext).getWindow().setAttributes(lp1);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        setBackground();
        super.showAtLocation(parent, gravity, x, y);
    }

    private void setBackground() {
        //popWindow弹出可以点击
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
//        this.setBackgroundDrawable(new ColorDrawable(Color.RED));
        this.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), (Bitmap) null));
//        // 设置背景颜色变暗
//        ViewGroup.LayoutParams layoutParams = ((Activity) mContext).getWindow().getDecorView().getLayoutParams();
//        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public void show(View view, int gravity) {
        this.showAtLocation(view, gravity, 0, 0);
    }

    @Override
    public void showAsDropDown(View anchor) {
        setBackground();
        super.showAsDropDown(anchor);
    }
}
