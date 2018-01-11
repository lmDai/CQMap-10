package com.yibogame.flux.dispatcher;


import com.yibogame.app.RxBus;
import com.yibogame.flux.actions.Action;
import com.yibogame.flux.stores.Store;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by parcool on 2016/8/19.
 */
public class Dispatcher {
    private static Dispatcher instance;

    private Map<Store, Subscription> mStoreSubscriptionHashMap = new HashMap<>();

    public static Dispatcher getInstance() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    private Dispatcher() {}

    public void register(final Store store) {
        if (null == store) {
            throw new IllegalArgumentException("the mStore can't be null");
        }

        Subscription subscription = Observable.defer(new Func0<Observable<Action>>() {
            @Override
            public Observable<Action> call() {
                return RxBus.getInstance().toObservable(Action.class);
            }
        }).subscribe(new Action1<Action>() {
            @Override
            public void call(Action action) {
                store.onAction(action);
            }
        });
        mStoreSubscriptionHashMap.put(store, subscription);
    }

    public void register(Store... stores) {
        if (null == stores || stores.length == 0) {
            throw new IllegalArgumentException("the mStore array is null or empty");
        }
        register(Arrays.asList(stores));
    }

    public void register(List<Store> stores) {
        if (null == stores || stores.isEmpty()) {
            throw new IllegalArgumentException("the mStore list is null or empty");
        }
        Observable.from(stores).forEach(new Action1<Store>() {
            @Override
            public void call(Store baseStore) {
                register(baseStore);
            }
        });
    }

    /***
     * 取消订阅
     *
     * @param store
     */
    public void unregister(Store store) {
        if (null == store) {
            throw new IllegalArgumentException("the mStore can't be null");
        }

        //获取到订阅者
        Subscription subscription = mStoreSubscriptionHashMap.get(store);

        //如果没有取消订阅
        if (subscription != null && !subscription.isUnsubscribed()) {
            //取消订阅
            subscription.unsubscribe();
        }
        store.onUnRegister();
        mStoreSubscriptionHashMap.remove(store);
    }

    /**
     * 取消订阅
     *
     * @param stores BaseStore[]
     */
    public void unregister(Store... stores) {
        if (null == stores || stores.length == 0) {
            throw new IllegalArgumentException("the mStore array is null or empty");
        }
        unregister(Arrays.asList(stores));
    }

    /**
     * 取消订阅
     *
     * @param stores List<BaseStore>
     */
    public void unregister(List<Store> stores) {
        if (null == stores || stores.isEmpty()) {
            throw new IllegalArgumentException("the mStore list is null or empty");
        }
        for (Store store : stores) {
            unregister(store);
        }
    }

    private boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }

    /**
     * 分发action
     *
     * @param action BaseAction child instance
     */
    public void dispatch(Action action) {
        if (null == action) {
            throw new IllegalArgumentException("the action can't be null");
        }
        RxBus.getInstance().send(action);
    }

    public void dispatch(String type,Map<String,Object> hashMap){
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }
        RxBus.getInstance().send(new Action(type,hashMap));
    }


    /***
     * 待验证！！！！
     *
     * @param type
     * @param data
     */
    public void dispatch(String type, Object... data) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(type);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        RxBus.getInstance().send(actionBuilder.build());
    }

    /**
     * sticky 模式分发action
     *
     * @param action BaseAction
     */
    public void dispatchSticky(Action action) {
        if (null == action) {
            throw new IllegalArgumentException("the action can't be null");
        }
        RxBus.getInstance().sendSticky(action);
    }
}
