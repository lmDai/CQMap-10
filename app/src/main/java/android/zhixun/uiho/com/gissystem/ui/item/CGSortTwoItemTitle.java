package android.zhixun.uiho.com.gissystem.ui.item;

import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.TreeAdapterItem;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.ViewHolder;

import java.util.List;

/**
 * Created by parcool on 2016/12/20.
 */

public class CGSortTwoItemTitle extends TreeAdapterItem<String> {

    public CGSortTwoItemTitle(String data) {
        super(data);
    }


    @Override
    protected List<TreeAdapterItem> initChildrenList(String data) {
        return null;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.item_cg_sort_two_title;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder) {
    }
}
