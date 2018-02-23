package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.OrderModel;
import android.zhixun.uiho.com.gissystem.rest.APIService;

import com.yibogame.app.DoOnSubscriber;
import com.yibogame.flux.stores.Store;
import com.yibogame.util.DateUtil;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/***
 * 拥有的涉密测绘成果
 */
public class OwnSecretResultActivity extends BaseActivityWithTitle {

    private RecyclerView mRecyclerView;//recyclerView
    private long companyId;//从地图上查到的公司的数据
    //    private List<OwnSMCHResultItem> listOrders = new ArrayList<>();
//    private List<String> listString = new ArrayList<>();
    private List<OrderModel> orderModelList = new ArrayList<>();

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_own_secret_result;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        //设置标题
        ToastUtil.showShort("拥有的涉密测绘成果");
        setTitleText("拥有的涉密测绘成果");
        companyId = getIntent().getLongExtra("company", -1);
        LogUtil.e("-----------" + companyId);
        //初始化
        initViews();
    }

    private void initViews() {

        Map<Object, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        APIService.getInstance().getOrderListObservable(map)
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DoOnSubscriber<OrderModel>() {
                    @Override
                    public void doOnSubscriber() {

                    }

                    @Override
                    public void onCompleted() {
                        initRecyclerView();
                        refresh();
                    }

                    @Override
                    public void onNext(OrderModel orderModel) {
                        orderModelList.add(orderModel);
//                        listOrders.add(new OwnSMCHResultItem(orderModel));
                    }
                });
    }


    private void refresh() {
//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                treeRecyclerViewAdapter.notifyDataSetChanged();
//                refresh();
//            }
//        }, 1000);
    }

//    TreeRecyclerViewAdapter treeRecyclerViewAdapter;

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.my_recyclerView);
        CommonAdapter<OrderModel> adapter = new CommonAdapter<OrderModel>(this,
                R.layout.item_unit_and_detail,
                orderModelList) {
            @Override
            protected void convert(ViewHolder holder, OrderModel orderModel, int position) {
                holder.setText(R.id.tv_unit_name, orderModel.companyName);
                holder.setText(R.id.tv_date, DateUtil.translateDate(orderModel.createTime));
                holder.setText(R.id.tv_code,orderModel.reportNo);

                holder.getView(R.id.tv_dispatchContent)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showShort("tv_dispatchContent");
                            }
                        });
            }
        };
        mRecyclerView.setAdapter(adapter);
