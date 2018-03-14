package android.zhixun.uiho.com.gissystem.rest;

import android.support.annotation.NonNull;
import android.util.Log;
import android.zhixun.uiho.com.gissystem.interfaces.DownloadProgressListener;

import com.yibogame.util.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by parcool on 2016/10/14.
 */

public class DownloadAPI {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 15;
    public Retrofit retrofit;
        public static final String BASE_URL_DOWNLOAD = "http://open.uiho.com/Test/DownloadServlet/";//测试接口地址
//    public static final String BASE_URL_DOWNLOAD = "http://192.168.31.130:8080/Test/DownloadServlet/";//测试下载接口地址

    //public static final String BASE_URL_UPLOAD = "http://dili.dev.uiho.com/comm/files/up";
    public static final String BASE_URL_UPLOAD = "http://www.map023.cn:8180/comm/files/up";

    public DownloadAPI(DownloadProgressListener listener) {

        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_DOWNLOAD)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public void download(@NonNull String path, File file, Subscriber subscriber) {
        Log.d(TAG, "download: " + path);
        retrofit.create(API.class)
                .getFile(path)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        FileUtils.writeFile(file,inputStream);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Observable<File> download(@NonNull String path, File file) {
        Log.d(TAG, "download: " + path);
        return retrofit.create(API.class)
                .getFile(path)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        FileUtils.writeFile(file,inputStream);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<InputStream, File>() {
                    @Override
                    public File call(InputStream inputStream) {
                        return file;
                    }
                });
    }
}
