package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.drawtool.DrawTool;
import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.IndustryCategoryModel;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.activity.MainActivity;
import android.zhixun.uiho.com.gissystem.ui.activity.UnitDetailActivity;
import android.zhixun.uiho.com.gissystem.ui.adapter.MainBottomAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.UnitFilterAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.UnitFilterUnitAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;
import android.zhixun.uiho.com.gissystem.ui.widget.DialogUtil;
import android.zhixun.uiho.com.gissystem.ui.widget.DividerGridItemDecoration;
import android.zhixun.uiho.com.gissystem.ui.widget.DragLayout;
import android.zhixun.uiho.com.gissystem.ui.widget.SimpleAlertDialog;
import android.zhixun.uiho.com.gissystem.ui.widget.SpaceDialog;

import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView.DEM_LAYER;


/**
 * 单位
 */

@Keep
public class UnitFragment extends BaseFragment implements View.OnClickListener {

    private BaseMapView mMapView;
    private View mCVLayer, mCVSift, mCVLocation, mZoomIn, mZoomOut, mCVSpace, mCVClear;
    private ImageView mIvUser;
    private TextView mTvSearch;
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

    public UnitFragment() {
        Bundle args = new Bundle();
        args.putString("name", this.getClass().getSimpleName());
        setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_unit, container, false);
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
        mZoomIn = view.findViewById(R.id.iv_zoom_in);
        mZoomOut = view.findViewById(R.id.iv_zoom_out);
        mCVSpace = view.findViewById(R.id.cv_space);
        mCVClear = view.findViewById(R.id.cv_clear);
        mIvUser = view.findViewById(R.id.iv_user);
        mTvSearch = view.findViewById(R.id.tv_search);
        mEtSearch = view.findViewById(R.id.et_search);
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
        mMapView.setOnSingleTapListener(this::OnMapSingleTap);
        mMapView.setOnStatusChangedListener(this::OnStatusChange);
    }

    private void OnStatusChange(Object o, OnStatusChangedListener.STATUS status) {
        switch (status) {
            case INITIALIZED:
                location();
                break;
            case LAYER_LOADED:

                break;
        }
    }

    private void location() {
        AndPermission.with(this)
                .permission(Manifest.permission_group.LOCATION)
                .callback(listener)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            mMapView.setScale(100000, true);
            mMapView.location();
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {

        }
    };

    public boolean isFirstLocation = true;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (isFirstLocation) {
                mMapView.setScale(100000, true);
                isFirstLocation = false;
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void initData() {
        showLoading();
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
            case R.id.iv_zoom_in://
                mMapView.zoomin();
                break;
            case R.id.iv_zoom_out://
                mMapView.zoomout();
                break;
            case R.id.cv_space://空间查询
                if (mMapView.getCurrentDrawSpace() == BaseMapView.SPACE_NONE) {
                    showSpaceDialog(v);
                } else if (mMapView.getCurrentDrawSpace() == BaseMapView.SPACE_BUFFER) {
                    showBufferInputDialog(v);
                } else if (mMapView.getCurrentDrawSpace() == BaseMapView.SPACE_POLYGON) {
                    setPolygon();
                } else {
//                    searchGeometry();
                    getCompanyList(mEtSearch.getText().toString(), -1, -1);
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
                    ToastUtil.showShort("请输入单位名称或组织机构代码后再搜索");
                    return;
                }
                getCompanyList(searchStr, -1, -1);
                break;
        }
    }

    private void setPolygon() {
        mMapView.setCurrentDrawGraphic(mMapView.getDrawTool().drawGraphic);
//        searchGeometry();
        getCompanyList(mEtSearch.getText().toString(), -1, -1);
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
            restoreAll();
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

    private void showSiftDialog(View view) {
        View contentView = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.layout_unit_filter, (ViewGroup) getView(),
                        false);
        DialogUtil.getInstance().showAnchorDialog(contentView, view);
        final EditText etSearch = contentView.findViewById(R.id.et_search);
        //rv填充数据
        RecyclerView zcdRv = contentView.findViewById(R.id.recyclerViewZCD);
        RecyclerView lbRv = contentView.findViewById(R.id.recyclerViewLB);
        DividerGridItemDecoration divider =
                new DividerGridItemDecoration(getActivity());
        zcdRv.addItemDecoration(divider);
        lbRv.addItemDecoration(divider);
        zcdRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        lbRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        //注册地
        UnitFilterUnitAdapter unitZCDAdapter =
                new UnitFilterUnitAdapter(getActivity(), areaModelList);
        zcdRv.setAdapter(unitZCDAdapter);
        unitZCDAdapter.setOnItemClickListener((view1, position) -> {
            if (position == 0) {//选择全部
                for (AreaModel areaModel : areaModelList) {
                    areaModel.setChecked(false);
                }
                areaModelList.get(0).setChecked(true);
                zcdLastPosition = position;
            } else {
                //让'全部'不选择，让当前这个取反
                areaModelList.get(0).setChecked(false);
                if (zcdLastPosition == position) return;
                areaModelList.get(position).setChecked(true);
                areaModelList.get(zcdLastPosition).setChecked(false);
                zcdLastPosition = position;
            }
            ZCD_CODE = areaModelList.get(position).getAreaId();
            unitZCDAdapter.notifyDataSetChanged();
        });
        //类别
        UnitFilterAdapter unitLBAdapter =
                new UnitFilterAdapter(getActivity(), industryCategoryModelList);
        lbRv.setAdapter(unitLBAdapter);
        unitLBAdapter.setOnItemClickListener((view12, position) -> {
            if (position == 0) {//如果选择的全部
                for (IndustryCategoryModel list : industryCategoryModelList) {
                    list.setChecked(false);
                }
                //让'全部'选中
                industryCategoryModelList.get(0).setChecked(true);
                lbLastPosition = position;
            } else {//如果选择的不是'全部'
                //让'全部'不选择，让当前这个取反
                industryCategoryModelList.get(0).setChecked(false);
                if (lbLastPosition == position) return;
                industryCategoryModelList.get(position).setChecked(true);
                industryCategoryModelList.get(lbLastPosition).setChecked(false);
                lbLastPosition = position;
            }
            HYLB_CODE = industryCategoryModelList.get(position).getIndustryCategoryId();
            unitLBAdapter.notifyDataSetChanged();
        });

        Button btnQuery = contentView.findViewById(R.id.acb_query);
        btnQuery.setOnClickListener(v -> {
            String searchStr = etSearch.getText().toString();
            searchSift(searchStr);
            DialogUtil.getInstance().dismiss();
        });
    }

    private void searchSift(String searchStr) {
        getCompanyList(searchStr, ZCD_CODE, HYLB_CODE);
    }

    private void getCompanyList(String searchStr, long areaId, long industryCategoryId) {
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
                        if (mMapView.getCurrentDrawSpace() == BaseMapView.SPACE_NONE) {
                            searchSql(companyList);
                        } else {
                            searchGeometry(companyList);
                        }
//                        showBottomLayout(companyList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }

    private void searchSql(List<CompanyDetailModel> response) {
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
                        if (objects.featureCount() == 0) {
                            ToastUtil.showShort("未获取单位信息,请重试");
                            restoreAll();
                            return;
                        }
                        showCompanyMarker(objects, response);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showShort("获取单位信息失败");
                    }
                });
    }

    private void showCompanyMarker(FeatureResult objects, List<CompanyDetailModel> companyList) {
        List<CompanyDetailModel> exitsCompanyList = new ArrayList<>();
        for (Object object : objects) {
//            if (object == null) continue;
            for (CompanyDetailModel companyDetailModel : companyList) {

                if (object instanceof Feature) {
                    Feature feature = (Feature) object;
                    int unitid = (int) feature.getAttributeValue("UNITID");
                    if (companyDetailModel.getCompanyId() != unitid)
                        continue;

                    PictureMarkerSymbol symbol =
                            new PictureMarkerSymbol(ContextCompat.getDrawable(getActivity(),
                                    R.drawable.ic_location_green));
                    Graphic graphic = new Graphic(feature.getGeometry(),
                            symbol, feature.getAttributes());
                    Location location = mMapView.getLocationDisplayManager().getLocation();
                    if (location != null) {
                        Point point1 = new Point(location.getLongitude(), location.getLatitude());
                        Point point2 = (Point) graphic.getGeometry();
                        double distance = GeometryEngine.geodesicDistance(point1, point2,
                                SpatialReference.create(SpatialReference.WKID_WGS84),
                                new LinearUnit(LinearUnit.Code.KILOMETER));
                        LogUtil.d("distance == " + distance);
                        companyDetailModel.distance = distance;
                    }
                    mMapView.addDrawLayerGraphic(graphic);
                    if (!exitsCompanyList.contains(companyDetailModel)) {
                        exitsCompanyList.add(companyDetailModel);
                    }
                }
            }
        }
        showBottomLayout(exitsCompanyList);
    }

    private void OnMapSingleTap(float x, float y) {
        GraphicsLayer drawLayer = mMapView.getDrawLayer();
        int[] ids = drawLayer.getGraphicIDs(x, y,
                1, 1);
        if (ids.length == 0)
            return;
        Graphic graphic = drawLayer.getGraphic(ids[0]);
        if (graphic == null)
            return;

        int unitID = (int) graphic.getAttributeValue("UNITID");
        for (CompanyDetailModel model : companyList) {
            if (model.getCompanyId() != unitID) {
                continue;
            }
            showCallout(graphic, model);
        }

    }

    private void showCallout(Graphic graphic, CompanyDetailModel model) {
        Callout callout = mMapView.getCallout();
        View calloutView = View.inflate(getActivity(), R.layout.view_callout, null);
        TextView tv_companyName = calloutView.findViewById(R.id.tv_companyName);
        TextView tv_address = calloutView.findViewById(R.id.tv_address);
        ImageView ivDetail = calloutView.findViewById(R.id.iv_detail);

        tv_companyName.setText(model.getCompanyName());
        tv_address.setText(model.getCompanyAddre());
        ivDetail.setOnClickListener(v -> UnitDetailActivity.navWith(getActivity(), model));

        callout.setCoordinates(((Point) graphic.getGeometry()));
        callout.setContent(calloutView);
        callout.show();
    }

    private void showBottomLayout(List<CompanyDetailModel> companyList) {
        mCVClear.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).hideBottomNav();
        MainBottomAdapter bottomAdapter =
                new MainBottomAdapter(getActivity(), companyList);
        Location location = mMapView.getLocationDisplayManager().getLocation();
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContentRv.setAdapter(bottomAdapter);
        dragView.setVisibility(View.VISIBLE);
        mDragLayout.animaToCenter();

        bottomAdapter.setOnItemClickListener((view, position) -> {
            switch (view.getId()) {
                case R.id.rl_location:
                    int companyId = companyList.get(position).getCompanyId();
                    for (int id : mMapView.getDrawLayer().getGraphicIDs()) {
                        Graphic graphic = mMapView.getDrawLayer().getGraphic(id);
                        int unit_id = (int) graphic.getAttributeValue("UNITID");
                        if (companyId != unit_id) continue;
                        switch (graphic.getGeometry().getType()) {
                            case POINT:
                                Point point = (Point) graphic.getGeometry();
                                mMapView.centerAt(point, true);
                                showCallout(graphic, companyList.get(position));
                                break;
                        }
                    }

                    break;
                case R.id.rl_detail:
                    CompanyDetailModel companyDetailModel = companyList.get(position);
                    Intent intent = new Intent(getActivity(), UnitDetailActivity.class);
                    intent.putExtra("unitModel", companyDetailModel);
                    startActivity(intent);
                    break;
            }

        });
    }

    private void hideBottomLayout() {
        mCVClear.setVisibility(View.GONE);
        mDragLayout.exit();
        dragView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).showBottomNav();
    }

    private void showSpaceDialog(View view) {
        int[] resIds = {R.drawable.ic_rect, R.drawable.ic_poygen, R.drawable.ic_buffer};
        String[] texts = {"矩形框选", "多边形框选", "缓冲区查询"};
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
                    }
                    mCVSpace.setBackgroundResource(R.drawable.ic_sure);
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
                .visibleEditText()
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

    private void searchGeometry(List<CompanyDetailModel> companyList) {
        showLoading();

        mMapView.queryGeometry(getActivity(),
                getString(R.string.feature_server_url),
                new BaseMapView.MainThreadCallback<FeatureResult>() {
                    @Override
                    public void onCallback(FeatureResult objects) {
                        dismissLoading();
                        restoreSpaceStatus();
                        if (objects.featureCount() == 0) {
                            ToastUtil.showShort("未获取单位信息,请重试");
                            restoreAll();
                            return;
                        }
                        showCompanyMarker(objects, companyList);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        dismissLoading();
                        ToastUtil.showEmpty();
                        restoreSpaceStatus();
                    }
                });
    }

    private void restoreSpaceStatus() {
        mMapView.setCurrentDrawSpace(BaseMapView.SPACE_NONE);
        mCVSpace.setBackgroundResource(R.drawable.ic_space);
        mMapView.clearAll();
    }

    private void restoreAll() {
        restoreSpaceStatus();
        hideBottomLayout();
        hideClearBtn();
        mMapView.clearAll();
        mMapView.getCallout().hide();
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
}
