package android.zhixun.uiho.com.gissystem.ui.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yibogame.util.LogUtil;

/**
 * Created by parcool on 2016/11/28.
 */
public class PopupWindowUtil {
    private static PopupWindowUtil ourInstance = new PopupWindowUtil();

    public static PopupWindowUtil getInstance() {
        return ourInstance;
    }

    private PopupWindowUtil() {
    }

    private PopupWindow mPopupWindow;

    public void showAnchorPopup(Activity activity, View contentView, View anchorView) {
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        contentView.measure(0, 0);
        mPopupWindow.setWidth(contentView.getMeasuredWidth());
        mPopupWindow.setHeight(contentView.getMeasuredHeight());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(activity, 1f);
            }
        });

        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        contentView.measure(0, 0);
        LogUtil.e("contentView=" + contentView.getMeasuredWidth() + "," + contentView.getMeasuredHeight());
        mPopupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0] - contentView.getMeasuredWidth() + anchorView.getMeasuredWidth(), location[1] + anchorView.getHeight());
//        mPopupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, 0, 0);
        backgroundAlpha(activity, 0.3f);
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }
}
