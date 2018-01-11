package android.zhixun.uiho.com.gissystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.body.InfoPushListBody;
import android.zhixun.uiho.com.gissystem.flux.custom.PushTypeId;
import android.zhixun.uiho.com.gissystem.flux.models.api.InfoPushListModel;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.adapter.ResultDeclarMessageAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.VerticalRV;
import android.zhixun.uiho.com.gissystem.ui.widget.VerticalRecyclerView;

import com.alibaba.fastjson.JSON;
import com.yibogame.flux.stores.Store;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simple on 2017/12/7.
 */

public class ResultDeclareMessageActivity extends BaseActivityWithTitle {

    VerticalRV mRecyclerView;
    ResultDeclarMessageAdapter mAdapter;
    List<InfoPushListModel> mData = new ArrayList<>();
    InfoPushListBody mBody = new InfoPushListBody();

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_result_declare_message;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        initViews();
        getData();
    }

    private void initViews() {
        PushTypeId typeId = (PushTypeId) getIntent().getSerializableExtra(DATA);
        if (typeId == null) return;
        switch (typeId) {
            case RESULT_FOLDER_UPDATE_MESSAGE:
                setTitleText("成果目录更新消息");
                break;
            case SECRECY_INSPECT_MESSAGE:
                setTitleText("保密检查消息");
                break;
            case RESULT_DECLAR_MESSAGE:
                setTitleText("成果申请消息");
                break;
            case PREPARE_MESSAGE://
                setTitleText("预审消息");
                break;
        }

        mBody.typeId = typeId.pushTyId;
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new ResultDeclarMessageAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mData.isEmpty()) return;
                Intent intent = new Intent(mContext, ResultDeclareMessageDetailActivity.class);
                intent.putExtra(ResultDeclareMessageDetailActivity.DATA, mData.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void getData() {
        APIService.getInstance()
                .getInfoPushList(JSON.toJSONString(mBody), new SimpleSubscriber<List<InfoPushListModel>>() {
                    @Override
                    public void onResponse(List<InfoPushListModel> response) {
                        mData.clear();
                        mData.addAll(response);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
