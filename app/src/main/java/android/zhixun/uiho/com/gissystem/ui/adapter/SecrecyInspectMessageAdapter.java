package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.zhixun.uiho.com.gissystem.flux.models.api.InfoPushListModel;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by simple on 2017/12/12.
 */

public class SecrecyInspectMessageAdapter extends CommonAdapter<InfoPushListModel> {

    public SecrecyInspectMessageAdapter(Context context, int layoutId, List<InfoPushListModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, InfoPushListModel infoPushListModel, int position) {

    }
}
