package com.yibogame.flux.stores;


import com.yibogame.app.RxBus;
import com.yibogame.flux.actions.Action;
import com.yibogame.flux.dispatcher.Dispatcher;

import java.io.Serializable;

/**
 * Created by parcool on 02/08/16.
 */
public abstract class Store implements Serializable{

    final Dispatcher dispatcher;

    public Store(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void emitStoreChange(StoreChangeEvent storeChangeEvent) {
        RxBus.getInstance().send(storeChangeEvent);
    }

//    abstract StoreChangeEvent onChangeEvent();
    public abstract void onAction(Action action);

    /***
     * 这个onUnRegister()是为了取消Store里的subscription，
     * 当然，如果里面没有，就可以不用管这个方法
     */
    public abstract void onUnRegister();

    public class StoreChangeEvent implements Serializable{
        private static final long serialVersionUID = -2237545958366224384L;
        private Action action;

        public StoreChangeEvent(Action action) {
            this.action = action;
        }

        public Action getAction() {
            return action;
        }
    }
}
