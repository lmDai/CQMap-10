package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.drawtool.DrawTool;
import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.IndustryCategoryModel;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.activity.MainActivity;
import android.zhixun.uiho.com.gissystem.ui.adapter.MainBottomAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;
import android.zhixun.uiho.com.gissystem.ui.widget.DialogUtil;
import android.zhixun.uiho.com.gissystem.ui.widget.DragLayout;
import android.zhixun.uiho.com.gissystem.ui.widget.SimpleAlertDialog;
import android.zhixun.uiho.com.gissystem.ui.widget.SpaceDialog;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yibogame.util.DateUtil;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView.DEM_LAYER;


/**
 * 单位
 */

@Keep
public class DispatchFragment extends BaseFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private BaseMapView mMapView;
    private View mCVLayer, mCVSift, mCVLocation, mZoomIn, mZoomOut, mCVSpace, mCVClear,
            mCvClassifySearch;
    private ImageView mIvUser, mIvSearch;
    private EditText mEtSearch;
    //各大区县，注册地
    private List<AreaModel> areaModelList = new ArrayList<>();
    //单位类别
    private List<IndustryCategoryModel> industryCategoryModelList = new ArrayList<>();
    //注册地的code
    private long ZCD_CODE = -1;
    //类别的code
    private long HYLB_CODE = -1;
    //公司信息集合
    private List<CompanyDetailModel> companyList = new ArrayList<>();
    private DragLayout mDragLayout;
    private RecyclerView mContentRv;
    private View dragView;
    //
    private AppCompatImageView calendar1, calendar2;
    private RelativeLayout relativeLayoutLeft, relativeLayoutRight;

    public DispatchFragment() {
        Bundle args = new Bundle();
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
        mIvSearch = view.findViewById(R.id.aciv_search);
        mEtSearch = view.findViewById(R.id.et_search);
        mCvClassifySearch = view.findViewById(R.id.cv_classifySearch);
        mEtSearch.setEnabled(false);
        mEtSearch.setText("fafafafaf");
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
        mIvSearch.setOnClickListener(this);
        mCvClassifySearch.setOnClickListener(this);
    }

    private void initData() {
        getSiftZCDData();
        getSiftLBData();
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
            case R.id.aciv_search:
                String searchStr = mEtSearch.getText().toString();
                if (TextUtils.isEmpty(searchStr)) {
                    ToastUtil.showShort("请输入单位名称或组织机构代码后再搜索");
                    return;
                }
                getCompanyList(searchStr, -1, -1);
                break;
            case R.id.cv_classifySearch:
                ToastUtil.showShort("cv_classifySearch");
                break;
        }
    }

    private void setPloygon() {
        mMapView.setCurrentDrawGraphic(mMapView.getDrawTool().drawGraphic);
        searchGeometry();
//        mMapView.setCurrentDrawSpace(BaseMapView.SPACE_POLYGON_SET_FINISH);
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

    private int zcdLastPosition = 0;
    private int lbLastPosition = 0;

    private TextView calendar1_content, calendar2_content;

    private void showSiftDialog(View view) {
        View contentView = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.layout_result_dispatch_filter,
                        (ViewGroup) ((Activity) getActivity()).getWindow().getDecorView().getRootView(),
                        false);
//                RecyclerView recyclerView1 = (RecyclerView) contentView.findViewById(R.id.recyclerView1);
        calendar1 = (AppCompatImageView) contentView.findViewById(R.id.calendar1);
        calendar2 = (AppCompatImageView) contentView.findViewById(R.id.calendar2);
        relativeLayoutLeft = (RelativeLayout) contentView.findViewById(R.id.relativeLayoutLeft);
        relativeLayoutRight = (RelativeLayout) contentView.findViewById(R.id.relativeLayoutRight);

        calendar1Content = (TextView) contentView.findViewById(R.id.calendar1_content);
        calendar2Content = (TextView) contentView.findViewById(R.id.calendar2_content);

//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        calendar2Content.setText("结束时间");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//            String dateString = year + "-" + month + "-" + day;
        calendar1Content.setText("开始时间");

        relativeLayoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirst = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd1 = DatePickerDialog.newInstance(DispatchFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH) - 1,
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd1.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd1.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        relativeLayoutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
        AppCompatButton acbQuery = (AppCompatButton) contentView.findViewById(R.id.acb_query);
        initEtSearchForDialog(contentView);
        initAcbSearchForDialog(acbQuery);
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
    private void initAcbSearchForDialog(AppCompatButton acbQuery) {
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
//            RxBus.getInstance()
//                    .send(new MainActivityAction(MainActivityAction.CLEAR_TEXT));
//            mProgressDialog.setMessage("搜索中……");
//            mProgressDialog.show();
//            mDispatchFragmentActionsCreator
//                    .buildSql(
//                            etSearchDialog.getText().toString(),
//                            etCaseCode.getText().toString(),
//                            etDataCode.getText().toString(),
//                            data1,
//                            data2);
        });
    }

    private void searchSift(String searchStr) {
        getCompanyList(searchStr, ZCD_CODE, HYLB_CODE);
    }

    private void getCompanyList(String searchStr, long industryCategoryId, long areaId) {
        showLoading();
        Map<Object, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(searchStr)) {
            map.put("key", searchStr);
        }
        if (industryCategoryId != -1) {
            map.put("industryCategoryId", industryCategoryId);
        }
        if (areaId != -1) {
            map.put("areaId", areaId);
        }
        APIService.getInstance()
                .getCompanyList(map, new SimpleSubscriber<List<CompanyDetailModel>>() {
                    @Override
                    public void onResponse(List<CompanyDetailModel> response) {
                        dismissLoading();
                        if (response == null || response.isEmpty()) {
                            ToastUtil.showShort("未查询到相关信息");
                            return;
                        }
                        companyList.clear();
                        companyList.addAll(response);
                        showMapSymbol(companyList);
                        showBottomLayout(companyList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                        ToastUtil.showShort("未查询到相关信息");
                    }
                });
    }

    private void showMapSymbol(List<CompanyDetailModel> response) {
        if (response == null || response.isEmpty()) return;
        StringBuilder sb = new StringBuilder();
        for (CompanyDetailModel model : response) {
            sb.append(model.getCompanyId());
            sb.append(",");
        }
        String in = sb.substring(0, sb.length() - 1);
        String whereClause = String.format("UNITID in (%s)", in);
        LogUtil.d("whereClause == " + whereClause);

        mMapView.querySQL(getActivity(),
                getString(R.string.feature_server_url), whereClause,
                new BaseMapView.MainThreadCallback<FeatureResult>() {
                    @Override
                    public void onCallback(FeatureResult objects) {
                        showCompanyMarker(objects);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showShort("获取单位信息失败");
                    }
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

//                String UNITID = (String) feature.getAttributeValue("UNITID");

            }
        }
    }

    private void showBottomLayout(List<CompanyDetailModel> response) {
        mCVClear.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).hideBottomNav();
        MainBottomAdapter bottomAdapter =
                new MainBottomAdapter(getActivity(), response);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContentRv.setAdapter(bottomAdapter);
        mDragLayout.animaToCenter();
        dragView.setVisibility(View.VISIBLE);
        bottomAdapter.setOnItemClickListener((view, position) -> {
            int companyId = response.get(position).getCompanyId();
            for (int id : mMapView.getGraphicLayer().getGraphicIDs()) {
                Graphic graphic = mMapView.getGraphicLayer().getGraphic(id);
                int unit_id = (int) graphic.getAttributeValue("UNITID");
                if (companyId == unit_id) {
//                    ToastUtil.showShort("========");
                    if (graphic.getGeometry() instanceof Point) {
                        Point point = (Point) graphic.getGeometry();
                        mMapView.centerAt(point, true);
                    }
                }
            }
//            ToastUtil.showShort("" + position);
        });
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

    //获取重庆各大区县
    private void getSiftZCDData() {
        showLoading();
        Map<Object, Object> map = new HashMap<>();
        APIService.getInstance().getAreaList(map, new SimpleSubscriber<List<AreaModel>>() {
            @Override
            public void onResponse(List<AreaModel> response) {
                dismissLoading();
                areaModelList.clear();
                AreaModel areaModel = new AreaModel();
                areaModel.setAreaId(-1);
                areaModel.setAreaName("全部");
                areaModelList.add(areaModel);
                areaModelList.addAll(response);
                //如果注册地还没选，那就默认让它选择全部
                if (ZCD_CODE == -1) {
                    for (AreaModel cqPrefectureModel : areaModelList) {
                        cqPrefectureModel.setChecked(false);
                    }
                    areaModelList.get(0).setChecked(true);
                } else {
                    //如果注册地已经选择，那么选中它
                    for (AreaModel cqPrefectureModel : areaModelList) {
                        if (cqPrefectureModel.getAreaId() == ZCD_CODE) {
                            cqPrefectureModel.setChecked(true);
                        } else {
                            cqPrefectureModel.setChecked(false);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissLoading();
            }
        });
    }

    //获取单位类别
    private void getSiftLBData() {
        showLoading();
        Map<Object, Object> map = new HashMap<>();
        APIService.getInstance()
                .getIndustryCategoryList(map, new SimpleSubscriber<List<IndustryCategoryModel>>() {
                    @Override
                    public void onResponse(List<IndustryCategoryModel> response) {
                        dismissLoading();
                        industryCategoryModelList.clear();
                        IndustryCategoryModel industryCategoryModelTemp = new IndustryCategoryModel();
                        industryCategoryModelTemp.setIndustryCategoryId(-1);
                        industryCategoryModelTemp.setIndustryCategoryName("全部");
                        industryCategoryModelList.add(industryCategoryModelTemp);
                        industryCategoryModelList.addAll(response);
                        //如果类别还没选，那就默认让它选择全部
                        if (HYLB_CODE == -1) {
                            for (IndustryCategoryModel industryCategoryModel : industryCategoryModelList) {
                                industryCategoryModel.setChecked(false);
                            }
                            industryCategoryModelList.get(0).setChecked(true);
                        } else {
                            //如果类别已经选择，那么选中它
                            for (IndustryCategoryModel industryCategoryModel : industryCategoryModelList) {
                                if (industryCategoryModel.getIndustryCategoryId() == HYLB_CODE) {
                                    industryCategoryModel.setChecked(true);
                                } else {
                                    industryCategoryModel.setChecked(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                    }
                });
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
}
