package android.zhixun.uiho.com.gissystem.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.HandoutFruitModel;
import android.zhixun.uiho.com.gissystem.flux.models.ReportHandoutListModel;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.util.DensityUtils;
import android.zhixun.uiho.com.gissystem.util.ScreenUtil;

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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.LinearLayout.SHOW_DIVIDER_MIDDLE;

/***
 * 拥有的涉密测绘成果
 */
public class OwnSecretResultActivity extends BaseActivityWithTitle {

    private RecyclerView mRecyclerView;//recyclerView
    private long companyId;//从地图上查到的公司的数据
    public static final String COMPANY_NAME = "company_name";
    private String mCompanyName;
    private List<ReportHandoutListModel> orderModelList = new ArrayList<>();

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
        setTitleText("拥有的涉密测绘成果");
        companyId = getIntent().getLongExtra("company", -1);
        mCompanyName = getIntent().getStringExtra(COMPANY_NAME);
        LogUtil.e("-----------" + companyId);
        //初始化
        initViews();
    }

    private void initViews() {
        Map<Object, Object> map = new HashMap<>();
        map.put("companyName", mCompanyName);
        APIService.getInstance()
                .getOrderList(map, new SimpleSubscriber<List<ReportHandoutListModel>>() {
                    @Override
                    public void onResponse(List<ReportHandoutListModel> response) {
                        if (response == null || response.isEmpty()) {
                            ToastUtil.showEmpty();
                            return;
                        }
                        orderModelList.clear();
                        orderModelList.addAll(response);
                        initRecyclerView();
                    }
                });
    }


    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.my_recyclerView);
        CommonAdapter<ReportHandoutListModel> adapter = new CommonAdapter<ReportHandoutListModel>(this,
                R.layout.item_dispatch_bottom,
                orderModelList) {
            @Override
            protected void convert(ViewHolder holder, ReportHandoutListModel item, int position) {
                holder.setText(R.id.tv_companyName, item.purposeUse);
//                holder.setText(R.id.tv_date, DateUtil.translateDate(orderModel.createTime));
                holder.setText(R.id.tv_reportNo, item.reportNo);
                String time = DateUtil.longToString(DateUtil.yyyyMMDD,
                        item.reportHandout.createTime);
                holder.setText(R.id.tv_time, time);

                holder.getView(R.id.rl_handoutContent)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                ToastUtil.showShort("tv_dispatchContent");

                                View rl_handoutConntent = holder.getView(R.id.rl_handoutContent);
                                TextView text_handoutContent = holder.getView(R.id.text_handoutContent);
                                ImageView iv_arrow = holder.getView(R.id.iv_arrow);
                                LinearLayout ll_content = holder.getView(R.id.ll_content);
                                ll_content.setVisibility(ll_content.getVisibility() == View.VISIBLE
                                        ? View.GONE : View.VISIBLE);
                                iv_arrow.animate()
                                        .rotationBy(180)
                                        .start();
//                                rl_handoutConntent.setOnClickListener(v2 -> {
//
//                                });
                                bindViewHandoutContent(item,ll_content);
                            }
                        });
            }
        };
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(OwnSecretResultActivity.this,
                DividerItemDecoration.VERTICAL));
    }

    private void bindViewHandoutContent(ReportHandoutListModel item, LinearLayout ll_content) {
        for (ReportHandoutListModel.FruitCategoryList fruitCategory : item.fruitCategoryList) {
            View contentView = View.inflate(this,
                    R.layout.item_dispatch_bottom_content, null);
            ll_content.addView(contentView);

            View rl_content = contentView.findViewById(R.id.rl_content);
            TextView tvContent = contentView.findViewById(R.id.tv_content);
            LinearLayout ll_data = contentView.findViewById(R.id.ll_data);
            LinearLayout ll_data_parent = contentView.findViewById(R.id.ll_data_parent);
            TextView tvCount = contentView.findViewById(R.id.tv_count);
            ImageView iv_arrow = contentView.findViewById(R.id.iv_contentArrow);
            rl_content.setOnClickListener(v -> {
                int visible = ll_data_parent.getVisibility() == View.VISIBLE ? GONE : VISIBLE;
                ll_data_parent.setVisibility(visible);
                iv_arrow.animate()
                        .rotationBy(180)
                        .start();
            });
            //设置content item的名字
            tvContent.setText(fruitCategory.categoryName);
            //设置content 的 列表
            tvCount.setText(String.format("共%s条数据", fruitCategory.fruitList.size()));
            //设置行的数据
            if (fruitCategory.fruitList.isEmpty()) continue;
            //需要显示的数据的集合
            List<ReportHandoutListModel.FruitCategoryList.FruitList.FruitAttrList> isShowList =
                    new ArrayList<>();
            for (ReportHandoutListModel.FruitCategoryList.FruitList fruitList : fruitCategory.fruitList) {
                LinearLayout ll_row = createRowLL();
                isShowList.clear();
                for (ReportHandoutListModel.FruitCategoryList.FruitList.FruitAttrList fruitAttrList
                        : fruitList.fruitAttrList) {
                    if (!fruitAttrList.isListShow) continue;
                    isShowList.add(fruitAttrList);
                }
                //设置表格的数据
                for (ReportHandoutListModel.FruitCategoryList.
                        FruitList.FruitAttrList fruitAttrList : isShowList) {
                    TextView textView = createRowText();
                    int color = fruitList.selected ? Color.RED : Color.BLACK;
                    textView.setText(fruitAttrList.attrValue);
                    textView.setTextColor(color);
                    ll_row.addView(textView);
                }
                //详情按钮
                TextView tv_info = createRowText();
                tv_info.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                tv_info.setText("详情");
                ll_row.addView(tv_info);
                tv_info.setOnClickListener(v -> {
                    showHandoutInfoDialog(String.valueOf(fruitList.fruitId));
                });
                ll_data.addView(ll_row);
            }
            //设置表格的标题
            LinearLayout ll_title = createRowLL();
            for (ReportHandoutListModel.FruitCategoryList.
                    FruitList.FruitAttrList fruitAttrList : isShowList) {
                TextView tv_title = createRowText();
                tv_title.setText(fruitAttrList.attrName);
                ll_title.addView(tv_title);
            }
            TextView tv_option = createRowText();
            tv_option.setText("操作");
            ll_title.addView(tv_option);
            ll_data.addView(ll_title, 0);
        }
    }

    @NonNull
    private TextView createRowText() {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setTextSize(13);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        return textView;
    }

    @NonNull
    private LinearLayout createRowLL() {
        LinearLayout ll_row = new LinearLayout(this);
        ll_row.setPadding(0, ScreenUtil.dip2px(this, 5),
                0, ScreenUtil.dip2px(this, 5));
        ll_row.setOrientation(LinearLayout.HORIZONTAL);
        ll_row.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.horizontal_divider));
        ll_row.setShowDividers(SHOW_DIVIDER_MIDDLE);
        return ll_row;
    }

    //显示详情dialog
    private void showHandoutInfoDialog(String fruitId) {
        showLoading();
        APIService.getInstance()
                .getHandoutFruit(fruitId, new SimpleSubscriber<HandoutFruitModel>() {
                    @Override
                    public void onResponse(HandoutFruitModel response) {
                        hideLoading();
                        List<HandoutFruitModel.FruitAttrList> fruitAttrList = response.fruitAttrList;
                        if (fruitAttrList == null || fruitAttrList.isEmpty()) {
                            ToastUtil.showShort("未查询到相关信息");
                            return;
                        }
                        AppCompatDialog dialog = new AppCompatDialog(OwnSecretResultActivity.this);
                        View dialogView = View.inflate(OwnSecretResultActivity.this, R.layout.dialog_handout_info,
                                null);
                        LinearLayout ll_content = dialogView.findViewById(R.id.ll_content);
                        RecyclerView rv = dialogView.findViewById(R.id.recycler_view);
                        //标题
                        LinearLayout ll_title = createRowLL();
                        ll_title.setBackgroundColor(ContextCompat.getColor(OwnSecretResultActivity.this, R.color.c55f3f));
                        //内容
                        LinearLayout ll_data = createRowLL();
                        ll_data.setOnClickListener(v -> {
                            int visible = rv.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
                            rv.setVisibility(visible);
                        });
                        ll_data.setPadding(0, DensityUtils.dp2px(OwnSecretResultActivity.this, 1), 0, 0);

                        List<HandoutFruitModel.FruitAttrList> notListShowModels = new ArrayList<>();
                        for (HandoutFruitModel.FruitAttrList attrModel : fruitAttrList) {
                            if (!attrModel.isListShow) {
                                notListShowModels.add(attrModel);
                                continue;
                            }
                            TextView tv_title = createRowText();
                            tv_title.setTextColor(Color.WHITE);
                            TextView tv_data = createRowText();
                            tv_data.setBackgroundColor(ContextCompat.getColor(OwnSecretResultActivity.this,
                                    R.color.color_454344));
                            tv_data.setTextColor(Color.WHITE);

                            tv_title.setText(attrModel.attrName);
                            tv_data.setText(attrModel.attrValue);

                            ll_title.addView(tv_title);
                            ll_data.addView(tv_data);
                        }
                        ll_content.addView(ll_title);
                        ll_content.addView(ll_data);
                        //不显示在列表的内容
                        rv.setLayoutManager(new GridLayoutManager(OwnSecretResultActivity.this, 2));
                        rv.setAdapter(new CommonAdapter<HandoutFruitModel.FruitAttrList>(OwnSecretResultActivity.this,
                                R.layout.item_not_list_show, notListShowModels) {
                            @Override
                            protected void convert(ViewHolder holder,
                                                   HandoutFruitModel.FruitAttrList item,
                                                   int position) {
                                String text = item.attrName + ":" + item.attrValue;
                                holder.setText(R.id.tv_notListShowItem, text);
                            }
                        });
                        //
                        dialog.setContentView(dialogView);
                        dialog.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideLoading();
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }
}
