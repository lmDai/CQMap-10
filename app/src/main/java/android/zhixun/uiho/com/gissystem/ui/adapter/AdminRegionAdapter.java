package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class AdminRegionAdapter extends CommonAdapter<AreaModel> {

    public AdminRegionAdapter(Context context, List<AreaModel> datas) {
        super(context, R.layout.item_admin_region, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AreaModel areaModel, int position) {
        holder.setText(R.id.tv_title,areaModel.getAreaName());
        holder.getView(R.id.tv_title).setSelected(areaModel.isChecked());
    }
}
