package android.zhixun.uiho.com.gissystem.ui.holder;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CheckGroupModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnExpandableItemCheckedListener;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

/**
 * Created by zp on 2016/11/30.
 */

public class CheckGroupViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private OnExpandableItemCheckedListener mOnExpandableItemCheckedListener;

    @NonNull

    private TextView tvTitle;

    public CheckGroupViewHolder(@NonNull View itemView, OnExpandableItemCheckedListener onExpandableItemCheckedListener) {
        super(itemView);
        this.mOnExpandableItemCheckedListener = onExpandableItemCheckedListener;
        tvTitle = (TextView) itemView.findViewById(R.id.toolbar_title);


    }

    public void bind(@NonNull CheckGroupModel checkGroupModel) {
        tvTitle.setText("检查情况");
    }

    @SuppressLint("NewApi")
    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            if (expanded) {
//                mArrowExpandImageView.setRotation(ROTATED_POSITION);
//            } else {
//                mArrowExpandImageView.setRotation(INITIAL_POSITION);
//            }
//        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
//            mArrowExpandImageView.startAnimation(rotateAnimation);
        }
    }
}
