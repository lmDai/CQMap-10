package android.zhixun.uiho.com.gissystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.models.CRActiveUserModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.UserModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.ui.adapter.LocalFileAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.VerticalRecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yibogame.app.DoOnSubscriber;
import com.yibogame.flux.stores.Store;
import com.yibogame.util.LogUtil;
import com.yibogame.util.SPUtil;
import com.yibogame.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalFileActivity extends BaseActivityWithTitle {
    private VerticalRecyclerView mRecyclerView;
    private List<CompanyDetailByCheckedModel> lists = new ArrayList<>();
    private DaoSession mDaoSession;
    private LocalFileAdapter localFileAdapter;
    private CRActiveUserModel crActiveUserModel;


    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_local_file;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("本地文件");
        mDaoSession = ((MyBaseApplication) getApplication()).getDaoSession();
        addMenuString("登记", new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocalFileActivity.this, CensorshipRegisterActivity.class);
                startActivity(intent);
            }
        });

        //初始化
        initView();

    }

    private AlertDialog myDialog;

    private void initView() {
        mRecyclerView = (VerticalRecyclerView) findViewById(R.id.recyclerView);
        localFileAdapter = new LocalFileAdapter(LocalFileActivity.this, lists);
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
        mRecyclerView.setAdapter(localFileAdapter);

        localFileAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
//                intent.putExtra("tid", lists.get(position).getId().longValue());
                intent.setClass(LocalFileActivity.this, CensorshipDetailActivity.class);
                intent.putExtra("tid", lists.get(position).getCompanyId());
                intent.putExtra("CompanyDetailByCheckedModel", lists.get(position));
                startActivity(intent);

            }
        });
        localFileAdapter.setOnDeleteClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                myDialog = new AlertDialog.Builder(LocalFileActivity.this).create();
                myDialog.show();

                myDialog.getWindow().setContentView(R.layout.layout_mydialog_view);
                ((TextView) myDialog.getWindow().findViewById(R.id.toolbar_title)).setText("是否删除");
                myDialog.getWindow()
                        .findViewById(R.id.dialog_button_ok)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete(lists.get(position).getSecrecyInspectId());
                                if (myDialog != null && myDialog.isShowing()) {
                                    myDialog.dismiss();
                                }
                            }
                        });
                myDialog.getWindow()
                        .findViewById(R.id.dialog_button_cancel)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog.dismiss();
                            }
                        });


            }
        });
        //提交至本地服务器
        localFileAdapter.setOnSubmitServerClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                commitToServer(lists.get(position).getSecrecyInspectId());
            }
        });

        localFileAdapter.setmOnEditListener((view, position) -> {
            Intent intent = new Intent();
            intent.setClass(LocalFileActivity.this, CensorshipRegisterActivity.class);
            intent.putExtra("secrecyInspectId",lists.get(position).getSecrecyInspectId());
            intent.putExtra("tid", lists.get(position).getCompanyId());
            intent.putExtra("CompanyDetailByCheckedModel", lists.get(position));
            intent.putExtra("tName",lists.get(position).getCompanyName());
            startActivity(intent);
        });

    }

    /***
     * 提交到服务器
     * @param secrecyInspectId
     */
    private void commitToServer(int secrecyInspectId) {
        Map<Object, Object> map = new HashMap<>();
        map.put("secrecyInspectId", secrecyInspectId);
        APIService.getInstance().commitLocalToServer(map, new DoOnSubscriber<String>() {
            @Override
            public void doOnSubscriber() {

            }

            @Override
            public void onCompleted() {
                ToastUtil.showShort("提交成功！");
                mRecyclerView.setRefreshing(true);
            }

            @Override
            public void onNext(String s) {
                LogUtil.d("提交到服务器后返回：" + s);
            }
        });
    }

    /***
     * 删除
     * @param secrecyInspectId
     */
    private void delete(int secrecyInspectId) {
        Map<Object, Object> map = new HashMap<>();
        map.put("secrecyInspectId", secrecyInspectId);
        APIService.getInstance().delete(map, new DoOnSubscriber<String>() {
            @Override
            public void doOnSubscriber() {

            }

            @Override
            public void onCompleted() {
                ToastUtil.showShort("删除成功！");
                getData();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtil.showShort(e.getMessage());
            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    private void getData() {
        String userJson = SPUtil.getInstance().getString("UserModelOfJson");
        UserModel userModel = JSONObject.parseObject(userJson, UserModel.class);
        Map<Object, Object> map = new HashMap<>();
        map.put("userId", userModel.getUserId());
        map.put("isLocal", 0);
        APIService.getInstance().getCompanyDetailByCheckedList(map, new DoOnSubscriber<List<CompanyDetailByCheckedModel>>() {
            @Override
            public void doOnSubscriber() {

            }

            @Override
            public void onCompleted() {
                mRecyclerView.refreshComplete();
                localFileAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
//                if (e.getCause().toString().equals("0")){
                    lists.clear();
//                    LogUtil.d("这是没有数据");
//                }
                mRecyclerView.refreshComplete();
                localFileAdapter.notifyDataSetChanged();
                ToastUtil.showShort(e.getMessage());
            }

            @Override
            public void onNext(List<CompanyDetailByCheckedModel> companyDetailModels) {
                lists.clear();
                lists.addAll(companyDetailModels);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRecyclerView != null) {
            mRecyclerView.setRefreshing(true);//进入页面就自动刷新
        }
    }
}
