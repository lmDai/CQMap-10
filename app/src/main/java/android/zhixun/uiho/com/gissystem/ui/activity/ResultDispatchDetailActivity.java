package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.models.AchievementModel;
import android.zhixun.uiho.com.gissystem.flux.models.UnitModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.OrderDetailModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.AchievementModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.greendao_gen.UnitModelDao;
import android.zhixun.uiho.com.gissystem.rest.APIService;

import com.yibogame.app.DoOnSubscriber;
import com.yibogame.flux.stores.Store;
import com.yibogame.util.DateUtil;

import org.greenrobot.greendao.query.Query;

import java.util.HashMap;
import java.util.Map;

public class ResultDispatchDetailActivity extends BaseActivityWithTitle {
    private TextView tvUnitNameContent;//单位名称
    private TextView tvBjNumberContent;//报件编号
    private TextView tvHandlePeopleContent;//经办人
    private TextView tvHandlePeopleCardContent;//经办人身份证
    private TextView tvHandlePeoplePhoneContent;//经办人联系电话
    private TextView tvLetterOfuthorityContent;//证明函机关
    private TextView tvLetterOfAuthorityHandlerContent;//证明函承办人
    private TextView tvLetterOfAuthorityPhoneContent;//证明函联系电话
    private TextView tvLetterOfAuthorityAddressContent;//证明函详细地址
    private TextView tvProjectPurposeContent;//项目使用目的
    private TextView tvProjectFormContent;//项目来源
    private TextView tvProjectStartTimeContent;//项目开始时间
    private TextView tvProjectEndContent;//项目结束时间
    private TextView tvBjTimeContent;//报件时间
    private long id, mHandoutId;
    private DaoSession mDaoSession;

    private String companyName;

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_result_dispatch_detail;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        //设置标题
        setTitleText("报件详情");
        id = getIntent().getLongExtra("company",-1);
        mHandoutId = getIntent().getIntExtra("HandoutId",-1);
        companyName = getIntent().getStringExtra("companyName");
        mDaoSession =((MyBaseApplication)getApplication()).getDaoSession();
        //初始化
        initViews();
    }

    private void initViews() {
        tvUnitNameContent = (TextView) findViewById(R.id.tv_unit_name_content);
        tvBjNumberContent = (TextView) findViewById(R.id.tv_bj_number_content);
        tvHandlePeopleContent = (TextView) findViewById(R.id.tv_handle_people_content);
        tvHandlePeopleCardContent = (TextView) findViewById(R.id.tv_handle_people_card_content);
        tvHandlePeoplePhoneContent = (TextView) findViewById(R.id.tv_handle_people_phone_content);
        tvLetterOfuthorityContent = (TextView) findViewById(R.id.tv_letter_of_authority_content);
        tvLetterOfAuthorityHandlerContent = (TextView) findViewById(R.id.tv_letter_of_authority_handler_content);
        tvLetterOfAuthorityPhoneContent = (TextView) findViewById(R.id.tv_letter_of_authority_phone_content);
        tvLetterOfAuthorityAddressContent = (TextView) findViewById(R.id.tv_letter_of_authority_address_content);
        tvProjectPurposeContent = (TextView) findViewById(R.id.tv_Project_purpose_content);
        tvProjectFormContent = (TextView) findViewById(R.id.tv_Project_form_content);
        tvProjectStartTimeContent = (TextView) findViewById(R.id.tv_Project_start_time_content);
        tvProjectEndContent = (TextView) findViewById(R.id.tv_Project_end_content);
        tvBjTimeContent = (TextView) findViewById(R.id.tv_bj_time_content);

        APIService.getInstance().getOrderDetail((int)mHandoutId, new DoOnSubscriber<OrderDetailModel>() {
            @Override
            public void doOnSubscriber() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(OrderDetailModel orderDetailModel) {
                tvUnitNameContent.setText(companyName);
                String BJCode = String.valueOf(orderDetailModel.getReportId());
                tvBjNumberContent.setText(BJCode);
                //报件时间
                String BJTime = DateUtil.longToString("yyyy-MM-dd",orderDetailModel.getEncryptionTimeOk());
                tvBjTimeContent.setText(BJTime);
                tvHandlePeopleContent.setText(orderDetailModel.getHandleUser().getPersonName());//经办人
//                tvHandlePeopleCardContent.setText(orderDetailModel.getHandleUser().getPersonName());//经办人身份证
                tvHandlePeoplePhoneContent.setText(orderDetailModel.getHandleUser().getPhone());//经办人联系电话
                tvLetterOfuthorityContent.setText(orderDetailModel.getCertificationLetterCompany().getUnitName());//证明函机关
                tvLetterOfAuthorityHandlerContent.setText(orderDetailModel.getCertificationLetterCompany().getPersonName() );//证明函承办人
                tvLetterOfAuthorityPhoneContent.setText(orderDetailModel.getCertificationLetterCompany().getPhone());//证明函联系电话
                tvLetterOfAuthorityAddressContent.setText(orderDetailModel.getCertificationLetterCompany().getAddres());//证明函详细地址
                tvProjectPurposeContent.setText(orderDetailModel.getPurposeUse());//项目使用目的
                tvProjectFormContent.setText(orderDetailModel.getSourceUse());//项目来源
                tvProjectStartTimeContent.setText(DateUtil.longToString(DateUtil.yyyyMMDD,orderDetailModel.getBenginTime()));//项目开始时间
                tvProjectEndContent.setText(DateUtil.longToString(DateUtil.yyyyMMDD,orderDetailModel.getEndTime()));//项目结束时间
            }
        });

//        UnitModelDao unitModelDao = mDaoSession.getUnitModelDao();
//        UnitModel unitModel = unitModelDao.queryBuilder().where(UnitModelDao.Properties.Id.eq(id)).build().unique();
//        String comPanyName = unitModel.getName();

    }
}
