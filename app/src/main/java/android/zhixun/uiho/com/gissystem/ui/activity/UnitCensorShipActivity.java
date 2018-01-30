package android.zhixun.uiho.com.gissystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CRCheckPersonModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.ui.adapter.CensorshipRegisterAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.UnitCensorShipAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.DividerItemDecoration;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yibogame.app.DoOnSubscriber;
import com.yibogame.flux.stores.Store;
import com.yibogame.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 单位保密检查结果页面
 */
public class UnitCensorShipActivity extends BaseActivityWithTitle {
    private XRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<CompanyDetailByCheckedModel> lists = new ArrayList<>();
    //    private DaoSession mDaoSession;
    private int unitId;//公司Id
    private String company;//公司名字
    private String name = "";
    //CRModel
    private List<CRModel> mListCRModel = new ArrayList<>();
    private List<CRCheckPersonModel> mListCheckPersonModel = new ArrayList<>();
    private UnitCensorShipAdapter mUnitCensorShipAdapter;


    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_unit_censor_ship;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("单位保密检查结果");
        unitId = getIntent().getIntExtra("company", -1);
//        mDaoSession = ((MyBaseApplication) getApplication()).getDaoSession();
//        if("重庆宝威科技有限公司".equals(company)){
//            unitId = 0;
//        }else if("重庆地质勘查有限责任公司".equals(company)){
//            unitId = 1;
//        }else {
//            unitId = 2;
//        }
        //初始化
        initView();
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_unitcensorship, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();

        switch (item_id) {
            case R.id.register:

                Intent intent = new Intent();
                intent.setClass(UnitCensorShipActivity.this, CensorshipRegisterAdapter.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    private void initView() {
        initRecyclerView();
    }

    private void getData() {
        Map<Object, Object> map = new HashMap<>();
        map.put("companyId", unitId);
        APIService.getInstance().getCompanyDetailByCheckedList(map,
                new DoOnSubscriber<List<CompanyDetailByCheckedModel>>() {
            @Override
            public void doOnSubscriber() {

            }

            @Override
            public void onCompleted() {
                mUnitCensorShipAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtil.showShort(e.getMessage());
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onNext(List<CompanyDetailByCheckedModel> companyDetailByCheckedModels) {
                lists.clear();
                lists.addAll(companyDetailByCheckedModels);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        //添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setShowFirstLine(true);
        dividerItemDecoration.setShowLastLine(true);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        layoutManager = new LinearLayoutManager(UnitCensorShipActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mUnitCensorShipAdapter = new UnitCensorShipAdapter(this, lists);
        mRecyclerView.setAdapter(mUnitCensorShipAdapter);
        mRecyclerView.setLoadingMoreEnabled(false);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
            }
        });
        mUnitCensorShipAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(UnitCensorShipActivity.this, CensorshipDetailActivity.class);
                intent.putExtra("CompanyDetailByCheckedModel", lists.get(position));
                startActivity(intent);
            }
        });
    }
}

