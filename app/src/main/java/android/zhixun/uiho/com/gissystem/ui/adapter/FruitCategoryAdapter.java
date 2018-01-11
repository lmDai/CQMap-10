package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.FruitCategoryListModel;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by simple on 2017/12/25.
 */

public class FruitCategoryAdapter extends CommonAdapter<FruitCategoryListModel> {

    public FruitCategoryAdapter(Context context, List<FruitCategoryListModel> datas) {
        super(context, R.layout.item_flow_type, datas);
    }

    @Override
    protected void convert(ViewHolder holder, FruitCategoryListModel fruitCategoryListModel, int position) {
    }
}
