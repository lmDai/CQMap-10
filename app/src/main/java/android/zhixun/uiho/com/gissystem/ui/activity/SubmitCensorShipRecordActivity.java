package android.zhixun.uiho.com.gissystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.models.CRActiveUserModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRModel;
import android.zhixun.uiho.com.gissystem.flux.models.SubmitCensorShipRecordModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.UserModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRActiveUserModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.ui.adapter.SubmitCensorShipRecordAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.DividerItemDecoration;
import android.zhixun.uiho.com.gissystem.ui.widget.VerticalRecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yibogame.app.DoOnSubscriber;
import com.yibogame.flux.stores.Store;
import com.yibogame.util.SPUtil;
import com.yibogame.util.ToastUtil;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitCensorShipRecordActivity extends BaseActivityWithTitle {
    private VerticalRecyclerView mRecyclerView;
    private List<SubmitCensorShipRecordModel> lists = new ArrayList<>();
    private long tid;
    //    private DaoSession mDaoSession;
    private List<Long> mListCRSubmitServerModel = new ArrayList<>();//保存所有提交到服务器的tid。
//    private List<CRModel> mListCRModel = new ArrayList<>();//保存在服务器中的CRModel 的list.


    private List<CompanyDetailByCheckedModel> mCompanyDetailModels = new ArrayList<>();

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_submit_censor_ship_record;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("提交的保密检查记录");
//        mDaoSession = ((MyBaseApplication) getApplication()).getDaoSession();
        //初始化
        addMenuString("登记", new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubmitCensorShipRecordActivity.this, CensorshipRegisterActivity.class);
                startActivity(intent);
            }
        });
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_submitcensorshiprecord, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private SubmitCensorShipRecordAdapter submitCensorShipRecordAdapter;
    private void initView() {
        mRecyclerView = (VerticalRecyclerView) findViewById(R.id.recyclerView);
        submitCensorShipRecordAdapter = new SubmitCensorShipRecordAdapter(SubmitCensorShipRecordActivity.this, mCompanyDetailModels);
        //添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(SubmitCensorShipRecordActivity.this, DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setShowLastLine(false);
        dividerItemDecoration.setShowFirstLine(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
            }
        });
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(submitCensorShipRecordAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SubmitCensorShipRecordActivity.this));
        //Recyclerview 添加监听
        submitCensorShipRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //将每个item的CRModel 的id 传到保密检查详情界面
                Intent intent = new Intent(SubmitCensorShipRecordActivity.this, CensorshipDetailActivity.class);
                intent.putExtra("tid", mCompanyDetailModels.get(position).getCompanyId());
                intent.putExtra("CompanyDetailByCheckedModel", mCompanyDetailModels.get(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setRefreshing(true);
    }

    private void refreshData() {
        String userJson = SPUtil.getInstance().getString("UserModelOfJson");
        UserModel userModel = JSONObject.parseObject(userJson,UserModel.class);
        Map<Object,Object> map = new HashMap<>();
        map.put("userId",userModel.getUserId());
        map.put("isLocal",1);
        APIService.getInstance().getCompanyDetailByCheckedList(map, new DoOnSubscriber<List<CompanyDetailByCheckedModel>>() {
            @Override
            public void doOnSubscriber() {

            }

            @Override
            public void onCompleted() {
                submitCensorShipRecordAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtil.showShort(e.getMessage());
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onNext(List<CompanyDetailByCheckedModel> companyDetailModels) {
                mCompanyDetailModels.clear();
                mCompanyDetailModels.addAll(companyDetailModels);
            }
        });
    }
}
