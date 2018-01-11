package com.yibogame.app;

import android.support.annotation.CallSuper;

import rx.Subscriber;

/**
 * Created by parcool on 2016/9/6.
 */

public abstract class DoOnSubscriber<T> extends Subscriber<T> {

    public abstract void doOnSubscriber();

    @CallSuper
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
