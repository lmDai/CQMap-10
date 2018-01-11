package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.InfoPushListModel;
import android.zhixun.uiho.com.gissystem.ui.adapter.SecrecyInspectMessageAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.VerticalRecyclerView;

import com.yibogame.flux.stores.Store;

import java.util.List;

/**
 * Created by simple on 2017/12/7.
 */

public class SecrecyInspectMessageActivity extends BaseActivityWithTitle {

    VerticalRecyclerView mRecyclerView;
    SecrecyInspectMessageAdapter mAdapter;
    List<InfoPushListModel> mData;

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_secrecy_inspect_message;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("保密检查消息");
        initViews();
    }


    private void initViews(){
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new SecrecyInspectMessageAdapter(this,
                R.layout.item_secrecy_inspect_message,mData);
    }
}
