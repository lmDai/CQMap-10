package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.drawtool.DrawTool;
import android.zhixun.uiho.com.gissystem.flux.body.ClassifyBody;
import android.zhixun.uiho.com.gissystem.flux.body.OrderBody;
import android.zhixun.uiho.com.gissystem.flux.body.ReportHandoutListBody;
import android.zhixun.uiho.com.gissystem.flux.models.GethandoutConditionByFCModel;
import android.zhixun.uiho.com.gissystem.flux.models.ReportHandoutListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.FruitCategoryListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.OrderModel;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.activity.MainActivity;
import android.zhixun.uiho.com.gissystem.ui.adapter.AdminRegionAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.ClassifyFlowTypeAttrValAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;
import android.zhixun.uiho.com.gissystem.ui.widget.DialogUtil;
import android.zhixun.uiho.com.gissystem.ui.widget.DragLayout;
import android.zhixun.uiho.com.gissystem.ui.widget.SimpleAlertDialog;
import android.zhixun.uiho.com.gissystem.ui.widget.SpaceDialog;
import android.zhixun.uiho.com.gissystem.util.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.util.ScreenUtil;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yibogame.util.DateUtil;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.LinearLayout.SHOW_DIVIDER_MIDDLE;
import static android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView.DEM_LAYER;


/**
 * 单位
 */

