package android.zhixun.uiho.com.gissystem.ui.activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.HoldersModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.HoldersParentModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.ui.adapter.HolderSizeMarkAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.DividerItemDecoration;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.yibogame.flux.stores.Store;

import java.util.ArrayList;

public class HolderSizeMarkActivity extends BaseActivityWithTitle {
    private RecyclerView mRecyclerView;
    private DaoSession mDaoSession;
    private int companyId;
    private CompanyDetailModel mUnitModel;
    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<HoldersModel> holdersModels;
    private ArrayList<HoldersParentModel> holdersParentModels = new ArrayList<>();
    private String companyName;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_holder_size_mark;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("持证人");
        mDaoSession = ((MyBaseApplication)getApplication()).getDaoSession();
        MyBaseApplication myBaseApplication = (MyBaseApplication) getApplication();
//        mUnitModel = myBaseApplication.getUnitModel();
        mUnitModel = myBaseApplication.getCompanyDetailModel();
        companyId = getIntent().getIntExtra("companyId",-1);
        companyName = getIntent().getStringExtra("companyName");
        holdersModels = getIntent().getParcelableArrayListExtra("holders");
        if (holdersModels != null && !holdersModels.isEmpty()) {
            for (HoldersModel holdersModel : holdersModels) {
                HoldersParentModel holdersParentModel = new HoldersParentModel();
                holdersParentModel.setName(holdersModel.getName());
                holdersParentModel.setCardBeginTime(holdersModel.getCardBeginTime());
                holdersParentModel.setCardEndTime(holdersModel.getCardEndTime());
                holdersParentModel.setStatus(holdersModel.getStatus());
                holdersParentModel.setIsHoldCard(holdersModel.getIsHoldCard());
                ArrayList<HoldersModel> arrayList = new ArrayList<>();
                arrayList.add(holdersModel);
                holdersParentModel.setHoldersModels(arrayList);
                holdersParentModels.add(holdersParentModel);
            }
        }
        //初始化
        initViews();

    }

    private void initViews() {
        //先查询出公司的名字
//        UnitModelDao unitModelDao = mDaoSession.getUnitModelDao();
//        UnitModel unitModel = unitModelDao.queryBuilder().where(UnitModelDao.Properties.ArcGisId.eq(companyId)).build().unique();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(HolderSizeMarkActivity.this);
        //添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setShowFirstLine(true);
        dividerItemDecoration.setShowLastLine(true);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
//        final List<HolderModel> HolderModelList = new ArrayList<>();
//        if ("长江重庆航道测绘处".equals(company)) {
//            for (int i = 0; i < 5; i++) {
//                //String idCard, String phoneNumber, String eMail, String unit, String number
//                HolderDetailModel beef = new HolderDetailModel("50038219940112907" + i, "1888319269" + i, "523373965" + i + "@qq.com", mUnitModel.getCompanyName(), "H21312489127" + i);
//
//
//                //String mName, String upLoading, String state, String date, List<HolderDetailModel> mMemberModels
//                HolderModel tacok = new HolderModel("张三" + i, "证照上传", "有效", "2016-11-" + i + "——2016-12-" + i, Arrays.asList(beef));
//
//                HolderModelList.add(tacok);
//            }
//        } else if ("重庆607勘察实业总公司".equals(company)) {
//            for (int i = 0; i < 6; i++) {
//                //String idCard, String phoneNumber, String eMail, String unit, String number
//                HolderDetailModel beef = new HolderDetailModel("50038219940586425" + i, "1592319269" + i, "526873965" + i + "@qq.com",mUnitModel.getCompanyName(), "H21312489128" + i);
//
//
//                //String mName, String upLoading, String state, String date, List<HolderDetailModel> mMemberModels
//                HolderModel tacok = new HolderModel("李四" + i, "证照上传", "有效", "2016-11-" + i + "——2016-12-" + i, Arrays.asList(beef));
//
//                HolderModelList.add(tacok);
//            }
//        } else {
//            for (int i = 0; i < 3; i++) {
//                //String idCard, String phoneNumber, String eMail, String unit, String number
//                HolderDetailModel beef = new HolderDetailModel("50038219940586567" + i, "1288319269" + i, "387873965" + i + "@qq.com", mUnitModel.getCompanyName(), "H21312489523" + i);
//
//
//                //String mName, String upLoading, String state, String date, List<HolderDetailModel> mMemberModels
//                HolderModel tacok = new HolderModel("王五" + i, "证照上传", "有效", "2016-11-" + i + "——2016-12-" + i, Arrays.asList(beef));
//
//                HolderModelList.add(tacok);
//            }
//        }

        HolderSizeMarkAdapter holderSizeMarkAdapter = new HolderSizeMarkAdapter(this, holdersParentModels,companyName);

        holderSizeMarkAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onParentExpanded(int parentPosition) {


            }

            @Override
            public void onParentCollapsed(int parentPosition) {

            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(holderSizeMarkAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
