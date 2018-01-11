package android.zhixun.uiho.com.gissystem.rest;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by parcool on 2016/6/14.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{

    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;

    public ProgressSubscriber(Context context) {
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    @Override
    public void onStart() {
        super.onStart();
//        showProgressDialog();//需使用doOnSubscribe()替代，onStart()只适合做数据上的处理，因为onStart()它总是在 subscribeObservable 所发生的线程被调用，而不能指定线程
    }

    protected abstract void onMyNext(T t);
    protected abstract void onMyError(Throwable e);


    @Override
    public void onNext(T t) {
        if (t == null){
            dismissProgressDialog();
//            e.printStackTrace();
            onError(new Throwable("t is null"));
        }else {
            onMyNext(t);
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        dismissProgressDialog();
        onMyError(e);
    }

    protected void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }
}
