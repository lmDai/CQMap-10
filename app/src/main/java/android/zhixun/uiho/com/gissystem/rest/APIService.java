package android.zhixun.uiho.com.gissystem.rest;

import android.text.TextUtils;
import android.zhixun.uiho.com.gissystem.app.ServerHttpException;
import android.zhixun.uiho.com.gissystem.flux.body.ReportHandoutListBody;
import android.zhixun.uiho.com.gissystem.flux.models.GethandoutConditionByFCModel;
import android.zhixun.uiho.com.gissystem.flux.models.RXProgress;
import android.zhixun.uiho.com.gissystem.flux.models.ReportHandoutListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.AchievementModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.AchievementTypeAndCountModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.BaseListResultModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.BaseResultModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CheckUserModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.FruitCategoryListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.IndustryCategoryModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.InfoPushListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.OrderDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.OrderModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.UserModel;
import android.zhixun.uiho.com.gissystem.interfaces.ProgressListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yibogame.app.DoOnSubscriber;
import com.yibogame.app.FastJsonConverterFactory;
import com.yibogame.util.LogUtil;
import com.yibogame.util.SPUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

//import com.yibogame.app.FastJsonConverterFactory;
//import com.yibogame.app.FastJsonConverterFactory;


/**
 * Created by parcool on 2016/9/4.
 */

public class APIService {
    private static final String SUCCESS = "1";
//    private static final String NO_DATA = "0";
//    private static final String FAILED = "-1";
//    private static final String ILLEGAL_ARGUMENT = "-2";
//    private static final String UNKNOW_ERROR = "-3";RestfulAPI
    /***
     * 网络连接超时时间(单位：秒)
     */
    public static final int DEFAULT_TIMEOUT = 59;
    //    public static final String BASE_URL = "http://open.uiho.com/Test/TestServlet/";//测试接口地址
//    public static final String BASE_URL = "http://dili.dev.uiho.com/info/InfoServlet/";
    public static final String BASE_URL = "http://www.map023.cn:8080/info/InfoServlet/";
    //    public static final String BASE_URL = "http://192.168.31.139:8080/dlxx_appx/InfoServlet/";
    //            public static final String BASE_URL = "http://192.168.31.130:8080/Test/TestServlet/";//测试接口地址
    public static final String APP_ID = "UIHOKX161024", APP_KEY = "lwgdta91eh3nxzc7";
    private static API api;
    private Retrofit restAdapter;

    private APIService() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Android")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                        .build();
                return chain.proceed(newRequest);
            }
        });

        restAdapter = new Retrofit.Builder()
                .client(httpClientBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
//                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        api = restAdapter.create(API.class);
    }

    private static APIService apiService = new APIService();

    public static APIService getInstance() {
        return apiService;
    }


    public <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }

    public <T> Observable.Transformer<BaseResultModel<T>, T> handleResponse() {
        return new Observable.Transformer<BaseResultModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResultModel<T>> baseResultModelObservable) {
                return baseResultModelObservable.flatMap(new Func1<BaseResultModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseResultModel<T> tBaseResultModel) {
                        LogUtil.i("返回结果:" + JSON.toJSONString(tBaseResultModel));
                        try {
                            if (!tBaseResultModel.getResponseCode().equals(SUCCESS)) {
                                return Observable.error(new ServerHttpException(tBaseResultModel.getResponseMsg(), new Throwable(tBaseResultModel.getResponseCode())));
                            }
                        } catch (Exception e) {
                            return Observable.error(new ServerHttpException(e.getMessage()));
                        }
                        return Observable.just(tBaseResultModel.getObject());
                    }
                });
            }
        };
    }

    public <T> Observable.Transformer<BaseListResultModel<T>, List<T>> handleResponseList() {
        return baseListResultModelObservable ->
                baseListResultModelObservable.flatMap(tBaseResultModel -> {
//            LogUtil.i("返回结果:" + JSON.toJSONString(tBaseResultModel));
                    try {
                        if (!tBaseResultModel.getResponseCode().equals(SUCCESS)) {
                            return Observable.error(new ServerHttpException(tBaseResultModel.getResponseMsg()));
                        }
                    } catch (Exception e) {
                        return Observable.error(new ServerHttpException(e.getMessage()));
                    }
                    return Observable.just(tBaseResultModel.getObject());
                });
    }


    public Observable.Transformer<ResponseBody, InputStream> handleDownload() {
        return new Observable.Transformer<ResponseBody, InputStream>() {
            @Override
            public Observable<InputStream> call(Observable<ResponseBody> responseBodyObservable) {
                return responseBodyObservable.flatMap(new Func1<ResponseBody, Observable<InputStream>>() {
                    @Override
                    public Observable<InputStream> call(ResponseBody responseBody) {
                        return Observable.just(responseBody.byteStream());
                    }
                });
            }
        };
    }


    /***
     * 处理observable
     *
     * @param observable 原始Observable
     * @param subscriber ProgressSubscriber
     * @param <T>        结果Model
     * @return 转化后的Observable
     */
