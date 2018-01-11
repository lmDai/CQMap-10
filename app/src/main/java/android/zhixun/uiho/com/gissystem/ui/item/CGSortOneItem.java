package android.zhixun.uiho.com.gissystem.ui.item;

import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortOneModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.FruitCategoryAttrsModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.TempSortItemModel;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.TreeAdapterItem;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.ViewHolder;

import com.yibogame.util.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by parcool on 2016/12/20.
 */

public class CGSortOneItem extends TreeAdapterItem<TempSortItemModel> {

    public CGSortOneItem(TempSortItemModel data) {
        super(data);
    }

    @Override
    protected List<TreeAdapterItem> initChildrenList(TempSortItemModel data) {
        return null;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.item_cg_sort_one;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder) {
        Map<String, String> map = data.getMapList().get(0);
        if (data.isTitle()){
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (i > 3) {
                    break;
                }
                if (i == 0) {
                    holder.setText(R.id.tv_code, entry.getKey());
                } else if (i == 1) {
                    holder.setText(R.id.tv_date, entry.getKey());
                } else if (i == 2) {
                    holder.setText(R.id.tv_base, entry.getKey());
                } else if (i == 3) {
                    holder.setText(R.id.tv_format, entry.getKey());
                }
                i++;
            }
            holder.setText(R.id.tv_detail,"操作");
        }else {
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (i > 3) {
                    break;
                }
                if (i == 0) {
                    holder.setText(R.id.tv_code, entry.getValue());
                } else if (i == 1) {
                    holder.setText(R.id.tv_date, entry.getValue());
                } else if (i == 2) {
                    holder.setText(R.id.tv_base, entry.getValue());
                } else if (i == 3) {
                    holder.setText(R.id.tv_format, entry.getValue());
                }
                i++;
            }
            holder.setText(R.id.tv_detail,"详情");
        }
    }
}
