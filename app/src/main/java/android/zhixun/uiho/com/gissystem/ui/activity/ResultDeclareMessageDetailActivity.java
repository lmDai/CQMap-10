package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.InfoPushListModel;

import com.yibogame.flux.stores.Store;
import com.yibogame.util.DateUtil;

/**
 * Created by simple on 2017/12/8.
 */

public class ResultDeclareMessageDetailActivity extends BaseActivityWithTitle {

    public static final String DATA = "DATA";

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_result_declare_message_detail;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("消息详情");

        InfoPushListModel model = (InfoPushListModel) getIntent().getSerializableExtra(DATA);
        ((TextView) findViewById(R.id.tv_pushTitle)).setText(model.pushTitle);
        ((TextView) findViewById(R.id.tv_time)).setText(DateUtil.longToString(DateUtil.yyyyMMddHHmmss,
                model.createTime));
        ((TextView) findViewById(R.id.tv_message)).setText(model.pushTxt);
    }
}