//        treeRecyclerViewAdapter = new TreeRecyclerViewAdapter(OwnSecretResultActivity.this, listOrders, 0);
//        treeRecyclerViewAdapter.setOnTreeDetailClickListener(new TreeRecyclerViewAdapter.OnTreeDetailClickListener() {
//            @Override
//            public void onClick(TreeAdapterItem node, int position, int sonItem, List<Integer> mListMap, int tatol) {
//                Intent intent = new Intent(OwnSecretResultActivity.this, ResultDispatchDetailActivity.class);
//                OrderModel achievementModel = (OrderModel) node.getData();
//                intent.putExtra("companyName", achievementModel.getCompanyName());
//                intent.putExtra("company", (long) achievementModel.getCompanyId());
//                intent.putExtra("HandoutId", achievementModel.getHandoutId());
//                startActivity(intent);
//            }
//        });
//        treeRecyclerViewAdapter.setOnTreeItemClickListener(new TreeRecyclerViewAdapter.OnTreeItemClickListener() {
//            @Override
//            public void onClick(TreeAdapterItem node, int position) {
//                LogUtil.e("-----" + node.getData() + "------");
//                LogUtil.e("----------TreeAdapterItem node------" + node.getData().toString());
//                if (node.getAllChilds() == null || node.getAllChilds().size() == 0) {//点击的为item
//                    if (node.getData().toString().equals("")) {//点击的为数据
//                        LogUtil.e("点击的为标题");
//                    } else {
//                        LogUtil.e("点击的为item" + position);
//
//                        if (node.getData() instanceof TempSortItemModel && !((TempSortItemModel) node.getData()).isTitle()) {
////                            TempSortItemModel cgSortOneModel = (TempSortItemModel) node.getData();
//                            AlertDialog myDialog = new AlertDialog.Builder(OwnSecretResultActivity.this).create();
//                            myDialog.show();
//                            int x = DeviceUtil.getScreenSize(OwnSecretResultActivity.this).x;
//                            myDialog.getWindow().getAttributes().width = x - DeviceUtil.dip2px(OwnSecretResultActivity.this, 42);
//                            myDialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
//                            myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            myDialog.getWindow().setContentView(R.layout.layout_own_detail_dialog);
//                            RecyclerView recyclerView = (RecyclerView) myDialog.getWindow().findViewById(R.id.my_recyclerView);
//                            //添加的模拟数据
//                            listString.clear();
//                            int i0 = 0;
//                            TempSortItemModel tempSortItemModel = (TempSortItemModel) node.getData();
//                            for (Map<String, String> stringStringMap : tempSortItemModel.getMapList()) {
//                                for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
//                                    i0++;
//                                    if (i0 <= 4) {
//                                        continue;
//                                    }
//                                    listString.add(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
//                                }
//                            }
//                            //添加标题
//                            LinearLayout linearLayout = (LinearLayout) myDialog.getWindow().findViewById(R.id.linearLayout1);
//                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                            int i = 0;
//                            for (Map<String, String> stringStringMap : tempSortItemModel.getMapList()) {
//                                for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
//                                    if (i >= 4) {
//                                        break;
//                                    }
//                                    i++;
//                                    TextView textView = new TextView(linearLayout.getContext());
//                                    textView.setLayoutParams(layoutParams);
//                                    textView.setGravity(Gravity.CENTER);
//                                    textView.setTextColor(getResources().getColor(R.color.colorWhite));
//                                    textView.setSingleLine();
//                                    textView.setEllipsize(TextUtils.TruncateAt.END);
//                                    textView.setText(stringStringEntry.getKey());
//                                    linearLayout.addView(textView);
//                                }
//                            }
//                            //添加标题对应的内容
//                            LinearLayout linearLayout2 = (LinearLayout) myDialog.getWindow().findViewById(R.id.linearLayout2);
//                            int i2 = 0;
//                            for (Map<String, String> stringStringMap : tempSortItemModel.getMapList()) {
//                                for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
//                                    if (i2 >= 4) {
//                                        break;
//                                    }
//                                    i2++;
//                                    TextView textView = new TextView(linearLayout.getContext());
//                                    textView.setLayoutParams(layoutParams);
//                                    textView.setGravity(Gravity.CENTER);
//                                    textView.setTextColor(getResources().getColor(R.color.colorWhite));
//                                    textView.setSingleLine();
//                                    textView.setEllipsize(TextUtils.TruncateAt.END);
//                                    textView.setText(stringStringEntry.getValue());
//                                    linearLayout2.addView(textView);
//                                }
//                            }
//
//
//                            OwnDetailDialogAdapter ownDetailDialogAdapter = new OwnDetailDialogAdapter(OwnSecretResultActivity.this, listString);
//                            ownDetailDialogAdapter.notifyDataSetChanged();
//                            recyclerView.setLayoutManager(new GridLayoutManager(OwnSecretResultActivity.this, 2));
//                            recyclerView.setAdapter(ownDetailDialogAdapter);
//                            myDialog.getWindow()
//                                    .findViewById(R.id.iv_delete)
//                                    .setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            myDialog.dismiss();
//                                        }
//                                    });
//                        } else {
////                            ToastUtil.showShort("居然会到这里？"+node.getData().getClass().getSimpleName());
//                        }
//
//                    }
//                }
//            }
//        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(OwnSecretResultActivity.this, DividerItemDecoration.VERTICAL));
//        mRecyclerView.setAdapter(treeRecyclerViewAdapter);
    }
}
