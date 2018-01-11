package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.InfoPushListModel;

import com.yibogame.util.DateUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by simple on 2017/12/12.
 */

public class ResultDeclarMessageAdapter extends CommonAdapter<InfoPushListModel> {

    public ResultDeclarMessageAdapter(Context context, List<InfoPushListModel> datas) {
        super(context, R.layout.item_result_declare_message, datas);
    }

    @Override
    protected void convert(ViewHolder holder, InfoPushListModel infoPushListModel, int position) {
        holder.setText(R.id.toolbar_title, infoPushListModel.pushTitle)
                .setText(R.id.tv_message, infoPushListModel.pushTxt)
                .setText(R.id.tv_time, DateUtil.longToString(DateUtil.yyyyMMddHHmmss, infoPushListModel.createTime));
    }
}
