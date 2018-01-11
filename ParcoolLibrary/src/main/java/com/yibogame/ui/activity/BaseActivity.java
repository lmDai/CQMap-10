package com.yibogame.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.yibogame.app.RxBus;
import com.yibogame.flux.dispatcher.Dispatcher;
import com.yibogame.flux.stores.Store;

import java.io.Serializable;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by parcool on 2016/11/25.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Dispatcher mDispatcher;
    protected Subscription mSubscription;
    private Store mStore;
    protected Context mContext;

    public static final String DATA = "DATA";

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (android.os.Build.VERSION.SDK_INT >= 19) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        this.mContext = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //初始化flux
        mDispatcher = Dispatcher.getInstance();
        mStore = initActionsCreatorAndStore();
        if (mStore != null) {
            mDispatcher.register(mStore);
            mSubscription = RxBus.getInstance().toObservable(Store.StoreChangeEvent.class).subscribe(new Action1<Store.StoreChangeEvent>() {
                @Override
                public void call(Store.StoreChangeEvent storeChangeEvent) {
                    onStoreCall(storeChangeEvent);
                }
            });
        }
    }

    protected abstract Store initActionsCreatorAndStore();
    protected abstract void onStoreCall(Store.StoreChangeEvent storeChangeEvent);

    /***
     * 注意：销毁flux中一系列的东西
     */
    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        if (mStore != null) {
            mDispatcher.unregister(mStore);
        }
    }

    

}
