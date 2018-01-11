package com.yibogame.app;

import android.support.annotation.CallSuper;

import rx.Subscriber;

/**
 * Created by parcool on 2016/9/6.
 */

public abstract class OnlyDoOnSubscriber<T> extends Subscriber<T> {

    public abstract void doOnSubscriber();

    @Override
    public void onCompleted() {

    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {

    }
}
