package android.zhixun.uiho.com.gissystem.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CheckListModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnExpandableItemCheckedListener;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

/**
 * Created by zp on 2016/11/30.
 */

public class CheckListViewHolder extends ChildViewHolder {

    private TextView tvTitle,tvState,tvTitleTwo;
    private OnExpandableItemCheckedListener mOnExpandableItemCheckedListener;
    public CheckListViewHolder(@NonNull View itemView, OnExpandableItemCheckedListener mOnExpandableItemCheckedListener) {
        super(itemView);
        this.mOnExpandableItemCheckedListener = mOnExpandableItemCheckedListener;
        tvTitle = (TextView) itemView.findViewById(R.id.toolbar_title);
        tvState = (TextView) itemView.findViewById(R.id.tv_state);
//        tvTitleTwo = (TextView) itemView.findViewById(R.id.tv_title_two);
    }

    public void bind(@NonNull CheckListModel checkListModel) {
      tvTitle.setText(checkListModel.getTitle());
        tvState.setText(checkListModel.getState());
    }

}
