package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.drawtool.DrawTool;
import android.zhixun.uiho.com.gissystem.flux.body.ReportHandoutListBody;
import android.zhixun.uiho.com.gissystem.flux.models.FruitListModel;
import android.zhixun.uiho.com.gissystem.flux.models.GethandoutConditionByFCModel;
import android.zhixun.uiho.com.gissystem.flux.models.HandoutFruitModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.FruitCategoryListModel;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.activity.MainActivity;
import android.zhixun.uiho.com.gissystem.ui.adapter.AdminRegionAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.ClassifyFlowTypeAttrValAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;
import android.zhixun.uiho.com.gissystem.ui.widget.DialogUtil;
import android.zhixun.uiho.com.gissystem.ui.widget.DragLayout;
import android.zhixun.uiho.com.gissystem.ui.widget.SimpleAlertDialog;
import android.zhixun.uiho.com.gissystem.ui.widget.SimpleVerRv;
import android.zhixun.uiho.com.gissystem.ui.widget.SpaceDialog;
import android.zhixun.uiho.com.gissystem.util.DensityUtils;
import android.zhixun.uiho.com.gissystem.util.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.util.ScreenUtil;

import com.alibaba.fastjson.JSON;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Polygon;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.LinearLayout.SHOW_DIVIDER_MIDDLE;
import static android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView.DEM_LAYER;

/**
 * Created by parcool on 2016/9/2.
 * 成果分发
 */

@Keep
public class DirectoryFragment extends BaseFragment implements View.OnClickListener {


    private BaseMapView mMapView;
    private View mCVLayer, mCVSift, mCVLocation, mZoomIn, mZoomOut, mCVSpace, mCVClear,
            mCvClassifySearch;
    private ImageView mIvUser, mIvSearchClear;
    private TextView mTvSearch;
    private EditText mEtSearch;
    private DragLayout mDragLayout;
    private View dragView;
    private SimpleVerRv mContentRv;
    private TextView mTvDataCount;
    private ImageView mIvArrow;
    private LinearLayout mLLTitle;
    //
    private ReportHandoutListBody mBody;
    private int mMapType = 0;
    //分类信息集合-成果类型那一坨
    private List<FruitCategoryListModel> mClassifyTypeList = new ArrayList<>();
    //分类信息集合-成果类型下面的集合
    private List<GethandoutConditionByFCModel> mFirstClassifyTypeBelowList = new ArrayList<>();
    private List<GethandoutConditionByFCModel> mClassifyTypeBelowList = new ArrayList<>();
    //分类信息集合
    private List<FruitListModel> mFruitList = new ArrayList<>();
    //
    private static final String FRUITID = "FRUITID";

