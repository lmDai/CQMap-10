package android.zhixun.uiho.com.gissystem.ui.item;

import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortTwoModel;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.TreeAdapterItem;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.ViewHolder;

import java.util.List;

/**
 * Created by parcool on 2016/12/20.
 */

public class CGSortTwoItem extends TreeAdapterItem<CGSortTwoModel> {

    public CGSortTwoItem(CGSortTwoModel data) {
        super(data);
    }


    @Override
    protected List<TreeAdapterItem> initChildrenList(CGSortTwoModel data) {
        return null;
    }


    @Override
    protected int initLayoutId() {
        return R.layout.item_cg_sort_two;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder) {
        holder.setText(R.id.tv_call, data.getCall());
        holder.setText(R.id.tv_dit, data.getDit());
        holder.setText(R.id.tv_rank, data.getRank());
        holder.setText(R.id.tv_HCJZ, data.getHCJZ());
    }
}