@Keep
public class DispatchFragment extends BaseFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private BaseMapView mMapView;
    private View mCVLayer, mCVSift, mCVLocation, mZoomIn, mZoomOut, mCVSpace, mCVClear,
            mCvClassifySearch;
    private ImageView mIvUser, mIvSearchClear;
    private TextView mTvSearch;
    private EditText mEtSearch;
    private DragLayout mDragLayout;
    private RecyclerView mContentRv;
    private View dragView;
    //
    private AppCompatImageView calendar1, calendar2;
    private RelativeLayout relativeLayoutLeft, relativeLayoutRight;
    //分类信息集合-成果类型那一坨
    private List<FruitCategoryListModel> mClassifyList = new ArrayList<>();
    //分类信息集合-成果类型下面的集合
    private List<GethandoutConditionByFCModel> mClassifyTypeList = new ArrayList<>();
    //分发信息集合
    private List<ReportHandoutListModel> mHandoutList = new ArrayList<>();
    //
    private ClassifyBody classifyBody = new ClassifyBody();

    public DispatchFragment() {
        Bundle args = new Bundle();
        args.putString("name", this.getClass().getSimpleName());
        setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dispatch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initBottomDragView(view);
        initEvent();
        initData();
    }

    private void initView(View view) {
        mMapView = view.findViewById(R.id.mapView);
        mCVLayer = view.findViewById(R.id.cv_layer);
        mCVSift = view.findViewById(R.id.cv_sift);
        mCVLocation = view.findViewById(R.id.cv_my_location);
        mZoomIn = view.findViewById(R.id.aiv_zoom_in);
        mZoomOut = view.findViewById(R.id.aiv_zoom_out);
        mCVSpace = view.findViewById(R.id.cv_space);
        mCVClear = view.findViewById(R.id.cv_clear);
        mIvUser = view.findViewById(R.id.iv_user);
        mTvSearch = view.findViewById(R.id.tv_search);
        mEtSearch = view.findViewById(R.id.et_search);
        mCvClassifySearch = view.findViewById(R.id.cv_classifySearch);
        mIvSearchClear = view.findViewById(R.id.aciv_clear);

        mEtSearch.setEnabled(false);
        mEtSearch.setHint("请选择分类或空间");

    }

    private void initEvent() {
        mCVLayer.setOnClickListener(this);
        mCVSift.setOnClickListener(this);
        mCVLocation.setOnClickListener(this);
        mZoomIn.setOnClickListener(this);
        mZoomOut.setOnClickListener(this);
        mCVSpace.setOnClickListener(this);
        mCVClear.setOnClickListener(this);
        mIvUser.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
        mCvClassifySearch.setOnClickListener(this);
        mIvSearchClear.setOnClickListener(this);
    }

    private void initData() {
        getClassifyData();
    }

    private void initBottomDragView(View view) {
        mDragLayout = view.findViewById(R.id.dragLayout);
        mContentRv = view.findViewById(R.id.content_rv);
        dragView = view.findViewById(R.id.bottom_drag_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_layer://切换地图样式
                showSwitchLayerDialog(v);
                break;
            case R.id.cv_sift://筛选，条件查询
                showSiftDialog(v);
                break;
            case R.id.cv_my_location://我的位置
                mMapView.location();
                break;
            case R.id.aiv_zoom_in://
                mMapView.zoomin();
                break;
            case R.id.aiv_zoom_out://
                mMapView.zoomout();
                break;
            case R.id.cv_space://空间查询
                if (mMapView.getCurrentDrawSpace() == BaseMapView.SPACE_NONE) {
                    showSpaceDialog(v);
                } else if (mMapView.getCurrentDrawSpace() == BaseMapView.SPACE_BUFFER) {
                    showBufferInputDialog(v);
                } else if (mMapView.getCurrentDrawSpace() == BaseMapView.SPACE_POLYGON) {
                    setPloygon();
                } else {
                    searchGeometry();
                }
                break;
            case R.id.cv_clear:
                restoreAll();
                break;
            case R.id.iv_user:
                ((MainActivity) getActivity()).openDrawer();
                break;
            case R.id.tv_search:
                String searchStr = mEtSearch.getText().toString();
                if (TextUtils.isEmpty(searchStr)) {
                    setSearhText("全部");
                }
                getReportHandoutList(mBody);
                break;
            case R.id.cv_classifySearch://分类查询
                showClassifyDialog(v);
                break;
            case R.id.aciv_clear:
                showSearchTextView();
                break;
        }
    }

    private void showSearchTextView() {
        mTvSearch.setVisibility(View.VISIBLE);
        mIvSearchClear.setVisibility(View.GONE);
        mEtSearch.setText("");
        mMapView.clearAll();
    }

    private void showSearchClearView() {
        mIvSearchClear.setVisibility(View.VISIBLE);
        mTvSearch.setVisibility(View.GONE);
    }

    private void setPloygon() {
        mMapView.setCurrentDrawGraphic(mMapView.getDrawTool().drawGraphic);
        searchGeometry();
//        mMapView.setCurrentDrawSpace(BaseMapView.SPACE_POLYGON_SET_FINISH);
    }

    private ReportHandoutListBody mBody;

    private void showClassifyDialog(View view) {
        showLoading();
        if (!mClassifyList.isEmpty()) {
            bindViewClassify(view, mClassifyList);
        } else {
            APIService.getInstance().getfruitCategoryList(
                    new SimpleSubscriber<List<FruitCategoryListModel>>() {
                        @Override
                        public void onResponse(List<FruitCategoryListModel> response) {
                            dismissLoading();
                            if (response == null || response.isEmpty()) {
                                ToastUtil.showShort("未获取到信息");
                                return;
                            }
                            mClassifyList.clear();
                            mClassifyList.addAll(response);
                            bindViewClassify(view, mClassifyList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            dismissLoading();
                        }
                    });
        }
    }

    private void bindViewClassify(View view, List<FruitCategoryListModel> response) {
        View dialogRoot = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_classify_search, ((ViewGroup) getView()),
                        false);

        response.get(0).selected = true;
        RecyclerView rv_flowType = dialogRoot.findViewById(R.id.rv_flow_type);
        rv_flowType.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        CommonAdapter flowTypeAdapter = new CommonAdapter<FruitCategoryListModel>(getActivity(),
                R.layout.item_flow_type, response) {
            @Override
            protected void convert(ViewHolder holder, FruitCategoryListModel item,
                                   int position) {
                if (item.selected) {
                    switchType(item, dialogRoot);
                }
                holder.itemView.setSelected(item.selected);
                TextView tvType = holder.getView(R.id.tv_text);
                tvType.setText(item.categoryName);
            }
        };
        rv_flowType.setAdapter(flowTypeAdapter);
        flowTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                response.get(position).selected = true;
                for (FruitCategoryListModel model : response) {
                    model.selected = response.indexOf(model) == position;
                }
                flowTypeAdapter.notifyDataSetChanged();
            }
        });
        Button btnSearch = dialogRoot.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(v -> {

            if (mClassifyList.isEmpty()) return;
            mBody = new ReportHandoutListBody();
            StringBuilder sb = new StringBuilder();
            for (FruitCategoryListModel model : mClassifyList) {
                if (model.selected) {
                    sb.append(model.categoryName);
                    sb.append(",");
                    mBody.fruitCategoryId = model.fruitCategoryId;
                }
            }
            if (!mClassifyTypeList.isEmpty()) {

                for (GethandoutConditionByFCModel gethandoutConditionByFCModel : mClassifyTypeList) {
                    if (gethandoutConditionByFCModel.fruitCateGoryAttrVal == null ||
                            gethandoutConditionByFCModel.fruitCateGoryAttrVal.isEmpty()) {
                        continue;
                    }
                    for (GethandoutConditionByFCModel.FruitCateGoryAttrVal attrVal :
                            gethandoutConditionByFCModel.fruitCateGoryAttrVal) {
                        if (attrVal.selected && !TextUtils.equals(attrVal.attrValue, "全部")) {
                            sb.append(attrVal.attrValue);
                            sb.append(",");
                            mBody.attrValueList
                                    .add(new ReportHandoutListBody
                                            .AttrValueList(gethandoutConditionByFCModel.fruitCategoryAttrId,
                                            attrVal.attrValue));
                        }
                    }
                }
            }
            DialogUtil.getInstance().dismiss();
            setSearhText(sb.toString());
        });
        DialogUtil.getInstance().showAnchorDialog(dialogRoot, view);
    }

    //设置搜索框的文字
    private void setSearhText(String text) {
        mEtSearch.setText("");
        mEtSearch.setText(text);
    }

    //切换成果类型
    private void switchType(FruitCategoryListModel model, View dialogRoot) {
        showLoading();
        APIService.getInstance()
                .gethandoutConditionByFCList(model.fruitCategoryId,
                        new SimpleSubscriber<List<GethandoutConditionByFCModel>>() {
                            @Override
                            public void onResponse(List<GethandoutConditionByFCModel> response) {
                                dismissLoading();
                                if (response == null || response.isEmpty()) {
                                    return;
                                }
                                mClassifyTypeList.clear();
                                mClassifyTypeList.addAll(response);
                                bindViewClassifyType(dialogRoot, mClassifyTypeList);
                            }
                        });
    }

    private void bindViewClassifyType(View dialogRoot, List<GethandoutConditionByFCModel> response) {
        LinearLayout llContainer = dialogRoot.findViewById(R.id.ll_container);
        llContainer.removeAllViews();
        for (GethandoutConditionByFCModel gcbfModel : response) {
            View itemView = LayoutInflater.from(dialogRoot.getContext())
                    .inflate(R.layout.dispatch_dialog_item, llContainer,
                            false);
            TextView tv_attrName = itemView.findViewById(R.id.tv_attrName);
            tv_attrName.setText(gcbfModel.attrName);
            llContainer.addView(itemView);

            List<GethandoutConditionByFCModel.FruitCateGoryAttrVal> attrVals =
                    gcbfModel.fruitCateGoryAttrVal;
            if (attrVals == null || attrVals.isEmpty()) {
                return;
            }
            if (response.indexOf(gcbfModel) != 0) {
                attrVals.add(0, new GethandoutConditionByFCModel.FruitCateGoryAttrVal("全部"));
            }
            attrVals.get(0).selected = true;
            RecyclerView recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            ClassifyFlowTypeAttrValAdapter typeAdapter = new ClassifyFlowTypeAttrValAdapter(getActivity(), attrVals);
            recyclerView.setAdapter(typeAdapter);
            typeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    for (GethandoutConditionByFCModel.FruitCateGoryAttrVal attrVal : attrVals) {
                        attrVal.selected = attrVals.indexOf(attrVal) == position;
                    }
                    attrVals.get(position).selected = true;
                    typeAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    //查询分发的集合
    private void getReportHandoutList(ReportHandoutListBody body) {
        if (body == null) {
            body = new ReportHandoutListBody();
        }
        showSearchClearView();
        showLoading();
        APIService.getInstance().getReportHandoutList(body,
                new SimpleSubscriber<List<ReportHandoutListModel>>() {
                    @Override
                    public void onResponse(List<ReportHandoutListModel> response) {
                        dismissLoading();
                        if (response == null || response.isEmpty()) return;
                        mHandoutList.clear();
                        mHandoutList.addAll(response);

                        searchMapService(mHandoutList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }

    private void searchMapService(List<ReportHandoutListModel> handoutListModels) {
        if (this.mHandoutList.isEmpty()) return;
        StringBuilder sb = new StringBuilder();
        sb.append("FRUITID");
        sb.append(" in ");
        sb.append("(");
        int mapType = 0;
        for (ReportHandoutListModel reportHandoutListModel : handoutListModels) {

            for (ReportHandoutListModel.FruitCategoryList fruitCategoryList :
                    reportHandoutListModel.fruitCategoryList) {

                mapType = fruitCategoryList.mapType;
                for (ReportHandoutListModel.FruitCategoryList.FruitList fruitList :
                        fruitCategoryList.fruitList) {

                    String fruitId = fruitList.fruitId;
                    sb.append(fruitId);
                    sb.append(",");
                }

            }

        }

        String whereClause = sb.substring(0, sb.lastIndexOf(",")) + ")";
        LogUtil.i("whereClause == " + whereClause);

        searchType(mapType, whereClause);
    }

    private void searchType(int mapType, String whereClause) {
        if (mapType == 0) return;
        String url;
        if (mapType == 1) {//点查询
            url = getString(R.string.point_feature_server_url);
        } else {//面查询
            url = getString(R.string.polygon_feature_server_url);
        }
        mMapView.querySQL(getActivity(), url, whereClause,
                new BaseMapView.MainThreadCallback<FeatureResult>() {
                    @Override
                    public void onCallback(FeatureResult result) {
                        if (result.featureCount() == 0) {
                            ToastUtil.showShort("未获取信息,请重试");
                            restoreAll();
                            return;
                        }

                        for (Object object : result) {
                            if (object instanceof Feature) {
                                Feature feature = (Feature) object;

                                SimpleFillSymbol symbol =
                                        new SimpleFillSymbol(Color.parseColor("#8855ef"),
                                                SimpleFillSymbol.STYLE.SOLID);
                                symbol.setAlpha(180);
                                symbol.setOutline(new SimpleLineSymbol(Color.WHITE, 2));
                                Graphic graphic = new Graphic(feature.getGeometry(),
                                        symbol, feature.getAttributes());
                                mMapView.addGraphic(graphic);
                            }
                        }
                        //地图单击事件
                        mMapView.setOnSingleTapListener((OnSingleTapListener) (x, y) -> {
                            int[] ids = mMapView.getDrawLayer().getGraphicIDs(x, y,
                                    1, 1);
                            if (ids.length == 0) return;
                            GraphicsLayer drawLayer = mMapView.getDrawLayer();
                            Graphic graphic = drawLayer.getGraphic(ids[0]);
                            if (graphic == null) return;
                            drawLayer.updateGraphic(ids[0], new SimpleFillSymbol(Color.RED));
                            String FRUIT_ID = String.valueOf(graphic.getAttributeValue("FRUITID"));
                            List<ReportHandoutListModel> handoutList = new ArrayList<>();
                            for (ReportHandoutListModel handout : mHandoutList) {
                                for (ReportHandoutListModel.FruitCategoryList fruitCategory :
                                        handout.fruitCategoryList) {
                                    for (ReportHandoutListModel.FruitCategoryList.FruitList fruitList
                                            : fruitCategory.fruitList) {
                                        if (!FRUIT_ID.contains(fruitList.fruitId))
                                            continue;
                                        handoutList.add(handout);
                                    }
                                }
                            }
                            showBottomLayout(handoutList);
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort("未获取信息,请重试");
                    }
                });
        dismissLoading();
    }

    private void showSwitchLayerDialog(View view) {
        View contentView = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.layout_map_switch, (ViewGroup) getView(),
                        false);
        DialogUtil.getInstance().showAnchorDialog(contentView, view);
        RadioGroup rgLayerSwitch = contentView.findViewById(R.id.rg_layer_switch);
        switch (mMapView.getCurrentMapLayer()) {
            case BaseMapView.DEM_LAYER:
                ((RadioButton) rgLayerSwitch.findViewById(R.id.rb_topographic)).setChecked(true);
                break;
            case BaseMapView.VEC_LAYER:
                ((RadioButton) rgLayerSwitch.findViewById(R.id.rb_vector)).setChecked(true);
                break;
            case BaseMapView.IMG_LAYER:
                ((RadioButton) rgLayerSwitch.findViewById(R.id.rb_image)).setChecked(true);
                break;

        }
        rgLayerSwitch.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_topographic:
                    mMapView.setCurrentMapLayer(DEM_LAYER);
                    break;
                case R.id.rb_vector:
                    mMapView.setCurrentMapLayer(BaseMapView.VEC_LAYER);
                    break;
                case R.id.rb_image:
                    mMapView.setCurrentMapLayer(BaseMapView.IMG_LAYER);
                    break;
            }
            DialogUtil.getInstance().dismiss();
        });
    }

    private TextView calendar1_content, calendar2_content;

    private void showSiftDialog(View view) {
        View contentView = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.layout_result_dispatch_filter,
                        (ViewGroup) getView(),
                        false);
        calendar1 = contentView.findViewById(R.id.calendar1);
        calendar2 = contentView.findViewById(R.id.calendar2);
        relativeLayoutLeft = contentView.findViewById(R.id.relativeLayoutLeft);
        relativeLayoutRight = contentView.findViewById(R.id.relativeLayoutRight);

        calendar1Content = contentView.findViewById(R.id.calendar1_content);
        calendar2Content = contentView.findViewById(R.id.calendar2_content);

//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        calendar2Content.setText("结束时间");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//            String dateString = year + "-" + month + "-" + day;
        calendar1Content.setText("开始时间");

        relativeLayoutLeft.setOnClickListener(view1 -> {
            isFirst = true;
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd1 = DatePickerDialog.newInstance(DispatchFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH) - 1,
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd1.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd1.show(getFragmentManager(), "Datepickerdialog");
        });
        relativeLayoutRight.setOnClickListener(view12 -> {
            isFirst = false;
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd2 = DatePickerDialog.newInstance(
                    DispatchFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd2.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd2.show(getFragmentManager(), "Datepickerdialog");
        });
        AppCompatButton acbQuery = contentView.findViewById(R.id.acb_query);
        initEtSearchForDialog(contentView);
        initAcbSearchForDialog(contentView, acbQuery);
        DialogUtil.getInstance().showAnchorDialog(contentView, view);
    }

    private String etString = "";

    private void initEtSearchForDialog(View contentView) {
        EditText etCaseCode = (EditText) contentView.findViewById(R.id.et_case_code);//报件编号
        EditText etDataCode = (EditText) contentView.findViewById(R.id.et_data_code);//图幅数/数据编号
        EditText etSearchDialog = (EditText) contentView.findViewById(R.id.et_search);
        calendar1_content = (TextView) contentView.findViewById(R.id.calendar1_content);
        calendar2_content = (TextView) contentView.findViewById(R.id.calendar2_content);

        etSearchDialog.setImeActionLabel("确定", EditorInfo.IME_ACTION_DONE);
        if (!TextUtils.isEmpty(etString)) {
            etSearchDialog.setText(etString);
            etSearchDialog.setSelection(etString.length());
        }
        etSearchDialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etString = editable.toString();
                mEtSearch.setText(etString);
                mEtSearch.setSelection(etString.length());
            }
        });
    }

    //条件查询确定按钮点击事件
    private void initAcbSearchForDialog(View contentView, AppCompatButton acbQuery) {
        EditText et_search = contentView.findViewById(R.id.et_search);
        EditText et_caseCode = contentView.findViewById(R.id.et_case_code);
        EditText et_dataCode = contentView.findViewById(R.id.et_data_code);
        //设置'查询'按钮功能
        acbQuery.setOnClickListener(view -> {
            long data1 = 0, data2 = 0;
            try {
                data1 = DateUtil.stringToLong("yyyy-MM-dd", calendar1_content.getText().toString());
            } catch (Exception e) {
//                    e.printStackTrace();
            }
            try {
                data2 = DateUtil.stringToLong("yyyy-MM-dd", calendar2_content.getText().toString());
            } catch (Exception e) {
//                    e.printStackTrace();
            }
            if (data1 != 0 && data2 == 0) {
                ToastUtil.showShort("请选择结束时间");
                return;
            }
            if (data1 == 0 && data2 != 0) {
                ToastUtil.showShort("请选择开始时间");
                return;
            }

            OrderBody orderBody = new OrderBody();
            orderBody.key = et_search.getText().toString();
            orderBody.reportNo = et_caseCode.getText().toString();
            orderBody.otherKey = et_dataCode.getText().toString();
            orderBody.reportTimeBegin = String.valueOf(data1);
            orderBody.reportTimeEnd = String.valueOf(data2);
            getOrderList(orderBody);
        });
    }

    private void showCompanyMarker(FeatureResult objects) {
        if (objects.featureCount() == 0) {
            ToastUtil.showShort("未获取单位信息,请重试");
            restoreAll();
            return;
        }
        for (Object object : objects) {
            if (object instanceof Feature) {
                Feature feature = (Feature) object;
                SimpleMarkerSymbol symbol =
                        new SimpleMarkerSymbol(Color.RED, 12, SimpleMarkerSymbol.STYLE.CIRCLE);
                Graphic graphic = new Graphic(feature.getGeometry(),
                        symbol, feature.getAttributes());
                mMapView.addGraphic(graphic);
            }
        }
    }

    private void showBottomLayout(List<ReportHandoutListModel> handoutList) {
        if (handoutList == null || handoutList.isEmpty()) {
            return;
        }
        mCVClear.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).hideBottomNav();
        CommonAdapter<ReportHandoutListModel> bottomAdapter =
                new CommonAdapter<ReportHandoutListModel>(getActivity(), R.layout.item_dispatch_bottom,
                        handoutList) {
                    @Override
                    protected void convert(ViewHolder holder, ReportHandoutListModel item, int position) {
                        holder.setText(R.id.tv_companyName, item.companyName)
                                .setText(R.id.tv_reportNo, item.reportNo);
                        View rl_handoutConntent = holder.getView(R.id.rl_handoutContent);
                        TextView text_handoutContent = holder.getView(R.id.text_handoutContent);
                        ImageView iv_arrow = holder.getView(R.id.iv_arrow);
                        LinearLayout ll_content = holder.getView(R.id.ll_content);
                        //分发内容的点击事件
                        rl_handoutConntent.setOnClickListener(v -> {
                            ll_content.setVisibility(ll_content.getVisibility() == View.VISIBLE
                                    ? View.GONE : View.VISIBLE);
                            iv_arrow.animate()
                                    .rotationBy(180)
                                    .start();
                        });
                        if (item.fruitCategoryList == null || item.fruitCategoryList.isEmpty()) {
                            return;
                        }
                        bindViewHandoutContent(item, ll_content);
                    }
                };
        mContentRv.setAdapter(bottomAdapter);
        mDragLayout.animaToCenter();
        dragView.setVisibility(View.VISIBLE);

//        bottomAdapter.setOnItemClickListener((view, position) -> {
//            int companyId = response.get(position).getCompanyId();
//            for (int id : mMapView.getDrawLayer().getGraphicIDs()) {
//                Graphic graphic = mMapView.getDrawLayer().getGraphic(id);
//                int unit_id = (int) graphic.getAttributeValue("UNITID");
//                if (companyId == unit_id) {
//                    if (graphic.getGeometry() instanceof Point) {
//                        Point point = (Point) graphic.getGeometry();
//                        mMapView.centerAt(point, true);
//                    }
//                }
//            }
//        });
    }

    //设置弹出框分发内容的view
    private void bindViewHandoutContent(ReportHandoutListModel item, LinearLayout ll_content) {
        for (ReportHandoutListModel.FruitCategoryList fruitCategory : item.fruitCategoryList) {
            View contentView = View.inflate(getActivity(),
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
                    textView.setText(fruitAttrList.attrValue);
                    ll_row.addView(textView);
                }
                //详情按钮
                TextView tv_info = createRowText();
                tv_info.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                tv_info.setText("详情");
                ll_row.addView(tv_info);
                tv_info.setOnClickListener(v -> {
                    ToastUtil.showShort("详情");
                    showInfoDialog();
                });
                //行的点击事件
                ll_row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShort("行的点击事件");
                    }
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
        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setMaxLines(1);
        textView.setTextSize(13);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        return textView;
    }

    @NonNull
    private LinearLayout createRowLL() {
        LinearLayout ll_row = new LinearLayout(getActivity());
        ll_row.setPadding(0, ScreenUtil.dip2px(getActivity(), 5),
                0, ScreenUtil.dip2px(getActivity(), 5));
        ll_row.setOrientation(LinearLayout.HORIZONTAL);
        ll_row.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.horizontal_divider));
        ll_row.setShowDividers(SHOW_DIVIDER_MIDDLE);
        return ll_row;
    }

    private void showInfoDialog() {

    }

    private void hideBottomLayout() {
        mCVClear.setVisibility(View.GONE);
        mDragLayout.exit();
        dragView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).showBottomNav();
    }

    private void showSpaceDialog(View view) {
        int[] resIds = {R.mipmap.ic_add, R.mipmap.ic_add, R.mipmap.ic_add
                , R.mipmap.ic_add, R.mipmap.ic_add};
        String[] texts = {"矩形框选", "多边形框选", "缓冲区查询", "图幅号查询", "行政区域查询"};
        new SpaceDialog(getActivity(), (ViewGroup) getView())
                .setData(resIds, texts)
                .show(view)
                .setOnItemClickListener((view1, position) -> {
                    switch (position) {
                        case 0://矩形
                            mMapView.getDrawTool().activate(DrawTool.ENVELOPE);
                            mMapView.setCurrentDrawSpace(BaseMapView.SPACE_RECT);
                            break;
                        case 1://多边形
                            mMapView.getDrawTool().activate(DrawTool.POLYGON);
                            mMapView.setCurrentDrawSpace(BaseMapView.SPACE_POLYGON);
                            break;
                        case 2://缓冲区查询
                            mMapView.getDrawTool().activate(DrawTool.FREEHAND_POLYLINE);
                            mMapView.setCurrentDrawSpace(BaseMapView.SPACE_BUFFER);
                            break;
                        case 3://图幅号查询
                            ToastUtil.showShort("图幅号查询");

                            break;
                        case 4://行政区域查询
                            ToastUtil.showShort("行政区域查询");
                            showAdminRegionDialog();
                            break;
                    }
                    AppCompatImageView ivSpace = mCVSpace.findViewById(R.id.aci_space);
                    ivSpace.setImageResource(R.mipmap.ic_sure_modifi);
                    mCVClear.setVisibility(View.VISIBLE);
                });
    }

    private void showBufferInputDialog(View view) {
        if (mMapView.getDrawLayer().getExtent() == null) {
            ToastUtil.showShort(" 请多次点击地图");
            return;
        }
        new SimpleAlertDialog(getActivity())
                .title("缓冲区查询")
                .message("请输入范围(0-100公里)")
                .setOkOnClickListener(R.string.alert_ok, (dialog1, v) -> {
                    String text = dialog1.getEditText().getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        ToastUtil.showShort("不能为空");
                        return;
                    }
                    int distance = Integer.parseInt(text);
                    setBufferGeometry(distance);
                    mMapView.setCurrentDrawSpace(BaseMapView.SPACE_BUFFER_SET_FINISH);
                })
                .visiableEditText()
                .setCancelOnClickListener(R.string.alert_cancel, null)
                .alert();
    }

    private int lastPosition = -1;

    //行政区域查询show dialog
    private void showAdminRegionDialog() {
        showLoading();
        lastPosition = -1;
        Map<Object, Object> map = new HashMap<>();
        APIService.getInstance()
                .getAreaList(map, new SimpleSubscriber<List<AreaModel>>() {
                    @Override
                    public void onResponse(List<AreaModel> response) {
                        dismissLoading();
                        View dialogView = View.inflate(getActivity(), R.layout.dialog_admin_region,
                                null);
                        RecyclerView rv = dialogView.findViewById(R.id.recycler_view);
                        Button btn_ok = dialogView.findViewById(R.id.btn_ok);
                        rv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                        AdminRegionAdapter adapter = new AdminRegionAdapter(getActivity(), response);
                        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                                ToastUtil.showShort("onItemClick");
//                                TextView textView = view.findViewById(R.id.tv_title);
                                if (position == lastPosition) return;
                                if (response.get(position).isChecked()) {
                                    response.get(position).setChecked(false);
//                                    textView.setSelected(false);
                                } else {
                                    response.get(position).setChecked(true);
                                    if (lastPosition != -1) {
                                        response.get(lastPosition).setChecked(false);
                                    }
//                                    textView.setSelected(true);
                                }
                                adapter.notifyDataSetChanged();
                                lastPosition = position;
                            }

                            @Override
                            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                                           int position) {
                                return false;
                            }
                        });
                        rv.setAdapter(adapter);
                        AppCompatDialog dialog = new AppCompatDialog(getActivity());
                        dialog.setContentView(dialogView);
                        dialog.show();
                        //确定，但是不查询？
                        btn_ok.setOnClickListener(v -> {
                            if (lastPosition == -1) {
                                ToastUtil.showShort("请选择一个区域");
                                return;
                            }
                            dialog.dismiss();
//                            currDrawType = DRAWTYPE_ADMIN_REGION;
//                            showCancelButton();
//                            searchAdminRegion(response.get(lastPosition).getAreaName());
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                    }
                });
    }

    private void setBufferGeometry(int distance) {
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(Color.RED,
                distance,
                SimpleLineSymbol.STYLE.SOLID
        );
        Geometry geometry = mMapView.getCurrentDrawGraphic().getGeometry();
        Graphic graphic = new Graphic(geometry, lineSymbol);
        mMapView.addDrawLayerGraphic(graphic);
    }

    private void searchGeometry() {
        showLoading();
        restoreSpaceStatus();

        mMapView.queryGeometry(getActivity(),
                getString(R.string.feature_server_url),
                new BaseMapView.MainThreadCallback<FeatureResult>() {
                    @Override
                    public void onCallback(FeatureResult objects) {
                        dismissLoading();
                        showCompanyMarker(objects);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        dismissLoading();
                    }
                });
    }

    private void restoreSpaceStatus() {
        mMapView.setCurrentDrawSpace(BaseMapView.SPACE_NONE);
        AppCompatImageView ivSpace = mCVSpace.findViewById(R.id.aci_space);
        ivSpace.setImageResource(R.mipmap.ic_kongjian);
        mMapView.clearAll();
    }

    private void restoreAll() {
        restoreSpaceStatus();
        hideBottomLayout();
        hideClearBtn();
        mMapView.clearAll();
    }

    private void showClearBtn() {
        mCVClear.setVisibility(View.VISIBLE);
    }

    private void hideClearBtn() {
        mCVClear.setVisibility(View.GONE);
    }

    private boolean isFirst;
    private String CGFFDate, CGFTDate;
    private TextView calendar1Content, calendar2Content;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        if (isFirst) {
            CGFFDate = year + "-" + month + "-" + dayOfMonth;
            calendar1Content.setText(CGFFDate);
        } else {
            CGFTDate = year + "-" + month + "-" + dayOfMonth;
            String dateTime = CGFTDate;

            Calendar c = Calendar.getInstance();

            try {
                c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long DateStringXZ = c.getTimeInMillis();
            Date date = new Date();
            long DateStringNow = date.getTime();
            if (DateStringNow >= DateStringXZ) {
                calendar2Content.setText(CGFTDate);
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                calendar2Content.setText(dateString);
            }
        }
    }

    //获取分类信息，成果类型那一坨
    private void getClassifyData() {
        showLoading();
        APIService.getInstance()
                .getfruitCategoryList(new SimpleSubscriber<List<FruitCategoryListModel>>() {
                    @Override
                    public void onResponse(List<FruitCategoryListModel> response) {
                        dismissLoading();
                        if (response == null || response.isEmpty()) {
                            return;
                        }
                        mClassifyList.clear();
                        mClassifyList.addAll(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                    }
                });
    }

    private void getOrderList(OrderBody orderBody) {
        showLoading();
        Map<Object, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(orderBody.key)) {
            map.put("key", orderBody.key);
        }
        if (!TextUtils.isEmpty(orderBody.reportNo)) {
            map.put("reportNo", orderBody.reportNo);
        }
        if (!TextUtils.isEmpty(orderBody.otherKey)) {
            map.put("otherKey", orderBody.otherKey);
        }
        if (!TextUtils.isEmpty(orderBody.reportTimeBegin)) {
            map.put("reportTimeBegin", orderBody.reportTimeBegin);
        }
        if (!TextUtils.isEmpty(orderBody.reportTimeBegin)) {
            map.put("reportTimeEnd", orderBody.reportTimeBegin);
        }
        APIService.getInstance()
                .getOrderList(map, new SimpleSubscriber<List<OrderModel>>() {
                    @Override
                    public void onResponse(List<OrderModel> response) {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                    }
                });
    }
}
