package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.yibogame.app.RxBus;
import com.yibogame.flux.dispatcher.Dispatcher;
import com.yibogame.flux.stores.Store;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by parcool on 2016/9/3.
 */

public abstract class MyBaseFragment extends Fragment {

    protected Dispatcher mDispatcher;
    protected Store mStore;
    protected Subscription mSubscription;


    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        mDispatcher = Dispatcher.getInstance();
        if (mStore != null) {
            mDispatcher.register(mStore);
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

    }

    /***
     * 创建Store
     * @return
     */
    protected abstract Store initActionsCreatorAndStore();
    protected abstract void onStoreCall(Store.StoreChangeEvent storeChangeEvent);

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mStore != null) {
            mDispatcher.unregister(mStore);
        }
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    protected Context mContext;

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = getActivity();
    }
}
