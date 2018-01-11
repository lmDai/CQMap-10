package com.yibogame.app;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class MyWeakReferenceHandler<T> extends Handler {

	private WeakReference<T> weakReferenceActivity = null;

	public MyWeakReferenceHandler(T t) {
		this.weakReferenceActivity = new WeakReference<>(t);
	}

	public abstract void handleMessage(Message msg, T weakReferenceActivity);

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		handleMessage(msg, weakReferenceActivity.get());
	}
}
