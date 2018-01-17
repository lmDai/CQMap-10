package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.GethandoutConditionByFCModel;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by simple on 2018/1/17.
 */

public class ClassifyFlowTypeAttrValAdapter extends CommonAdapter<GethandoutConditionByFCModel.FruitCateGoryAttrVal> {

    public ClassifyFlowTypeAttrValAdapter(Context context,
                                          List<GethandoutConditionByFCModel.FruitCateGoryAttrVal> datas) {
        super(context, R.layout.item_flow_type, datas);
    }

    @Override
    protected void convert(ViewHolder holder,
                           GethandoutConditionByFCModel.FruitCateGoryAttrVal item,
                           int position) {
        holder.itemView.setSelected(item.selected);
        TextView itemView = holder.getView(R.id.tv_text);
        itemView.setText(item.attrValue);
    }

}
