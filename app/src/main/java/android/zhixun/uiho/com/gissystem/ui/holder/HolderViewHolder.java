package android.zhixun.uiho.com.gissystem.ui.holder;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.HolderModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.HoldersParentModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnExpandableItemCheckedListener;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.yibogame.util.DateUtil;

/**
 * Created by zp on 2016/11/29.
 */

public class HolderViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private OnExpandableItemCheckedListener mOnExpandableItemCheckedListener;
    private boolean isFirst = true;

    @NonNull

    private TextView tvName,tvUploading,tvState,tvDateContent;
    private ImageView ivState,ivHolderSj;
    public HolderViewHolder(@NonNull View itemView,OnExpandableItemCheckedListener onExpandableItemCheckedListener) {
        super(itemView);
        this.mOnExpandableItemCheckedListener = onExpandableItemCheckedListener;
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvUploading = (TextView) itemView.findViewById(R.id.tv_uploading);
        ivState = (ImageView) itemView.findViewById(R.id.iv_state);
        tvDateContent = (TextView) itemView.findViewById(R.id.tv_date_content);
        ivHolderSj = (ImageView) itemView.findViewById(R.id.iv_holder_sj);

    }

    public void bind(@NonNull HoldersParentModel holderModel) {
        tvName.setText(holderModel.getName());
        tvUploading.setText("证件上传");
//        tvState.setText(holderModel.getState());
        tvDateContent.setText(DateUtil.longToString(DateUtil.yyyyMMDD,holderModel.getCardBeginTime())+" - "+DateUtil.longToString(DateUtil.yyyyMMDD,holderModel.getCardEndTime()));
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
        if(isFirst){
            ivHolderSj.setImageResource(R.mipmap.ic_arrow_drop_up_black_36dp);
            isFirst = false;
        }else{
            ivHolderSj.setImageResource(R.mipmap.arrow_bottom);
            isFirst = true;
        }
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