    public DirectoryFragment() {
        Bundle args = new Bundle();
        args.putString("name", this.getClass().getSimpleName());
        setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_directory, container, false);
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
        getClassifyTypeData();
    }

    private void initBottomDragView(View view) {
        mDragLayout = view.findViewById(R.id.dragLayout);
        dragView = view.findViewById(R.id.bottom_drag_view);
        mContentRv = view.findViewById(R.id.recycler_view);
        mTvDataCount = view.findViewById(R.id.tv_dataCount);
        mIvArrow = view.findViewById(R.id.iv_arrow);
        mLLTitle = view.findViewById(R.id.ll_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_layer://切换地图样式
                showSwitchLayerDialog(v);
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
                onSpaceClick(v);
                break;
            case R.id.cv_clear:
                restoreSpaceStatus();
                break;
            case R.id.iv_user:
                ((MainActivity) getActivity()).openDrawer();
                break;
            case R.id.tv_search:
                searchBtnClick();
                break;
            case R.id.cv_classifySearch://分类查询
                showClassifyDialog(v);
                break;
            case R.id.aciv_clear:
                mMapType = 0;
                restoreAll();
                showSearchTextView();
                mBody = null;
                break;
        }
    }

    private void searchBtnClick() {
//        String searchStr = mEtSearch.getText().toString();
//        if (TextUtils.isEmpty(searchStr)) {
//            setSearhText("全部");
//        }

        showSearchClearView();
        getFruitList();
    }

    private void onSpaceClick(View v) {
        switch (mMapView.getCurrentDrawSpace()) {
            case BaseMapView.SPACE_NONE:
                showSpaceDialog(v);
                break;
            case BaseMapView.SPACE_RECT:
                if (mMapView.getCurrentDrawGraphic() == null) {
                    ToastUtil.showShort("请滑动屏幕框选查询区域");
                    return;
                }
                setSearhText(mEtSearch.getText().toString() + "矩形框选");
                break;
            case BaseMapView.SPACE_BUFFER:
                showBufferInputDialog(v);
                break;
            case BaseMapView.SPACE_POLYGON:
                setPolygon();
                if (mMapView.getCurrentDrawGraphic().getGeometry().isEmpty()) {
                    ToastUtil.showShort("请多次点击屏幕选择多边形查询的范围");
                    return;
                }
                setSearhText(mEtSearch.getText().toString() + "多边形框选");
                break;
            case BaseMapView.SPACE_BUFFER_SET_FINISH:
                if (mMapView.getCurrentDrawGraphic() == null) {
                    ToastUtil.showShort("请滑动屏幕框并输入缓冲区的范围");
                    return;
                }
                setSearhText(mEtSearch.getText().toString() + "缓冲区查询");
                break;
            case BaseMapView.SPACE_MAP_NUMBER:
                setSearhText(mEtSearch.getText().toString() + "图幅号查询");
                break;
            case BaseMapView.SPACE_ADMIN_REGION:
                if (mMapView.getCurrentDrawGraphic() == null) {
                    ToastUtil.showShort("请选择行政区域");
                    return;
                }
                setSearhText(mEtSearch.getText().toString() + "行政区域查询");
                break;
        }
    }

    private void setPolygon() {
        mMapView.setCurrentDrawGraphic(mMapView.getDrawTool().drawGraphic);
    }

    private void showSearchTextView() {
        mTvSearch.setVisibility(View.VISIBLE);
        mIvSearchClear.setVisibility(View.GONE);
        mEtSearch.setText("");
        mMapView.clearAll();
    }

    private void showSearchTextViewWithSapce() {
        mTvSearch.setVisibility(View.VISIBLE);
        mIvSearchClear.setVisibility(View.GONE);
    }

    private void showSearchClearView() {
        mIvSearchClear.setVisibility(View.VISIBLE);
        mTvSearch.setVisibility(View.GONE);
    }

    private void showClassifyDialog(View view) {
        showLoading();
        APIService.getInstance().getfruitCategoryList(
                new SimpleSubscriber<List<FruitCategoryListModel>>() {
                    @Override
                    public void onResponse(List<FruitCategoryListModel> response) {
                        dismissLoading();
                        if (response == null || response.isEmpty()) {
                            ToastUtil.showShort("未获取到信息");
                            return;
                        }
                        mClassifyTypeList.clear();
                        mClassifyTypeList.addAll(response);
                        mMapType = mClassifyTypeList.get(0).mapType;
                        bindViewClassify(view, mClassifyTypeList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                    }
                });

    }

    private void bindViewClassify(View view, List<FruitCategoryListModel> response) {

        View dialogRoot = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_classify_search, ((ViewGroup) getView()),
                        false);
        RecyclerView rv_flowType = dialogRoot.findViewById(R.id.rv_flow_type);
        response.get(0).selected = true;

        View ll_arrow = dialogRoot.findViewById(R.id.ll_arrow);
        View iv_arrow = dialogRoot.findViewById(R.id.iv_arrow);
        ll_arrow.setOnClickListener(v -> {
            iv_arrow.animate()
                    .rotationBy(180)
                    .start();
            int visible = rv_flowType.getVisibility() == View.VISIBLE ? View.GONE
                    : View.VISIBLE;
            rv_flowType.setVisibility(visible);
        });
        //成果类型列表
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
        //成果类型item的点击事件
        flowTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                response.get(position).selected = true;
                for (FruitCategoryListModel model : response) {
                    model.selected = response.indexOf(model) == position;
                }
                mMapType = response.get(position).mapType;
                flowTypeAdapter.notifyDataSetChanged();
            }
        });
        Button btnSearch = dialogRoot.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(v -> {
            if (mClassifyTypeList.isEmpty()) return;
            mBody = new ReportHandoutListBody();
            //填充请求参数
            StringBuilder sb = new StringBuilder();
            for (FruitCategoryListModel model : mClassifyTypeList) {
                if (model.selected) {
                    sb.append(model.categoryName);
                    sb.append(",");
                    mBody.fruitCategoryId = model.fruitCategoryId;
                }
            }
            if (!mClassifyTypeBelowList.isEmpty()) {

                for (GethandoutConditionByFCModel gethandoutConditionByFCModel : mClassifyTypeBelowList) {
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
                .getDirectoryHandoutConditionByFCList(model.fruitCategoryId,
                        new SimpleSubscriber<List<GethandoutConditionByFCModel>>() {
                            @Override
                            public void onResponse(List<GethandoutConditionByFCModel> response) {
                                dismissLoading();
                                if (response == null || response.isEmpty()) {
                                    return;
                                }
                                mClassifyTypeBelowList.clear();
                                mClassifyTypeBelowList.addAll(response);
                                bindViewClassifyTypeBelow(dialogRoot, mClassifyTypeBelowList);
                            }
                        });
    }

    private void bindViewClassifyTypeBelow(View dialogRoot, List<GethandoutConditionByFCModel> response) {
        LinearLayout llContainer = dialogRoot.findViewById(R.id.ll_container);
        llContainer.removeAllViews();

        for (GethandoutConditionByFCModel gcbfModel : response) {
            View itemView = LayoutInflater.from(dialogRoot.getContext())
                    .inflate(R.layout.dispatch_dialog_item, llContainer,
                            false);

            RecyclerView recyclerView = itemView.findViewById(R.id.recycler_view);
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
            //全部展开，收缩
            View ll_arrow = itemView.findViewById(R.id.ll_arrow);
            View iv_arrow = itemView.findViewById(R.id.iv_arrow);
            ll_arrow.setOnClickListener(v -> {
                iv_arrow.animate()
                        .rotationBy(180)
                        .start();
                int visible = recyclerView.getVisibility() == View.VISIBLE ? View.GONE
                        : View.VISIBLE;
                recyclerView.setVisibility(visible);
            });

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
    private void getFruitList() {
        showLoading();
        if (mBody == null) {
            mBody = new ReportHandoutListBody();

            if (mClassifyTypeList.isEmpty()) return;
            StringBuilder sb = new StringBuilder();
            mBody = new ReportHandoutListBody();
            mBody.fruitCategoryId = mClassifyTypeList.get(0).fruitCategoryId;
            sb.append(mClassifyTypeList.get(0).categoryName);
            mMapType = mClassifyTypeList.get(0).mapType;
            if (mFirstClassifyTypeBelowList.isEmpty())
                return;
            sb.append(",");
            GethandoutConditionByFCModel gethandoutConditionByFCModel = mFirstClassifyTypeBelowList.get(0);
            //attrValues
            ReportHandoutListBody.AttrValueList attrValueList =
                    new ReportHandoutListBody.AttrValueList(
                            gethandoutConditionByFCModel.fruitCategoryAttrId,
                            gethandoutConditionByFCModel.fruitCateGoryAttrVal
                                    .get(0).attrValue);
            mBody.attrValueList.add(attrValueList);
            List<GethandoutConditionByFCModel.FruitCateGoryAttrVal> fruitCateGoryAttrVal =
                    gethandoutConditionByFCModel.fruitCateGoryAttrVal;
            String attrValue = fruitCateGoryAttrVal.get(0).attrValue;
            sb.append(attrValue);
            setSearhText(sb.toString());
        }
        Map<Object, Object> map = new HashMap<>();
        if (!mBody.attrValueList.isEmpty()) {
            map.put("attrValueList", mBody.attrValueList);
        }
        if (mBody.fruitCategoryId != -1) {
            map.put("fruitCategoryId", mBody.fruitCategoryId);
        }
        if (!TextUtils.isEmpty(mBody.fruitIds)) {
            map.put("attrValueList", mBody.fruitIds);
        }
        map.put("page", -1);
        map.put("rows", -1);
        APIService.getInstance().getFruitList(map,
                new SimpleSubscriber<List<FruitListModel>>() {
                    @Override
                    public void onResponse(List<FruitListModel> response) {
                        dismissLoading();
                        if (response == null || response.isEmpty()) return;
                        mFruitList.clear();
                        mFruitList.addAll(response);
//                        showBottomLayout(mFruitList);
                        searchMapService(mFruitList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }

    private void searchMapService(List<FruitListModel> fruitList) {
        if (fruitList.isEmpty()) return;
        showLoading();
        StringBuilder sb = new StringBuilder();
        sb.append("FRUITID");
        sb.append(" in ");
        sb.append("(");
        int mapType = mMapType;
        int index = 0;
        for (FruitListModel model : fruitList) {
            index++;
            String fruitId = String.valueOf(model.fruitId);
            sb.append(fruitId);
            if (index > 999) {
                index = 0;
                sb.append(") OR FRUITID in (");
            } else {
                sb.append(",");
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
        showLoading();
        if (mMapView.getCurrentDrawSpace() == BaseMapView.SPACE_NONE) {
            showBottomLayout(mFruitList);
            querySql(mapType, whereClause, url);
        } else {
            switch (mMapView.getCurrentDrawSpace()) {
                case BaseMapView.SPACE_POLYGON:
                    setPolygon();
                    break;
            }
            queryGeometryAndSql(mapType, whereClause, url);
        }

        dismissLoading();
    }

    private void querySql(int mapType, String whereClause, String url) {
        mMapView.querySQL(getActivity(), url, whereClause,
                new BaseMapView.MainThreadCallback<FeatureResult>() {
                    @Override
                    public void onCallback(FeatureResult result) {
                        dismissLoading();
                        if (result.featureCount() == 0) {
                            ToastUtil.showShort("未获取信息,请重试");
                            restoreAll();
                            return;
                        }
                        showMapSymbol(result, mapType, mFruitList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dismissLoading();
                        ToastUtil.showShort("未获取信息,请重试");
                    }
                });
    }

    private void showMapSymbol(FeatureResult result, int mapType, List<FruitListModel> fruitList) {
        Symbol symbol;
        if (mapType == 1) {
            symbol = new PictureMarkerSymbol(getActivity(),
                    ContextCompat.getDrawable(getActivity(), R.drawable.ic_location_green));
        } else {
            symbol = createSimpleFillSymbol();
        }
        Graphic graphic;
        List<Graphic> graphicList = new ArrayList<>(1000);
        for (Object object : result) {
            if (object instanceof Feature) {
                Feature feature = (Feature) object;
                graphic = new Graphic(feature.getGeometry(),
                        symbol, feature.getAttributes());
                graphicList.add(graphic);
            }
        }
        if (!graphicList.isEmpty()) {
            Graphic[] graphics = graphicList.toArray(new Graphic[graphicList.size()]);
            mMapView.addGraphics(graphics);
            mMapView.centerAtGraphic(graphicList.get(0));
        }
        dismissLoading();
        //地图单击事件
        mMapView.setOnSingleTapListener((OnSingleTapListener) (float x, float y) -> {
            if (fruitList.isEmpty()) {
                ToastUtil.showShort("分类信息集合为空，请重新获取");
                return;
            }
            int[] ids = mMapView.getDrawLayer().getGraphicIDs(x, y,
                    1, 1);
            if (ids == null || ids.length == 0) {
//                                ToastUtil.showShort("图层为空，请再次点击");
                return;
            }
            GraphicsLayer drawLayer = mMapView.getDrawLayer();
            Graphic updateGraphic = drawLayer.getGraphic(ids[0]);
            Symbol updateSymbol;
            if (updateGraphic == null) return;
            double FRUIT_ID;
            if (mMapType == 1) {
                updateSymbol = new PictureMarkerSymbol(getActivity(),
                        ContextCompat.getDrawable(getActivity(), R.drawable.ic_location_red));
                FRUIT_ID = ((double) updateGraphic.getAttributeValue("FRUITID"));
            } else {
                updateSymbol = new SimpleFillSymbol(Color.RED);
                FRUIT_ID = ((Double) updateGraphic.getAttributeValue("FRUITID")).longValue();
            }
            drawLayer.updateGraphic(ids[0], updateSymbol);
            for (FruitListModel model : fruitList) {
                if (FRUIT_ID != model.fruitId) continue;

                model.selected = true;
                if (mContentRv.getAdapter() != null) {
                    int position = fruitList.indexOf(model);
                    mContentRv.getAdapter()
                            .notifyItemChanged(position);
//                    LogUtil.d("smoothScrollToPosition ==" + position);
                    mContentRv.scrollToPosition(position);
                }
            }
        });
    }

    @NonNull
    private SimpleFillSymbol createSimpleFillSymbol() {
        SimpleFillSymbol symbol =
                new SimpleFillSymbol(Color.parseColor("#8855ef"),
                        SimpleFillSymbol.STYLE.SOLID);
        symbol.setAlpha(180);
        symbol.setOutline(new SimpleLineSymbol(Color.WHITE, 2));
        return symbol;
    }

    //切换地图底图类型
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

    private void showBottomLayout(List<FruitListModel> fruitList) {
        if (fruitList == null || fruitList.isEmpty()) {
            return;
        }
        showLoading();
        ((MainActivity) getActivity()).hideBottomNav();
        mTvDataCount.setText(String.format("共%s条数据", fruitList.size()));
        //设置标题
        mLLTitle.removeAllViews();
        FruitListModel model = fruitList.get(0);
        List<FruitListModel.FruitAttrList> attrLists = JSON.parseArray(model.fruitAttrList,
                FruitListModel.FruitAttrList.class);
        for (FruitListModel.FruitAttrList attr : attrLists) {
            TextView textView = createRowText();
            textView.setText(attr.attrName);
            mLLTitle.addView(textView);
        }
        TextView tv_option = createRowText();
        tv_option.setText("操作");
        mLLTitle.addView(tv_option);
        //设置箭头点击事件
        mIvArrow.setOnClickListener(v -> {
            v.animate()
                    .rotationBy(180)
                    .start();
            int visible = mContentRv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            mContentRv.setVisibility(visible);
            mLLTitle.setVisibility(visible);
        });
        CommonAdapter<FruitListModel> adapter = new CommonAdapter<FruitListModel>(getActivity(),
                R.layout.item_directory_content, fruitList) {
            @Override
            protected void convert(ViewHolder holder, FruitListModel item, int position) {
                LinearLayout ll_row = holder.getView(R.id.ll_row);
                ll_row.removeAllViews();
                List<FruitListModel.FruitAttrList> attrLists = JSON.parseArray(item.fruitAttrList,
                        FruitListModel.FruitAttrList.class);
                for (FruitListModel.FruitAttrList attr : attrLists) {
                    TextView textView = createRowText();
                    textView.setText(attr.attrValue);
                    int color = item.selected ? Color.RED : Color.BLACK;
                    textView.setTextColor(color);
                    ll_row.addView(textView);
                }
                TextView tv_info = createRowText();
                tv_info.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                tv_info.setText("详情");
                tv_info.setOnClickListener(v -> showFruitInfoDialog(String.valueOf(item.fruitId)));
                ll_row.addView(tv_info);
            }
        };
        mContentRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                long fruitId = fruitList.get(position).fruitId;
                int[] graphicIDs = mMapView.getDrawLayer().getGraphicIDs();
                if (graphicIDs == null || graphicIDs.length == 0) {
                    ToastUtil.showShort("未在地图上找到相关信息，请重试");
                    return;
                }
                for (int id : graphicIDs) {
                    Graphic graphic = mMapView.getDrawLayer().getGraphic(id);
                    if (graphic == null) continue;
                    double attributeValue;
                    Symbol updateSymbol;
                    if (mMapType == 1) {
                        updateSymbol = new PictureMarkerSymbol(getActivity(),
                                ContextCompat.getDrawable(getActivity(), R.drawable.ic_location_red));
                        attributeValue = ((Double) graphic.getAttributeValue(FRUITID)).longValue();
                    } else {
                        updateSymbol = new SimpleFillSymbol(Color.RED);
                        attributeValue = ((Double) graphic.getAttributeValue(FRUITID)).longValue();
                    }

                    if (fruitId != attributeValue) continue;
                    mMapView.getDrawLayer().updateGraphic(id, updateSymbol);
                    if (graphic.getGeometry() == null) continue;
                    mMapView.centerAtGraphic(graphic);
                    fruitList.get(position).selected = true;
                    adapter.notifyItemChanged(position);
                }
            }
        });
        mDragLayout.animaToCenter();
        dragView.setVisibility(View.VISIBLE);
        dismissLoading();
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
        textView.setSingleLine();
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

    //显示详情dialog
    private void showFruitInfoDialog(String fruitId) {
        showLoading();
        APIService.getInstance()
                .getFruit(fruitId, new SimpleSubscriber<HandoutFruitModel>() {
                    @Override
                    public void onResponse(HandoutFruitModel response) {
                        dismissLoading();
                        List<HandoutFruitModel.FruitAttrList> fruitAttrList = response.fruitAttrList;
                        if (fruitAttrList == null || fruitAttrList.isEmpty()) {
                            ToastUtil.showShort("未查询到相关信息");
                            return;
                        }
                        AppCompatDialog dialog = new AppCompatDialog(getActivity());
                        View dialogView = View.inflate(getActivity(), R.layout.dialog_handout_info,
                                null);
                        LinearLayout ll_content = dialogView.findViewById(R.id.ll_content);
                        RecyclerView rv = dialogView.findViewById(R.id.recycler_view);
                        //标题
                        LinearLayout ll_title = createRowLL();
                        ll_title.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.c55f3f));
                        //内容
                        LinearLayout ll_data = createRowLL();
                        ll_data.setOnClickListener(v -> {
                            int visible = rv.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
                            rv.setVisibility(visible);
                        });
                        ll_data.setPadding(0, DensityUtils.dp2px(getActivity(), 1), 0, 0);

                        List<HandoutFruitModel.FruitAttrList> notListShowModels = new ArrayList<>();
                        for (HandoutFruitModel.FruitAttrList attrModel : fruitAttrList) {
                            if (!attrModel.isListShow) {
                                notListShowModels.add(attrModel);
                                continue;
                            }
                            TextView tv_title = createRowText();
                            tv_title.setTextColor(Color.WHITE);
                            TextView tv_data = createRowText();
                            tv_data.setBackgroundColor(ContextCompat.getColor(getActivity(),
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
                        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        rv.setAdapter(new CommonAdapter<HandoutFruitModel.FruitAttrList>(getActivity(),
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
                        dismissLoading();
                        ToastUtil.showShort("暂无数据");
                    }
                });
    }

    private void hideBottomLayout() {
        mCVClear.setVisibility(View.GONE);
        mDragLayout.exit();
        dragView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).showBottomNav();
    }

    //显示空间查询dialog
    private void showSpaceDialog(View view) {
        int[] resIds = {R.drawable.ic_rect, R.drawable.ic_poygen, R.drawable.ic_buffer
                , R.drawable.ic_map_number, R.drawable.ic_admi_region};
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
                            mMapView.getDrawTool().activate(DrawTool.POLYLINE);
                            mMapView.setCurrentDrawSpace(BaseMapView.SPACE_BUFFER);
                            break;
                        case 3://图幅号查询
                            mMapView.setCurrentDrawSpace(BaseMapView.SPACE_MAP_NUMBER);
                            showMapNumberDialog();
                            break;
                        case 4://行政区域查询
                            mMapView.setCurrentDrawSpace(BaseMapView.SPACE_ADMIN_REGION);
                            showAdminRegionDialog();
                            break;
                    }
                    mCVSpace.setBackgroundResource(R.drawable.ic_sure);
                    showClearBtn();
                    showSearchTextViewWithSapce();
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
                    float distance = Float.parseFloat(text);
                    setBufferGeometry(distance);
                    mMapView.setCurrentDrawSpace(BaseMapView.SPACE_BUFFER_SET_FINISH);
                    dialog1.dismiss();
                })
                .visibleEditText()
                .setCancelOnClickListener(R.string.alert_cancel, null)
                .alert();
    }

    private void showMapNumberDialog() {
        new SimpleAlertDialog(getActivity())
                .title("图幅号查询")
                .message("可输入多个图幅号，图幅号直接用空格或逗号分隔如\n'H49E005001 H49E005002'\n或\n'H49E005001,H49E005002'")
                .setOkOnClickListener(R.string.alert_ok, (dialog1, view) -> {
                    String text = dialog1.getEditText().getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        ToastUtil.showShort("不能为空");
                        return;
                    }
                    dialog1.dismiss();
                    searchMapNumber(text);
                })
                .visibleEditText()
                .setCancelOnClickListener(R.string.alert_cancel, null)
                .alert();
    }

    private void searchMapNumber(String text) {
        if (text.contains(",")) {
            String[] split = text.split(",");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                sb.append(String.format("'%s',", split[i]));
            }
            text = sb.substring(0, sb.lastIndexOf(","));
        }
        if (text.contains(" ")) {
            String[] split = text.split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                sb.append(String.format("'%s',", split[i]));
            }
            text = sb.substring(0, sb.lastIndexOf(","));
        }
        for (int i = 0; i < 7; i++) {
            String url = String.format("http://ddk.digitalcq.com:6080/arcgis/rest/services/CQGRID_2000/Mapserver/%s/query", i);
            String where = String.format("1=1 and 新图号 in ('%s')", text);
            showLoading();
            mMapView.querySQL(getActivity(), url, where,
                    new BaseMapView.MainThreadCallback<FeatureResult>() {
                        @Override
                        public void onCallback(FeatureResult result) {
                            dismissLoading();
                            if (result.featureCount() == 0) return;
                            Symbol symbol;
                            if (mMapType == 1) {
                                symbol = new PictureMarkerSymbol(getActivity(),
                                        ContextCompat.getDrawable(getActivity(), R.drawable.ic_location_green));
                            } else {
                                symbol = new SimpleLineSymbol(Color.RED, 2,
                                        SimpleLineSymbol.STYLE.SOLID);
                            }

                            for (Object o : result) {
                                if (o instanceof Feature) {
                                    Feature feature = (Feature) o;
                                    Graphic graphic = new Graphic(feature.getGeometry(), symbol);
                                    mMapView.setCurrentDrawGraphic(graphic);
                                    mMapView.centerAtGraphic(graphic);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            dismissLoading();
                        }
                    });
        }
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
                                if (position == lastPosition) return;
                                if (response.get(position).isChecked()) {
                                    response.get(position).setChecked(false);
                                } else {
                                    response.get(position).setChecked(true);
                                    if (lastPosition != -1) {
                                        response.get(lastPosition).setChecked(false);
                                    }
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
                        //确定，但是不查询
                        btn_ok.setOnClickListener(v -> {
                            if (lastPosition == -1) {
                                ToastUtil.showShort("请选择一个区域");
                                return;
                            }
                            dialog.dismiss();
                            searchAdminRegion(response.get(lastPosition).getAreaName());
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                    }
                });
    }

    //查询行政区域
    private void searchAdminRegion(String searchText) {
        showLoading();
        String url = getString(R.string.query_admin_region);
        String where = String.format("1=1 and MC='%s'", searchText);
        mMapView.querySQL(getActivity(), url, where,
                new BaseMapView.MainThreadCallback<FeatureResult>() {
                    @Override
                    public void onCallback(FeatureResult result) {
                        dismissLoading();
                        if (result.featureCount() == 0) {
                            ToastUtil.showShort(" 未获取到相关信息");
                            return;
                        }
                        for (Object o : result) {
                            if (o instanceof Feature) {
                                Feature feature = (Feature) o;
                                SimpleLineSymbol symbol =
                                        new SimpleLineSymbol(Color.RED, 2,
                                                SimpleLineSymbol.STYLE.SOLID);
                                Graphic graphic = new Graphic(feature.getGeometry(), symbol);
                                mMapView.setCurrentDrawGraphic(graphic);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        ToastUtil.showShort(" 未获取到相关信息,请重试");
                        restoreAll();
                    }
                });
    }

    //设置缓冲区的图形
    private void setBufferGeometry(float distance) {
        Geometry geometry = mMapView.getDrawTool().drawGraphic.getGeometry();
        Polygon buffer = GeometryEngine.buffer(geometry, mMapView.getSpatialReference(),
                distance / 1000, null);

        SimpleFillSymbol symbol = new SimpleFillSymbol(Color.RED);

        Graphic graphic = new Graphic(buffer, symbol);
        mMapView.setCurrentDrawGraphic(graphic);
    }

//    private void queryGeometry(List<FruitListModel> fruitList) {
//        showLoading();
//        String url = getString(R.string.polygon_feature_server_url);
//        mMapView.queryGeometry(getActivity(), url,
//                new BaseMapView.MainThreadCallback<FeatureResult>() {
//                    @Override
//                    public void onCallback(FeatureResult objects) {
//                        dismissLoading();
//                        restoreSpaceStatus();
//                        if (objects.featureCount() == 0) {
//                            ToastUtil.showShort("未查询到相关信息");
//                            return;
//                        }
//                        List<FruitListModel> selectHandoutList = new ArrayList<>();
//                        for (Object object : objects) {
//                            if (object instanceof Feature) {
//                                Feature feature = (Feature) object;
//                                double FRUIT_ID = (double) feature.getAttributeValue(FRUITID);
//                                for (FruitListModel model : fruitList) {
//                                    if (FRUIT_ID != model.fruitId) continue;
//                                    selectHandoutList.add(model);
//                                }
//                            }
//                        }
//                        searchMapService(selectHandoutList);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        ToastUtil.showShort("暂无数据");
//                        dismissLoading();
//                        restoreSpaceStatus();
//                    }
//                });
//    }

    private void queryGeometryAndSql(int mapType, String whereClause, String url) {
        showLoading();
        mMapView.queryGeometryAndSql(getActivity(), url, whereClause,
                new BaseMapView.MainThreadCallback<FeatureResult>() {
                    @Override
                    public void onCallback(FeatureResult result) {
                        dismissLoading();
                        restoreSpaceStatus();
                        if (result.featureCount() == 0) {
                            ToastUtil.showShort("未查询到相关信息");
                            return;
                        }

                        filterDataWithSymbol(result, mapType);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtil.showShort("暂无数据");
                        dismissLoading();
                        restoreSpaceStatus();
                    }
                });
    }

    private void filterDataWithSymbol(FeatureResult result, int mapType) {
        List<FruitListModel> filterList = new ArrayList<>();
        for (FruitListModel model : mFruitList) {
            for (Object o : result) {
                if (o instanceof Feature) {
                    Feature feature = (Feature) o;
                    double fruitIdValue;
                    if (mapType == 1) {
                        fruitIdValue = (double) feature.getAttributeValue(FRUITID);
                    } else {
                        fruitIdValue = ((Double) feature.getAttributeValue(FRUITID)).longValue();
                    }
                    if (fruitIdValue != model.fruitId) continue;
                    filterList.add(model);
                }
            }
        }
        showMapSymbol(result, mapType, filterList);
        showBottomLayout(filterList);
    }

    private void restoreSpaceStatus() {
        mMapView.setCurrentDrawSpace(BaseMapView.SPACE_NONE);
        mCVSpace.setBackgroundResource(R.drawable.ic_space);
        mMapView.clearAll();
        hideClearBtn();
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

    //获取分类信息，成果类型那一坨
    private void getClassifyTypeData() {
        showLoading();
        APIService.getInstance()
                .getfruitCategoryList(new SimpleSubscriber<List<FruitCategoryListModel>>() {
                    @Override
                    public void onResponse(List<FruitCategoryListModel> response) {
                        dismissLoading();
                        if (response == null || response.isEmpty()) {
                            return;
                        }
                        mClassifyTypeList.clear();
                        mClassifyTypeList.addAll(response);
                        mMapType = response.get(0).mapType;
                        getClassifyTypeBelow(response.get(0).fruitCategoryId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
                    }
                });
    }

    private void getClassifyTypeBelow(long fruitCategoryId) {
        showLoading();
        APIService.getInstance()
                .getDirectoryHandoutConditionByFCList(fruitCategoryId,
                        new SimpleSubscriber<List<GethandoutConditionByFCModel>>() {
                            @Override
                            public void onResponse(List<GethandoutConditionByFCModel> response) {
                                dismissLoading();
                                if (response == null || response.isEmpty()) {
                                    return;
                                }
                                mFirstClassifyTypeBelowList.clear();
                                mFirstClassifyTypeBelowList.addAll(response);
                            }
                        });
    }
}
