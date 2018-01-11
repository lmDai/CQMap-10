package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.zhixun.uiho.com.gissystem.R;

import com.yibogame.flux.stores.Store;
import com.yibogame.util.ToastUtil;

/**
 * Created by parcool on 2016/11/29.
 */

public class TestTitleActivity extends BaseActivityWithTitle{

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("这是标题栏");
        addMenu(getResources().getDrawable(R.drawable.ic_equalizer_white_24dp), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShort("点击了菜单！");
            }
        });
    }

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }
}
