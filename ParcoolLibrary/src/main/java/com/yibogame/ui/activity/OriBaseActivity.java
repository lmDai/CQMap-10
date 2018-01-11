package com.yibogame.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yibogame.R;
import com.yibogame.app.RxBus;
import com.yibogame.flux.dispatcher.Dispatcher;
import com.yibogame.flux.stores.Store;

import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by parcool on 2016/4/15.
 */
public abstract class OriBaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected LinearLayout llLoading;
    protected RelativeLayout rlContainer;
    protected AppBarLayout appBarLayout;
    protected Dispatcher mDispatcher;
    protected Subscription mSubscription;
    private Store mStore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int contentViewResource = getContentViewResource();
        if (contentViewResource == 0) {
            setContentView(R.layout.activity_base);
        } else {
            setContentView(contentViewResource);
        }
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

        //添加toolbar
        initToolBar();
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        rlContainer = (RelativeLayout) findViewById(R.id.rl_content_container);
    }

    protected abstract int getContentViewResource();
    protected abstract void initToolBar();
    protected abstract Store initActionsCreatorAndStore();
    protected abstract void onStoreCall(Store.StoreChangeEvent storeChangeEvent);


    protected RelativeLayout getRootContentView() {
        return (RelativeLayout) findViewById(R.id.rl_content_container);
    }

    protected void showContent() {
        rlContainer.setVisibility(View.VISIBLE);
        llLoading.setVisibility(View.GONE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(300);
        alphaAnimation.setFillAfter(true);
        rlContainer.startAnimation(alphaAnimation);
    }

    protected void enableLoading() {
        rlContainer.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
    }

    /***
     * 注意：销毁flux中一系列的东西
     */
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
