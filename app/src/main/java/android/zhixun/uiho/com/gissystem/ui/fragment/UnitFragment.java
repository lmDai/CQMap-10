package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.drawtool.DrawEvent;
import android.zhixun.uiho.com.gissystem.drawtool.DrawEventListener;
import android.zhixun.uiho.com.gissystem.drawtool.DrawTool;
import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.IndustryCategoryModel;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.adapter.MainBottomAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.UnitFilterAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.UnitFilterUnitAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;
import android.zhixun.uiho.com.gissystem.ui.widget.BottomSheetPw;
import android.zhixun.uiho.com.gissystem.ui.widget.DialogUtil;
import android.zhixun.uiho.com.gissystem.ui.widget.DividerGridItemDecoration;
import android.zhixun.uiho.com.gissystem.ui.widget.SpaceDialog;

import com.esri.android.map.GraphicsLayer;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;
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
public class UnitFragment extends BaseFragment implements View.OnClickListener, DrawEventListener {

    private BaseMapView mMapView;
    private View mCVLayer, mCVSift, mCVLocation, mZoomIn, mZoomOut, mCVSpace, mCVClear;
    //各大区县，注册地
    private List<AreaModel> areaModelList = new ArrayList<>();
    //单位类别
    private List<IndustryCategoryModel> industryCategoryModelList = new ArrayList<>();
    //注册地的code
    private long ZCD_CODE = -1;
    //类别的code
    private long HYLB_CODE = -1;
    private GraphicsLayer drawLayer;
    private DrawTool drawTool;
    //
    GraphicsLayer graphicsLayer = new GraphicsLayer();


    public UnitFragment() {
        Bundle args = new Bundle();
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

        drawLayer = new GraphicsLayer();
        mMapView.addLayer(drawLayer);
        drawTool = new DrawTool(mMapView);
        drawTool.addEventListener(this);

        mMapView.addLayer(graphicsLayer);
    }

    private void initEvent() {
        mCVLayer.setOnClickListener(this);
        mCVSift.setOnClickListener(this);
        mCVLocation.setOnClickListener(this);
        mZoomIn.setOnClickListener(this);
        mZoomOut.setOnClickListener(this);
        mCVSpace.setOnClickListener(this);
        mCVClear.setOnClickListener(this);
    }

    private void initData() {
        getSiftZCDData();
        getSiftLBData();
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
            case R.id.cv_space:
                showSpaceDialog(v);
                break;
            case R.id.cv_clear:
                drawLayer.removeAll();
                drawTool.deactivate();
                mCVClear.setVisibility(View.GONE);
                break;
        }
    }

    private void showSwitchLayerDialog(View view) {
        View contentView = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.layout_map_switch, (ViewGroup) getView(),
                        false);
        DialogUtil.getInstance().showAnchorDialog(contentView, view);
        RadioGroup rgLayerSwitch = contentView.findViewById(R.id.rg_layer_switch);
        switch (mMapView.getCurrentLayer()) {
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
                    mMapView.setCurrentLayer(DEM_LAYER);
                    break;
                case R.id.rb_vector:
                    mMapView.setCurrentLayer(BaseMapView.VEC_LAYER);
                    break;
                case R.id.rb_image:
                    mMapView.setCurrentLayer(BaseMapView.IMG_LAYER);
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
                ZCD_CODE = areaModelList.get(position).getAreaId();
            }
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
                HYLB_CODE = industryCategoryModelList.get(position).getIndustryCategoryId();
            }
            unitLBAdapter.notifyDataSetChanged();
        });

        DialogUtil.getInstance().showAnchorDialog(contentView, view);
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
                        showMapSymbol(response);
                        showBottomPw(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
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
        QueryParameters qp = new QueryParameters();
        qp.setWhere(whereClause);
        QueryTask qTask = new QueryTask(getString(R.string.feature_server_url));
        qTask.execute(qp, new CallbackListener<FeatureResult>() {
            @Override
            public void onCallback(FeatureResult objects) {
                if (objects.featureCount() == 0) {
                    ToastUtil.showShort("未获取单位信息,请重试");
                    return;
                }
                for (Object object : objects) {
                    if (object instanceof Feature) {
                        Feature feature = (Feature) object;
                        SimpleMarkerSymbol symbol =
                                new SimpleMarkerSymbol(Color.RED, 12, SimpleMarkerSymbol.STYLE.CIRCLE);
                        // turn feature into graphic
                        Graphic graphic = new Graphic(feature.getGeometry(),
                                symbol, feature.getAttributes());
                        // add graphic to layer
                        drawLayer.addGraphic(graphic);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtil.showShort("获取单位信息失败");
            }
        });
    }

    private void showBottomPw(List<CompanyDetailModel> response) {
        MainBottomAdapter bottomAdapter =
                new MainBottomAdapter(getActivity(), response);
        BottomSheetPw pw = new BottomSheetPw(getActivity());
        pw.setAdapter(bottomAdapter);
        pw.showAtLocation(mCVSift, Gravity.CENTER, 0, 0);
    }

    private void showSpaceDialog(View view) {
        int[] resIds = {R.mipmap.ic_add, R.mipmap.ic_add, R.mipmap.ic_add};
        String[] texts = {"矩形", "多边形", "缓冲区查询"};
        new SpaceDialog(getActivity(), (ViewGroup) getView())
                .setData(resIds, texts)
                .show(view)
                .setOnItemClickListener((view1, position) -> {
                    switch (position) {
                        case 0://矩形
                            drawTool.activate(DrawTool.ENVELOPE);
                            break;
                        case 1://多边形
                            drawTool.activate(DrawTool.POLYGON);
                            break;
                        case 2://缓冲区查询
                            drawTool.activate(DrawTool.POLYLINE);
                            break;
                    }
                    mCVClear.setVisibility(View.VISIBLE);
                });
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

    @Override
    public void handleDrawEvent(DrawEvent event) {
        drawLayer.addGraphic(event.getDrawGraphic());
    }
}