//    public <T> Observable<T> work(Observable<BaseResultModel<T>> observable, final Subscriber<T> subscriber) {
//        return observable.compose(applySchedulers())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        if (subscriber instanceof ProgressSubscriber) {
//                            ((ProgressSubscriber) subscriber).showProgressDialog();
//                        }
//                    }
//                })
//                .flatMap(new Func1<BaseResultModel<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(BaseResultModel<T> tBaseResultModel) {
//                        LogUtil.i("返回结果:" + JSON.toJSONString(tBaseResultModel));
//                        try {
//                            if (!tBaseResultModel.getResponseCode().equals(SUCCESS)) {
//                                return Observable.error(new ServerHttpException(tBaseResultModel.getResponseMsg()));
//                            }
//                        } catch (Exception e) {
//                            return Observable.error(new ServerHttpException(e.getMessage()));
//                        }
//                        return Observable.just(tBaseResultModel.getObject());
//                    }
//                });
//    }


    /***
     * 构建params(也就是把map+action+target)
     *
     * @param params
     * @param action
     * @param target
     * @return
     */
    private String buildParams(Map<Object, Object> params, String action, String target) {
        Map<Object, Object> mapTemp = new HashMap<>();
        mapTemp.put("action", action);
        mapTemp.put("appid", APP_ID);
        mapTemp.put("appkey", APP_KEY);
        mapTemp.put("datetime", System.currentTimeMillis());
        mapTemp.put("target", target);
        mapTemp.put("source", "android");
        mapTemp.put("params", params);
        LogUtil.d("请求参数:" + JSON.toJSONString(mapTemp));
        return JSON.toJSONString(mapTemp);
    }


    private String buildParamsWithToken(Map<Object, Object> params, String action, String target) {
        Map<Object, Object> mapTemp = new HashMap<>();
        mapTemp.put("action", action);
        mapTemp.put("appid", APP_ID);
        mapTemp.put("appkey", APP_KEY);
        mapTemp.put("datetime", System.currentTimeMillis());
        mapTemp.put("target", target);
        mapTemp.put("source", "android");
        mapTemp.put("params", params);
        String userJson = SPUtil.getInstance().getString("UserModelOfJson");
        UserModel userModel = JSONObject.parseObject(userJson, UserModel.class);
        mapTemp.put("token", userModel.getToken());


        LogUtil.d("请求参数:" + JSON.toJSONString(mapTemp));
        return JSON.toJSONString(mapTemp);
    }


    private String buildParams(String params, String action, String target) {
        Map<Object, Object> mapTemp = new HashMap<>();
        mapTemp.put("action", action);
        mapTemp.put("appid", APP_ID);
        mapTemp.put("appkey", APP_KEY);
        mapTemp.put("datetime", System.currentTimeMillis());
        mapTemp.put("target", target);
        mapTemp.put("source", "android");
        mapTemp.put("params", params);
        LogUtil.d("请求参数:" + JSON.toJSONString(mapTemp));
        return JSON.toJSONString(mapTemp);
    }

    private String buildParams(int params, String action, String target) {
        Map<Object, Object> mapTemp = new HashMap<>();
        mapTemp.put("action", action);
        mapTemp.put("appid", APP_ID);
        mapTemp.put("appkey", APP_KEY);
        mapTemp.put("datetime", System.currentTimeMillis());
        mapTemp.put("target", target);
        mapTemp.put("source", "android");
        mapTemp.put("params", params);
        LogUtil.d("请求参数:" + JSON.toJSONString(mapTemp));
        return JSON.toJSONString(mapTemp);
    }

    public Observable<ResponseBody> upload(String filePath, String classSimpleName) {
        File file = new File(filePath);
        return upload(file, classSimpleName);
    }

    public Observable<ResponseBody> upload(File file, String classSimpleName) {
        if (!file.getAbsoluteFile().exists()) {
            LogUtil.d(file.getName() + "," + file.toString() + "," + file.length());
            return Observable.error(new Throwable("file not exists!" + file.getAbsolutePath()));
        }

//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        UploadProgressRequestBody requestBody = new UploadProgressRequestBody(MediaType.parse("multipart/form-data"), file, new ProgressListener() {
            private RXProgress rxProgress = null;
            int preProgress;

            @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {
                if (rxProgress == null) {
                    preProgress = 0;
                    rxProgress = new RXProgress();
                    rxProgress.setClassSimpleName(classSimpleName);
                    rxProgress.setProgress((int) (((float) bytesRead / contentLength * 100)));
                } else {
                    preProgress = rxProgress.getProgress();
                    rxProgress.setProgress((int) (((float) bytesRead / contentLength * 100)));
                }
                LogUtil.i("rxProgress.getProgress()=" + rxProgress.getProgress() + ",preProgress=" + preProgress);
                if (rxProgress.getProgress() > preProgress && rxProgress.getProgress() < 100) {
//                    RxBus.getInstance().send(rxProgress);
                }
            }
        });
        MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        String descriptionString = "This is a description";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        return api.upload(description, multipartBodyPart);//.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
