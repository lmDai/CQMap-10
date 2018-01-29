package android.zhixun.uiho.com.gissystem.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.HoldersModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnExpandableItemCheckedListener;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

/**
 * Created by zp on 2016/11/29.
 */

public class HolderDetailViewHolder  extends ChildViewHolder {

    private TextView tvIdCardContent,tvPhoneContent,tvEmailContent,tvUnitContent,tvNumberContent;
    private OnExpandableItemCheckedListener mOnExpandableItemCheckedListener;
    private String companyName;

    public HolderDetailViewHolder(@NonNull View itemView,OnExpandableItemCheckedListener mOnExpandableItemCheckedListener,String companyName) {
        super(itemView);
        this.mOnExpandableItemCheckedListener = mOnExpandableItemCheckedListener;
        tvIdCardContent = (TextView) itemView.findViewById(R.id.tv_idCard_content);
        tvPhoneContent = (TextView) itemView.findViewById(R.id.tv_phone_content);
        tvEmailContent = (TextView) itemView.findViewById(R.id.tv_email_content);
        tvUnitContent = (TextView) itemView.findViewById(R.id.tv_unit_content);
        tvNumberContent = (TextView) itemView.findViewById(R.id.tv_number_content);
        this.companyName = companyName;
    }

    public void bind(@NonNull HoldersModel holderDetailModel) {
        tvIdCardContent.setText(holderDetailModel.getIdcard());
        tvPhoneContent.setText(holderDetailModel.getPhone());
        tvEmailContent.setText(holderDetailModel.eMail);
        tvUnitContent.setText(companyName);
        tvNumberContent.setText(String.valueOf(holderDetailModel.getCardSource()));
    }
}