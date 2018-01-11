package android.zhixun.uiho.com.gissystem.ui.widget;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.zhixun.uiho.com.gissystem.R;

import com.yibogame.util.DeviceUtil;
import com.yibogame.util.LogUtil;

/**
 * Created by parcool on 2016/11/28.
 */
public class DialogUtil {
    private static DialogUtil ourInstance = new DialogUtil();

    public static DialogUtil getInstance() {
        return ourInstance;
    }

    private DialogUtil() {
    }

    private Dialog mDialog;

    public void showAnchorDialog(View contentView, View anchorView) {
        mDialog = new Dialog(contentView.getContext(), R.style.MyDialogStyle);
        int leftMargin;
        int rightMargin;
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            leftMargin = ((RelativeLayout.LayoutParams) layoutParams).leftMargin;
            rightMargin = ((RelativeLayout.LayoutParams) layoutParams).rightMargin;
            LogUtil.i("RelativeLayout.LayoutParams" + leftMargin + "," + rightMargin);
//            ((RelativeLayout.LayoutParams) layoutParams).topMargin += contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * 2;
        } else if (layoutParams instanceof LinearLayout.LayoutParams) {
            leftMargin = ((LinearLayout.LayoutParams) layoutParams).leftMargin;
            rightMargin = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
            LogUtil.i("LinearLayout.LayoutParams" + leftMargin + "," + rightMargin);
//            ((LinearLayout.LayoutParams) layoutParams).topMargin += contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * 2;
        } else {
            leftMargin = DeviceUtil.dip2px(contentView.getContext(), 12);
            rightMargin = DeviceUtil.dip2px(contentView.getContext(), 12);
            LogUtil.i("12...");
        }
        int contentViewWidth = DeviceUtil.getScreenSize(contentView.getContext()).x - leftMargin - rightMargin;
        LinearLayout linearLayout = new LinearLayout(contentView.getContext());
//        linearLayout.setBackgroundColor(Color.BLUE);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        View vClose = LayoutInflater.from(contentView.getContext()).inflate(R.layout.layout_close, linearLayout, false);
        vClose.findViewById(R.id.cv_close).setClickable(true);
        vClose.findViewById(R.id.cv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        linearLayout.addView(vClose);
        linearLayout.addView(contentView);
        mDialog.setContentView(linearLayout, layoutParams);

//        mDialog.setTitle("Custom Dialog");
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);

        Window dialogWindow = mDialog.getWindow();
        if (location[0] >= DeviceUtil.getScreenSize(contentView.getContext()).x / 2) {
            assert dialogWindow != null;
            dialogWindow.setWindowAnimations(R.style.MyDialogAnimRight);
            LinearLayout.LayoutParams lpVClose = (LinearLayout.LayoutParams) vClose.getLayoutParams();
            lpVClose.topMargin = contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * -2;
            lpVClose.bottomMargin = contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * -2;
            lpVClose.rightMargin = contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * -1;
            lpVClose.leftMargin = contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * -1;
            lpVClose.gravity = Gravity.RIGHT;
        } else {
            assert dialogWindow != null;
            dialogWindow.setWindowAnimations(R.style.MyDialogAnimLeft);
            LinearLayout.LayoutParams lpVClose = (LinearLayout.LayoutParams) vClose.getLayoutParams();
            lpVClose.topMargin = contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * -2;
            lpVClose.bottomMargin = contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * -2;
            lpVClose.rightMargin = contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * -1;
            lpVClose.leftMargin = contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation) * -1;
            lpVClose.gravity = Gravity.LEFT;
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

        if (location[0] >= DeviceUtil.getScreenSize(contentView.getContext()).x / 2) {
            //右边
            lp.x = location[0] - contentViewWidth + anchorView.getMeasuredWidth() - contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation); // 新位置X坐标 - contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation)
        } else {
            //左边
            lp.x = location[0] + contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation);
        }
        lp.y = location[1] - DeviceUtil.getStatusHeight(contentView.getContext()) + contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation); // 新位置Y坐标+ contentView.getContext().getResources().getDimensionPixelSize(R.dimen.cardView_elevation)
        lp.width = contentViewWidth; // 宽度
        LogUtil.e("width=" + lp.width + "lp.x=" + lp.x + ",screen.width=" + DeviceUtil.getScreenSize(contentView.getContext()));
        lp.height = layoutParams.height; // 高度
        lp.alpha = 1f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog_symbol.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);

        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