///Users/tanyi/android/sdk

    /***
     * 获取公司列表
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription getCompanyList(Map<Object, Object> map, DoOnSubscriber<List<CompanyDetailModel>> subscriber) {
        return api.getCompanyList(buildParams(map, "getCompanyList", "company"))
                .compose(applySchedulers())
                .compose(handleResponseList())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .subscribe(subscriber);
    }


    /***
     * 获取单位详情
     * @param key
     * @param subscriber
     * @return
     */
    public Subscription getCompanyDetail(String key, DoOnSubscriber<CompanyDetailModel> subscriber) {
        return api.getCompanyDetail(buildParams(key, "getCompanyInfoById", "company"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponse())
                .subscribe(subscriber);
    }

    /***
     * 获取单位详情Observable方式
     * @param key
     * @return
     */
    public Observable<CompanyDetailModel> getCompanyDetailObservable(String key) {
        return api.getCompanyDetail(buildParams(key, "getCompanyInfoById", "company"))
//                .compose(applySchedulers())
                .compose(handleResponse());
    }


    /***
     * 登录接口撒
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription login(Map<Object, Object> map, DoOnSubscriber<UserModel> subscriber) {
        return api.login(buildParams(map, "login", "user"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponse())
                .subscribe(subscriber);
    }

    /***
     * 获取重庆各大区县
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription getAreaList(Map<Object, Object> map, DoOnSubscriber<List<AreaModel>> subscriber) {
        return api.getAreaList(buildParams(map, "getAllArea", "area"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }


    /***
     * 通过单位ID查询成果类型与其类型的数量
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription getAchievementTypeAndCountList(Map<Object, Object> map, DoOnSubscriber<List<AchievementTypeAndCountModel>> subscriber) {
        return api.getAchievementTypeAndCountList(buildParams(map, "getFruitCategoryList", "fruitCategory"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }

    /***
     * 查询成果分发的订单/报件的列表
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription getOrderList(Map<Object, Object> map, DoOnSubscriber<List<OrderModel>> subscriber) {
        return api.getOrderList(buildParams(map, "handoutList", "handout"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }

    /***
     * 查询成果分发的订单/报件的列表
     * @param map
     * @return
     */
    public Observable<List<OrderModel>> getOrderListObservable(Map<Object, Object> map) {
        return api.getOrderList(buildParams(map, "handoutList", "handout"))
//                .compose(applySchedulers())
//                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList());
//                .subscribe(subscriber);
    }


    /***
     * 获取行业类别
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription getIndustryCategoryList(Map<Object, Object> map, DoOnSubscriber<List<IndustryCategoryModel>> subscriber) {
        return api.getIndustryCategoryList(buildParams(map, "getAll", "industryCategory"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }

    /***
     * 通过单位ID查询成果
     * @param handoutId
     * @return
     */
    public Observable<AchievementModel> getAchievementDetails(String handoutId) {
        return api.getAchievementDetails(buildParams(handoutId, "getHandoutInfo", "handout"))
                .compose(handleResponse());
    }


    /***
     * 获取成果详情
     * @param handoutId
     * @param subscriber
     * @return
     */
    public Subscription getAchievementDetails(String handoutId, DoOnSubscriber<AchievementModel> subscriber) {
        return api.getAchievementDetails(buildParams(handoutId, "getHandoutInfo", "handout"))
                .compose(applySchedulers())
                .compose(handleResponse())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .subscribe(subscriber);
    }

    /***
     *
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription getCheckUserList(Map<Object, Object> map, DoOnSubscriber<List<CheckUserModel>> subscriber) {
        return api.getCheckUserList(buildParams(map, "getUserList", "user"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }


    /***
     * 提交保密检查
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription commitCheck(Map<Object, Object> map, DoOnSubscriber<String> subscriber) {
        return api.commitCheck(buildParamsWithToken(map, "setSecrecyInspect", "secrecyInspect"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponse())
                .subscribe(subscriber);
    }


    /***
     * 获取单位的保密检查记录详情（其实是列表，他把详情也返回回来了）
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription getCompanyDetailByCheckedList(Map<Object, Object> map, DoOnSubscriber<List<CompanyDetailByCheckedModel>> subscriber) {
        return api.getCompanyDetailByCheckedList(buildParams(map, "getSecrecyInspectList", "secrecyInspect"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }

    /***
     * 导出PDF
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription export(Map<Object, Object> map, DoOnSubscriber<String> subscriber) {
        return api.export(buildParams(map, "getSecrecyInspectToPDF", "secrecyInspect"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponse())
                .subscribe(subscriber);
    }

    /***
     * 删除本地的保密检查记录
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription delete(Map<Object, Object> map, DoOnSubscriber<String> subscriber) {
        return api.delete(buildParams(map, "delSecrecyInspect", "secrecyInspect"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponse())
                .subscribe(subscriber);
    }

    /***
     * 提交到服务器
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription commitLocalToServer(Map<Object, Object> map, DoOnSubscriber<String> subscriber) {
        return api.commitLocalToServer(buildParamsWithToken(map, "submitSecrecyInspect", "secrecyInspect"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponse())
                .subscribe(subscriber);
    }


    /***
     * 获取订单(报件)详情
     * @param ordIdStr
     * @param subscriber
     * @return
     */
    public Subscription getOrderDetail(int ordIdStr, DoOnSubscriber<OrderDetailModel> subscriber) {
        return api.getOrderDetail(buildParams(ordIdStr, "getReportByHandout", "report"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponse())
                .subscribe(subscriber);
    }

    public Subscription getInfoPushList(String paramsJson, DoOnSubscriber<List<InfoPushListModel>> subscriber) {
        return api.getInfoPushList(buildParams(paramsJson, "getInfoPushList", "infoPush"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }

    /**
     * 获取成果种类,分类查询
     */
    public Subscription getfruitCategoryList(DoOnSubscriber<List<FruitCategoryListModel>> subscriber) {
        return api.getfruitCategoryList(buildParams("{}", "fruitCategoryList", "fruit"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }

    /**
     * 获取成果种类下面的子类
     */
    public Subscription gethandoutConditionByFCList(long fruitCategoryId,
                                                    DoOnSubscriber<List<GethandoutConditionByFCModel>> subscriber) {
        return api.gethandoutConditionByFCList(
                buildParams("{\"fruitCategoryId\": " + fruitCategoryId + "}",
                        "gethandoutConditionByFC", "fruit"))
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }

    /**
     * 成果分发查询-按成果类型
     */
    public Subscription getReportHandoutList(ReportHandoutListBody body,
                                             DoOnSubscriber<List<ReportHandoutListModel>> subscriber) {
        Map<Object, Object> map = new HashMap<>();
        map.put("attrValueList", body.attrValueList);
        if (body.fruitCategoryId != -1) {
            map.put("fruitCategoryId", body.fruitCategoryId);
        }
        if (!TextUtils.isEmpty(body.fruitIds)) {
            map.put("attrValueList", body.fruitIds);
        }
        if (body.mapNum != -1) {
            map.put("mapNum", body.mapNum);
        }
        String data = buildParams(map, "reportHandoutList", "report");
        return api.getReportHandoutList(data)
                .compose(applySchedulers())
                .doOnSubscribe(subscriber::doOnSubscriber)
                .compose(handleResponseList())
                .subscribe(subscriber);
    }


//    /***
//     * 登录
//     *
//     * @param map
//     * @param subscriber
//     * @return
//     */
//    public Subscription login(Map<Object, Object> map, DoOnSubscriber<UserModel> subscriber) {
//        return api.login(buildParams(map, "login", "user"))
//                .doOnSubscribe(subscriber::doOnSubscriber)
//                .compose(applySchedulers())
//                .compose(handleResponse())
//                .subscribe(subscriber);
//    }
//
//    public Subscription getNewsList(Map<Object, Object> map, DoOnSubscriber<List<NewsModel>> subscriber) {
//        return api.getNewsList(buildParams(map, "list", "news"))
//                .doOnSubscribe(subscriber::doOnSubscriber)
//                .compose(applySchedulers())
//                .compose(handleResponseList())
//                .subscribe(subscriber);
//    }
//
//    public Subscription checkUpdate(Map<Object, Object> map, DoOnSubscriber<CheckUpdateVersionModel> subscriber) {
//        return api.checkUpdate(buildParams(map, "checkNew", "version"))
//                .doOnSubscribe(subscriber::doOnSubscriber)
//                .compose(applySchedulers())
//                .compose(handleResponse())
//                .subscribe(subscriber);
//    }
//
//    public Subscription getMessageList(Map<Object, Object> map, DoOnSubscriber<List<MessageModel>> subscriber) {
//        return api.getMessageList(buildParams(map, "list", "message"))
//                .doOnSubscribe(subscriber::doOnSubscriber)
//                .compose(applySchedulers())
//                .compose(handleResponseList())
//                .subscribe(subscriber);
//    }
//
//    public Subscription getMsgList(Map<Object, Object> map, String target, String action, DoOnSubscriber<List<MsgModel>> subscriber) {
//        return api.getMsgList(buildParams(map, action, target))
//                .doOnSubscribe(subscriber::doOnSubscriber)
//                .compose(applySchedulers())
//                .compose(handleResponseList())
//                .subscribe(subscriber);
//    }
//
//    public Subscription getPluginList(Map<Object, Object> map, String target, String action, DoOnSubscriber<List<PluginModel>> subscriber) {
//        return api.getPluginList(buildParams(map, action, target))
//                .doOnSubscribe(subscriber::doOnSubscriber)
//                .compose(applySchedulers())
//                .compose(handleResponseList())
//                .subscribe(subscriber);
//    }
//
////    public Subscription getFile(String filePath, DoOnSubscriber<InputStream> subscriber) {
////        return api.getFile(filePath)
////                .doOnSubscribe(subscriber::doOnSubscriber)
////                .compose(applySchedulers())
////                .compose(handleDownload())
////                .subscribe(subscriber);
////    }
//
////    public Subscription getMapUrl(Map<Object,Object> map, String target, String action, DoOnSubscriber<MapZipModel> subscriber){
////        return api.getMapUrl(buildParams(map,action,target))
////                .doOnSubscribe(subscriber::doOnSubscriber)
////                .compose(applySchedulers())
////                .compose(handleResponse())
////                .subscribe(subscriber);
////    }
//
//    public Observable<MapZipModel> getMapUrl(Map<Object,Object> map, String target, String action, OnlyDoOnSubscriber<MapZipModel> subscriber){
//        return api.getMapUrl(buildParams(map,action,target))
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        subscriber.doOnSubscriber();
//                    }
//                })
//                .compose(applySchedulers())
//                .compose(handleResponse());
//    }

}
