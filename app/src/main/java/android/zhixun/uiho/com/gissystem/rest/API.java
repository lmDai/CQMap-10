package android.zhixun.uiho.com.gissystem.rest;


import android.zhixun.uiho.com.gissystem.flux.models.FruitListModel;
import android.zhixun.uiho.com.gissystem.flux.models.GethandoutConditionByFCModel;
import android.zhixun.uiho.com.gissystem.flux.models.HandoutFruitModel;
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

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

import static android.zhixun.uiho.com.gissystem.rest.APIService.BASE_URL;
import static android.zhixun.uiho.com.gissystem.rest.DownloadAPI.BASE_URL_DOWNLOAD;
import static android.zhixun.uiho.com.gissystem.rest.DownloadAPI.BASE_URL_UPLOAD;

/**
 * Created by parcool on 2016/9/5.
 */

public interface API {
    @GET(BASE_URL_DOWNLOAD)
//    @Headers({"Content-Type: image/jpeg"})
    Observable<ResponseBody> getFile(@Field("filepath") String filepath);


    @Multipart
    @POST(BASE_URL_UPLOAD)
    Observable<String> uploadFile(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST(BASE_URL_UPLOAD)
    Observable<ResponseBody> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseResultModel<UserModel>> login(@Field("data") String data);

    /***
     * 获取单位列表
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseListResultModel<CompanyDetailModel>> getCompanyList(@Field("data") String data);


    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseResultModel<CompanyDetailModel>> getCompanyDetail(@Field("data") String data);

    /***
     * 获取区县列表
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseListResultModel<AreaModel>> getAreaList(@Field("data") String data);

    /***
     * 获取行业类别列表
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseListResultModel<IndustryCategoryModel>> getIndustryCategoryList(@Field("data") String data);


    /***
     * 查询成果分发的订单/报件的列表
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseListResultModel<ReportHandoutListModel>> getOrderList(@Field("data") String data);

    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseListResultModel<OrderModel>> getOrderList_handout(@Field("data") String data);

    /***
     * 获取订单(报件)详情
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseResultModel<OrderDetailModel>> getOrderDetail(@Field("data") String data);


    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseResultModel<AchievementModel>> getAchievementDetails(@Field("data") String data);


    /***
     * 查询所有参检人员
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseListResultModel<CheckUserModel>> getCheckUserList(@Field("data") String data);


    /***
     * 通过单位ID查询成果种类与其类型下面共有几条数据
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseListResultModel<AchievementTypeAndCountModel>> getAchievementTypeAndCountList(@Field("data") String data);


    /***
     * 保密检查登记
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseResultModel<String>> commitCheck(@Field("data") String data);


    /***
     * 查询保密检查单位列表与详情
     * @param data
     * @return
     */
    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseListResultModel<CompanyDetailByCheckedModel>> getCompanyDetailByCheckedList(@Field("data") String data);


    /***
     * 导出
     * @param data
     * @return
     */
    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseResultModel<String>> export(@Field("data") String data);


    /***
     * 删除保存到'本地'的登记
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(BASE_URL)
    Observable<BaseResultModel<String>> delete(@Field("data") String data);


    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseResultModel<String>> commitLocalToServer(@Field("data") String data);


    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseListResultModel<InfoPushListModel>> getInfoPushList(@Field("data") String data);

    /**
     * 获取成果分类
     */
    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseListResultModel<FruitCategoryListModel>> getfruitCategoryList(@Field("data") String data);

    /**
     * 获取成果种类下面的子类
     */
    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseListResultModel<GethandoutConditionByFCModel>> gethandoutConditionByFCList(@Field("data") String data);

    /**
     * 成果分发查询-按成果类型
     */
    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseListResultModel<ReportHandoutListModel>> getReportHandoutList(@Field("data") String data);

    /**
     * 成果详情（分发）
     */
    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseResultModel<HandoutFruitModel>> getHandoutFruit(@Field("data") String data);

    /**
     * 成果目录列表
     */
    @POST(BASE_URL)
    @FormUrlEncoded
    Observable<BaseListResultModel<FruitListModel>> getFruitList(@Field("data") String data);
}
