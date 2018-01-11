package android.zhixun.uiho.com.gissystem.rest;

import com.yibogame.app.DoOnSubscriber;

/**
 * Created by simple on 2017/12/12.
 */

public abstract class SimpleSubscriber<T> extends DoOnSubscriber<T> {

    @Override
    public void doOnSubscriber() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        onResponse(t);
    }

    public abstract void onResponse(T response);
}
