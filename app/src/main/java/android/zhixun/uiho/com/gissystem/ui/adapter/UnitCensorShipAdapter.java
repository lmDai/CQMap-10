package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CRCheckPersonModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRModel;
import android.zhixun.uiho.com.gissystem.flux.models.UnitCensorShipModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyPersonListModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;


import com.yibogame.util.DateUtil;
import com.yibogame.util.LogUtil;

import java.util.List;

/**
 * Created by zp on 2016/11/29.
 */

public class UnitCensorShipAdapter extends RecyclerView.Adapter<UnitCensorShipAdapter.ViewHolder> {
    private Context context;
    private List<CompanyDetailByCheckedModel> mListCRModel;//crmodel
    //    private List<CRCheckPersonModel> mListCheckPersonModel;//crCheckPersonModel
    private OnItemClickListener mOnItemClickListener;


    /**
     * 构造方法
     *
     * @param context     Context对象，不允许为null
     * @param ListCRModel
     */
    public UnitCensorShipAdapter(Context context, List<CompanyDetailByCheckedModel> ListCRModel) {
        this.context = context;
        this.mListCRModel = ListCRModel;
//        this.mListCheckPersonModel = ListCheckPersonModel;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //

    @Override
    public UnitCensorShipAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unitcensorship, parent, false);

        return new UnitCensorShipAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(UnitCensorShipAdapter.ViewHolder holder, int position) {
        CompanyDetailByCheckedModel unitCensorShipModel = mListCRModel.get(position);
//        Uri uri = Uri.parse("res:///"+R.mipmap.avatar_wangbaoqiang);
//        holder.myPicture.setImageURI(uri);
//        holder.tvName.setText(eventRecordModel.getName());
//        holder.tvDate.setText(eventRecordModel.getDate());
//        holder.tvContent.setText(eventRecordModel.getMessage());
        if (unitCensorShipModel.getIsAdoptLeader() == 1) {
            holder.ivPassOrNot.setImageResource(R.mipmap.pass);
        } else {
            holder.ivPassOrNot.setImageResource(R.mipmap.notpass);
        }
        String secrecyPerson = "";
        if (unitCensorShipModel.getSecrecyPersonList() != null) {
            //这个for循环太丑了，先这样吧，懒得搞了
            for (int i = 0; i < unitCensorShipModel.getSecrecyPersonList().size(); i++) {
                if (i == unitCensorShipModel.getSecrecyPersonList().size() - 1) {
                    secrecyPerson += unitCensorShipModel.getSecrecyPersonList().get(i).getName() + "\t";
                } else {
                    secrecyPerson += unitCensorShipModel.getSecrecyPersonList().get(i).getName() + "、";
                }
            }
        }
        String cooperatePersons = unitCensorShipModel.getCooperatePersons();
        //参检人员
//        String checkPerson = unitCensorShipModel.getExaminer() + unitCensorShipModel.getTempCheckPerson();
        holder.tvExaminerContent.setText(secrecyPerson.equals("") ? "未选择检查人员" : secrecyPerson + cooperatePersons);
        holder.tvCheckTimeContent.setText(DateUtil.longToString(DateUtil.yyyyMMddHHmmss, unitCensorShipModel.getSecrecyTime()));
        //如果mOnItemClickListener不为空，则回调。
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mListCRModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvExaminerContent, tvCheckTimeContent;
        public ImageView ivPassOrNot;

        public ViewHolder(View itemView) {
            super(itemView);
            tvExaminerContent = (TextView) itemView.findViewById(R.id.tv_examiner_content);
            tvCheckTimeContent = (TextView) itemView.findViewById(R.id.tv_checkTime_content);
            ivPassOrNot = (ImageView) itemView.findViewById(R.id.iv_pass);


        }
    }
}
