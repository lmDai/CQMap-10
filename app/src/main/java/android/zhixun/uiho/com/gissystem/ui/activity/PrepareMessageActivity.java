package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.ui.widget.VerticalRV;

import com.yibogame.flux.stores.Store;

/**
 * Created by simple on 2017/12/7.
 */

public class PrepareMessageActivity extends BaseActivityWithTitle {

    VerticalRV mRecyclerView;

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_prepare_message;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("预审消息");

        initViews();
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }
}
