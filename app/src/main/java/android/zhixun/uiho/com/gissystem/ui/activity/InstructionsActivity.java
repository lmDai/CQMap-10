package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.zhixun.uiho.com.gissystem.R;

import com.yibogame.flux.stores.Store;

/**
 * Created by simple on 2017/12/7.
 */

public class InstructionsActivity extends BaseActivityWithTitle {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_instructions;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("使用说明");
    }


    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }
}
