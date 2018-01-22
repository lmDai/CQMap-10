package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatImageView;
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
import android.zhixun.uiho.com.gissystem.ui.widget.SpaceDialog;
import android.zhixun.uiho.com.gissystem.util.DensityUtils;
import android.zhixun.uiho.com.gissystem.util.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.util.ScreenUtil;

import com.alibaba.fastjson.JSON;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
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
    private LinearLayout ll_content;
    //
    //分类信息集合-成果类型那一坨
    private List<FruitCategoryListModel> mClassifyList = new ArrayList<>();
    //分类信息集合-成果类型下面的集合
    private List<GethandoutConditionByFCModel> mClassifyTypeList = new ArrayList<>();
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
        dragView = view.findViewById(R.id.bottom_drag_view);
        ll_content = view.findViewById(R.id.ll_content);
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
            case R.id.aiv_zoom_in://
                mMapView.zoomin();
                break;
            case R.id.aiv_zoom_out://
                mMapView.zoomout();
                break;
            case R.id.cv_space://空间查询
                onSpaceClick(v);
                break;
            case R.id.cv_clear:
                setSearhText("");
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
                restoreAll();
                showSearchTextView();
                break;
        }
    }

    private void searchBtnClick() {
        String searchStr = mEtSearch.getText().toString();
        if (TextUtils.isEmpty(searchStr)) {
            setSearhText("全部");
        }
        getFruitList(mBody);
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

    private void showSearchClearView() {
        mIvSearchClear.setVisibility(View.VISIBLE);
        mTvSearch.setVisibility(View.GONE);
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
    private void getFruitList(ReportHandoutListBody body) {
        if (body == null) {
            body = new ReportHandoutListBody();
        }
        showSearchClearView();
        showLoading();
        APIService.getInstance().getFruitList(body,
                new SimpleSubscriber<List<FruitListModel>>() {
                    @Override
                    public void onResponse(List<FruitListModel> response) {
                        dismissLoading();
                        if (response == null || response.isEmpty()) return;
                        mFruitList.clear();
                        mFruitList.addAll(response);
                        if (mMapView.getCurrentDrawSpace() != BaseMapView.SPACE_NONE) {
                            queryGeometry(mFruitList);
                        } else {
                            searchMapService(mFruitList);
                        }
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
        showBottomLayout(fruitList);
        StringBuilder sb = new StringBuilder();
//        sb.append("FRUITID");
//        sb.append(" in ");
//        sb.append("(");
        int mapType = 2;
        for (FruitListModel model : fruitList) {

            String fruitId = String.valueOf(model.fruitId);
            sb.append(fruitId);
            sb.append(",");
        }

//        String whereClause = sb.substring(0, sb.lastIndexOf(",")) + ")";
        String whereClause = "FRUITID in (74304,74305,74306,74307,74308,74309,74310,74311,74312,74313,74314,74315,74316,74317,74318,74319,74320,74321,74322,74323,74324,74325,74326,74327,74328,74329,74330,74331,74332,74333,74334,74335,74336,74337,74338,74339,74340,74341,74342,74343,74344,74345,74346,74347,74348,74349,74350,74351,74352,74353,74354,74355,74356,74357,74358,74359,74360,74361,74362,74363,74364,74365,74366,74367,74368,74369,74370,74371,74372,74373,74374,74375,74376,74377,74378,74379,74380,74381,74382,74383,74384,74385,74386,74387,74388,74389,74390,74391,74392,74393,74394,74395,74396,74397,74398,74399,74400,74401,74402,74403,74404,74405,74406,74407,74408,74409,74410,74411,74412,74413,74414,74415,74416,74417,74418,74419,74420,74421,74422,74423,74424,74425,74426,74427,74428,74429,74430,74431,74432,74433,74434,74435,74436,74437,74438,74439,74440,74441,74442,74443,74444,74445,74446,74447,74448,74449,74450,74451,74452,74453,74454,74455,74456,74457,74458,74459,74460,74461,74462,74463,74464,74465,74466,74467,74468,74469,74470,74471,74472,74473,74474,74475,74476,74477,74478,74479,74480,74481,74482,74483,74484,74485,74486,74487,74488,74489,74490,74491,74492,74493,74494,74495,74496,74497,74498,74499,74500,74501,74502,74503,74504,74505,74506,74507,74508,74509,74510,74511,74512,74513,74514,74515,74516,74517,74518,74519,74520,74521,74522,74523,74524,74525,74526,74527,74528,74529,74530,74531,74532,74533,74534,74535,74536,74537,74538,74539,74540,74541,74542,74543,74544,74545,74546,74547,74548,74549,74550,74551,74552,74553,74554,74555,74556,74557,74558,74559,74560,74561,74562,74563,74564,74565,74566,74567,74568,74569,74570,74571,74572,74573,74574,74575,74576,74577,74578,74579,74580,74581,74582,74583,74584,74585,74586,74587,74588,74589,74590,74591,74592,74593,74594,74595,74596,74597,74598,74599,74600,74601,74602,74603,74604,74605,74606,74607,74608,74609,74610,74611,74612,74613,74614,74615,74616,74617,74618,74619,74620,74621,74622,74623,74624,74625,74626,74627,74628,74629,74630,74631,74632,74633,74634,74635,74636,74637,74638,74639,74640,74641,74642,74643,74644,74645,74646,74647,74648,74649,74650,74651,74652,74653,74654,74655,74656,74657,74658,74659,74660,74661,74662,74663,74664,74665,74666,74667,74668,74669,74670,74671,74672,74673,74674,74675,74676,74677,74678,74679,74680,74681,74682,74683,74684,74685,74686,74687,74688,74689,74690,74691,74692,74693,74694,74695,74696,74697,74698,74699,74700,74701,74702,74703,74704,74705,74706,74707,74708,74709,74710,74711,74712,74713,74714,74715,74716,74717,74718,74719,74720,74721,74722,74723,74724,74725,74726,74727,74728,74729,74730,74731,74732,74733,74734,74735,74736,74737,74738,74739,74740,74741,74742,74743,74744,74745,74746,74747,74748,74749,74750,74751,74752,74753,74754,74755,74756,74757,74758,74759,74760,74761,74762,74763,74764,74765,74766,74767,74768,74769,74770,74771,74772,74773,74774,74775,74776,74777,74778,74779,74780,74781,74782,74783,74784,74785,74786,74787,74788,74789,74790,74791,74792,74793,74794,74795,74796,74797,74798,74799,74800,74801,74802,74803,74804,74805,74806,74807,74808,74809,74810,74811,74812,74813,74814,74815,74816,74817,74818,74819,74820,74821,74822,74823,74824,74825,74826,74827,74828,74829,74830,74831,74832,74833,74834,74835,74836,74837,74838,74839,74840,74841,74842,74843,74844,74845,74846,74847,74848,74849,74850,74851,74852,74853,74854,74855,74856,74857,74858,74859,74860,74861,74862,74863,74864,74865,74866,74867,74868,74869,74870,74871,74872,74873,74874,74875,74876,74877,74878,74879,74880,74881,74882,74883,74884,74885,74886,74887,74888,74889,74890,74891,74892,74893,74894,74895,74896,74897,74898,74899,74900,74901,74902,74903,74904,74905,74906,74907,74908,74909,74910,74911,74912,74913,74914,74915,74916,74917,74918,74919,74920,74921,74922,74923,74924,74925,74926,74927,74928,74929,74930,74931,74932,74933,74934,74935,74936,74937,74938,74939,74940,74941,74942,74943,74944,74945,74946,74947,74948,74949,74950,74951,74952,74953,74954,74955,74956,74957,74958,74959,74960,74961,74962,74963,74964,74965,74966,74967,74968,74969,74970,74971,74972,74973,74974,74975,74976,74977,74978,74979,74980,74981,74982,74983,74984,74985,74986,74987,74988,74989,74990,74991,74992,74993,74994,74995,74996,74997,74998,74999,75000,75001,75002,75003,75004,75005,75006,75007,75008,75009,75010,75011,75012,75013,75014,75015,75016,75017,75018,75019,75020,75021,75022,75023,75024,75025,75026,75027,75028,75029,75030,75031,75032,75033,75034,75035,75036,75037,75038,75039,75040,75041,75042,75043,75044,75045,75046,75047,75048,75049,75050,75051,75052,75053,75054,75055,75056,75057,75058,75059,75060,75061,75062,75063,75064,75065,75066,75067,75068,75069,75070,75071,75072,75073,75074,75075,75076,75077,75078,75079,75080,75081,75082,75083,75084,75085,75086,75087,75088,75089,75090,75091,75092,75093,75094,75095,75096,75097,75098,75099,75100,75101,75102,75103,75104,75105,75106,75107,75108,75109,75110,75111,75112,75113,75114,75115,75116,75117,75118,75119,75120,75121,75122,75123,75124,75125,75126,75127,75128,75129,75130,75131,75132,75133,75134,75135,75136,75137,75138,75139,75140,75141,75142,75143,75144,75145,75146,75147,75148,75149,75150,75151,75152,75153,75154,75155,75156,75157,75158,75159,75160,75161,75162,75163,75164,75165,75166,75167,75168,75169,75170,75171,75172,75173,75174,75175,75176,75177,75178,75179,75180,75181,75182,75183,75184,75185,75186,75187,75188,75189,75190,75191,75192,75193,75194,75195,75196,75197,75198,75199,75200,75201,75202,75203,75204,75205,75206,75207,75208,75209,75210,75211,75212,75213,75214,75215,75216,75217,75218,75219,75220,75221,75222,75223,75224,75225,75226,75227,75228,75229,75230,75231,75232,75233,75234,75235,75236,75237,75238,75239,75240,75241,75242,75243,75244,75245,75246,75247,75248,75249,75250,75251,75252,75253,75254,75255,75256,75257,75258,75259,75260,75261,75262,75263,75264,75265,75266,75267,75268,75269,75270,75271,75272,75273,75274,75275,75276,75277,75278,75279,75280,75281,75282,75283,75284,75285,75286,75287,75288,75289,75290,75291,75292,75293,75294,75295,75296,75297,75298,75299,75300,75301,75302) OR FRUITID in (75303,75304,75305,75306,75307,75308,75309,75310,75311,75312,75313,75314,75315,75316,75317,75318,75319,75320,75321,75322,75323,75324,75325,75326,75327,75328,75329,75330,75331,75332,75333,75334,75335,75336,75337,75338,75339,75340,75341,75342,75343,75344,75345,75346,75347,75348,75349,75350,75351,75352,75353,75354,75355,75356,75357,75358,75359,75360,75361,75362,75363,75364,75365,75366,75367,75368,75369,75370,75371,75372,75373,75374,75375,75376,75377,75378,75379,75380,75381,75382,75383,75384,75385,75386,75387,75388,75389,75390,75391,75392,75393,75394,75395,75396,75397,75398,75399,75400,75401,75402,75403,75404,75405,75406,75407,75408,75409,75410,75411,75412,75413,75414,75415,75416,75417,75418,75419,75420,75421,75422,75423,75424,75425,75426,75427,75428,75429,75430,75431,75432,75433,75434,75435,75436,75437,75438,75439,75440,75441,75442,75443,75444,75445,75446,75447,75448,75449,75450,75451,75452,75453,75454,75455,75456,75457,75458,75459,75460,75461,75462,75463,75464,75465,75466,75467,75468,75469,75470,75471,75472,75473,75474,75475,75476,75477,75478,75479,75480,75481,75482,75483,75484,75485,75486,75487,75488,75489,75490,75491,75492,75493,75494,75495,75496,75497,75498,75499,75500,75501,75502,75503,75504,75505,75506,75507,75508,75509,75510,75511,75512,75513,75514,75515,75516,75517,75518,75519,75520,75521,75522,75523,75524,75525,75526,75527,75528,75529,75530,75531,75532,75533,75534,75535,75536,75537,75538,75539,75540,75541,75542,75543,75544,75545,75546,75547,75548,75549,75550,75551,75552,75553,75554,75555,75556,75557,75558,75559,75560,75561,75562,75563,75564,75565,75566,75567,75568,75569,75570,75571,75572,75573,75574,75575,75576,75577,75578,75579,75580,75581,75582,75583,75584,75585,75586,75587,75588,75589,75590,75591,75592,75593,75594,75595,75596,75597,75598,75599,75600,75601,75602,75603,75604,75605,75606,75607,75608,75609,75610,75611,75612,75613,75614,75615,75616,75617,75618,75619,75620,75621,75622,75623,75624,75625,75626,75627,75628,75629,75630,75631,75632,75633,75634,75635,75636,75637,75638,75639,75640,75641,75642,75643,75644,75645,75646,75647,75648,75649,75650,75651,75652,75653,75654,75655,75656,75657,75658,75659,75660,75661,75662,75663,75664,75665,75666,75667,75668,75669,75670,75671,75672,75673,75674,75675,75676,75677,75678,75679,75680,75681,75682,75683,75684,75685,75686,75687,75688,75689,75690,75691,75692,75693,75694,75695,75696,75697,75698,75699,75700,75701,75702,75703,75704,75705,75706,75707,75708,75709,75710,75711,75712,75713,75714,75715,75716,75717,75718,75719,75720,75721,75722,75723,75724,75725,75726,75727,75728,75729,75730,75731,75732,75733,75734,75735,75736,75737,75738,75739,75740,75741,75742,75743,75744,75745,75746,75747,75748,75749,75750,75751,75752,75753,75754,75755,75756,75757,75758,75759,75760,75761,75762,75763,75764,75765,75766,75767,75768,75769,75770,75771,75772,75773,75774,75775,75776,75777,75778,75779,75780,75781,75782,75783,75784,75785,75786,75787,75788,75789,75790,75791,75792,75793,75794,75795,75796,75797,75798,75799,75800,75801,75802,75803,75804,75805,75806,75807,75808,75809,75810,75811,75812,75813,75814,75815,75816,75817,75818,75819,75820,75821,75822,75823,75824,75825,75826,75827,75828,75829,75830,75831,75832,75833,75834,75835,75836,75837,75838,75839,75840,75841,75842,75843,75844,75845,75846,75847,75848,75849,75850,75851,75852,75853,75854,75855,75856,75857,75858,75859,75860,75861,75862,75863,75864,75865,75866,75867,75868,75869,75870,75871,75872,75873,75874,75875,75876,75877,75878,75879,75880,75881,75882,75883,75884,75885,75886,75887,75888,75889,75890,75891,75892,75893,75894,75895,75896,75897,75898,75899,75900,75901,75902,75903,75904,75905,75906,75907,75908,75909,75910,75911,75912,75913,75914,75915,75916,75917,75918,75919,75920,75921,75922,75923,75924,75925,75926,75927,75928,75929,75930,75931,75932,75933,75934,75935,75936,75937,75938,75939,75940,75941,75942,75943,75944,75945,75946,75947,75948,75949,75950,75951,75952,75953,75954,75955,75956,75957,75958,75959,75960,75961,75962,75963,75964,75965,75966,75967,75968,75969,75970,75971,75972,75973,75974,75975,75976,75977,75978,75979,75980,75981,75982,75983,75984,75985,75986,75987,75988,75989,75990,75991,75992,75993,75994,75995,75996,75997,75998,75999,76000,76001,76002,76003,76004,76005,76006,76007,76008,76009,76010,76011,76012,76013,76014,76015,76016,76017,76018,76019,76020,76021,76022,76023,76024,76025,76026,76027,76028,76029,76030,76031,76032,76033,76034,76035,76036,76037,76038,76039,76040,76041,76042,76043,76044,76045,76046,76047,76048,76049,76050,76051,76052,76053,76054,76055,76056,76057,76058,76059,76060,76061,76062,76063,76064,76065,76066,76067,76068,76069,76070,76071,76072,76073,76074,76075,76076,76077,76078,76079,76080,76081,76082,76083,76084,76085,76086,76087,76088,76089,76090,76091,76092,76093,76094,76095,76096,76097,76098,76099,76100,76101,76102,76103,76104,76105,76106,76107,76108,76109,76110,76111,76112,76113,76114,76115,76116,76117,76118,76119,76120,76121,76122,76123,76124,76125,76126,76127,76128,76129,76130,76131,76132,76133,76134,76135,76136,76137,76138,76139,76140,76141,76142,76143,76144,76145,76146,76147,76148,76149,76150,76151,76152,76153,76154,76155,76156,76157,76158,76159,76160,76161,76162,76163,76164,76165,76166,76167,76168,76169,76170,76171,76172,76173,76174,76175,76176,76177,76178,76179,76180,76181,76182,76183,76184,76185,76186,76187,76188,76189,76190,76191,76192,76193,76194,76195,76196,76197,76198,76199,76200,76201,76202,76203,76204,76205,76206,76207,76208,76209,76210,76211,76212,76213,76214,76215,76216,76217,76218,76219,76220,76221,76222,76223,76224,76225,76226,76227,76228,76229,76230,76231,76232,76233,76234,76235,76236,76237,76238,76239,76240,76241,76242,76243,76244,76245,76246,76247,76248,76249,76250,76251,76252,76253,76254,76255,76256,76257,76258,76259,76260,76261,76262,76263,76264,76265,76266,76267,76268,76269,76270,76271,76272,76273,76274,76275,76276,76277,76278,76279,76280,76281,76282,76283,76284,76285,76286,76287,76288,76289,76290,76291,76292,76293,76294,76295,76296,76297,76298,76299,76300,76301) OR FRUITID in (76302,76303,76304,76305,76306,76307,76308,76309,76310,76311,76312,76313,76314,76315,76316,76317,76318,76319,76320,76321,76322,76323,76324,76325,76326,76327,76328,76329,76330,76331,76332,76333,76334,76335,76336,76337,76338,76339,76340,76341,76342,76343,76344,76345,76346,76347,76348,76349,76350,76351,76352,76353,76354,76355,76356,76357,76358,76359,76360,76361,76362,76363,76364,76365,76366,76367,76368,76369,76370,76371,76372,76373,76374,76375,76376,76377,76378,76379,76380,76381,76382,76383,76384,76385,76386,76387,76388,76389,76390,76391,76392,76393,76394,76395,76396,76397,76398,76399,76400,76401,76402,76403,76404,76405,76406,76407,76408,76409,76410,76411,76412,76413,76414,76415,76416,76417,76418,76419,76420,76421,76422,76423,76424,76425,76426,76427,76428,76429,76430,76431,76432,76433,76434,76435,76436,76437,76438,76439,76440,76441,76442,76443,76444,76445,76446,76447,76448,76449,76450,76451,76452,76453,76454,76455,76456,76457,76458,76459,76460,76461,76462,76463,76464,76465,76466,76467,76468,76469,76470,76471,76472,76473,76474,76475,76476,76477,76478,76479,76480,76481,76482,76483,76484,76485,76486,76487,76488,76489,76490,76491,76492,76493,76494,76495,76496,76497,76498,76499,76500,76501,76502,76503,76504,76505,76506,76507,76508,76509,76510,76511,76512,76513,76514,76515,76516,76517,76518,76519,76520,76521,76522,76523,76524,76525,76526,76527,76528,76529,76530,76531,76532,76533,76534,76535,76536,76537,76538,76539,76540,76541,76542,76543,76544,76545,76546,76547,76548,76549,76550,76551,76552,76553,76554,76555,76556,76557,76558,76559,76560,76561,76562,76563,76564,76565,76566,76567,76568,76569,76570,76571,76572,76573,76574,76575,76576,76577,76578,76579,76580,76581,76582,76583,76584,76585,76586,76587,76588,76589,76590,76591,76592,76593,76594,76595,76596,76597,76598,76599,76600,76601,76602,76603,76604,76605,76606,76607,76608,76609,76610,76611,76612,76613,76614,76615,76616,76617,76618,76619,76620,76621,76622,76623,76624,76625,76626,76627,76628,76629,76630,76631,76632,76633,76634,76635,76636,76637,76638,76639,76640,76641,76642,76643,76644,76645,76646,76647,76648,76649,76650,76651,76652,76653,76654,76655,76656,76657,76658,76659,76660,76661,76662,76663,76664,76665,76666,76667,76668,76669,76670,76671,76672,76673,76674,76675,76676,76677,76678,76679,76680,76681,76682,76683,76684,76685,76686,76687,76688,76689,76690,76691,76692,76693,76694,76695,76696,76697,76698,76699,76700,76701,76702,76703,76704,76705,76706,76707,76708,76709,76710,76711,76712,76713,76714,76715,76716,76717,76718,76719,76720,76721,76722,76723,76724,76725,76726,76727,76728,76729,76730,76731,76732,76733,76734,76735,76736,76737,76738,76739,76740,76741,76742,76743,76744,76745,76746,76747,76748,76749,76750,76751,76752,76753,76754,76755,76756,76757,76758,76759,76760,76761,76762,76763,76764,76765,76766,76767,76768,76769,76770,76771,76772,76773,76774,76775,76776,76777,76778,76779,76780,76781,76782,76783,76784,76785,76786,76787,76788,76789,76790,76791,76792,76793,76794,76795,76796,76797,76798,76799,76800,76801,76802,76803,76804,76805,76806,76807,76808,76809,76810,76811,76812,76813,76814,76815,76816,76817,76818,76819,76820,76821,76822,76823,76824,76825,76826,76827,76828,76829,76830,76831,76832,76833,76834,76835,76836,76837,76838,76839,76840,76841,76842,76843,76844,76845,76846,76847,76848,76849,76850,76851,76852,76853,76854,76855,76856,76857,76858,76859,76860,76861,76862,76863,76864,76865,76866,76867,76868,76869,76870,76871,76872,76873,76874,76875,76876,76877,76878,76879,76880,76881,76882,76883,76884,76885,76886,76887,76888,76889,76890,76891,76892,76893,76894,76895,76896,76897,76898,76899,76900,76901,76902,76903,76904,76905,76906,76907,76908,76909,76910,76911,76912,76913,76914,76915,76916,76917,76918,76919,76920,76921,76922,76923,76924,76925,76926,76927,76928,76929,76930,76931,76932,76933,76934,76935,76936,76937,76938,76939,76940,76941,76942,76943,76944,76945,76946,76947,76948,76949,76950,76951,76952,76953,76954,76955,76956,76957,76958,76959,76960,76961,76962,76963,76964,76965,76966,76967,76968,76969,76970,76971,76972,76973,76974,76975,76976,76977,76978,76979,76980,76981,76982,76983,76984,76985,76986,76987,76988,76989,76990,76991,76992,76993,76994,76995,76996,76997,76998,76999,77000,77001,77002,77003,77004,77005,77006,77007,77008,77009,77010,77011,77012,77013,77014,77015,77016,77017,77018,77019,77020,77021,77022,77023,77024,77025,77026,77027,77028,77029,77030,77031,77032,77033,77034,77035,77036,77037,77038,77039,77040,77041,77042,77043,77044,77045,77046,77047,77048,77049,77050,77051,77052,77053,77054,77055,77056,77057,77058,77059,77060,77061,77062,77063,77064,77065,77066,77067,77068,77069,77070,77071,77072,77073,77074,77075,77076,77077,77078,77079,77080,77081,77082,77083,77084,77085,77086,77087,77088,77089,77090,77091,77092,77093,77094,77095,77096,77097,77098,77099,77100,77101,77102,77103,77104,77105,77106,77107,77108,77109,77110,77111,77112,77113,77114,77115,77116,77117,77118,77119,77120,77121,77122,77123,77124,77125,77126,77127,77128,77129,77130,77131,77132,77133,77134,77135,77136,77137,77138,77139,77140,77141,77142,77143,77144,77145,77146,77147,77148,77149,77150,77151,77152,77153,77154,77155,77156,77157,77158,77159,77160,77161,77162,77163,77164,77165,77166,77167,77168,77169,77170,77171,77172,77173,77174,77175,77176,77177,77178,77179,77180,77181,77182,77183,77184,77185,77186,77187,77188,77189,77190,77191,77192,77193,77194,77195,77196,77197,77198,77199,77200,77201,77202,77203,77204,77205,77206,77207,77208,77209,77210,77211,77212,77213,77214,77215,77216,77217,77218,77219,77220,77221,77222,77223,77224,77225,77226,77227,77228,77229,77230,77231,77232,77233,77234,77235,77236,77237,77238,77239,77240,77241,77242,77243,77244,77245,77246,77247,77248,77249,77250,77251,77252,77253,77254,77255,77256,77257,77258,77259,77260,77261,77262,77263,77264,77265,77266,77267,77268,77269,77270,77271,77272,77273,77274,77275,77276,77277,77278,77279,77280,77281,77282,77283,77284,77285,77286,77287,77288,77289,77290,77291,77292,77293,77294,77295,77296,77297,77298,77299,77300) OR FRUITID in (77301,77302,77303,77304,77305,77306,77307,77308,77309,77310,77311,77312,77313,77314,77315,77316,77317,77318,77319,77320,77321,77322,77323,77324,77325,77326,77327,77328,77329,77330,77331,77332,77333,77334,77335,77336,77337,77338,77339,77340,77341,77342,77343,77344,77345,77346,77347,77348,77349,77350,77351,77352,77353,77354,77355,77356,77357,77358,77359,77360,77361,77362,77363,77364,77365,77366,77367,77368,77369,77370,77371,77372,77373,77374,77375,77376,77377,77378,77379,77380,77381,77382,77383,77384,77385,77386,77387,77388,77389,77390,77391,77392,77393,77394,77395,77396,77397,77398,77399,77400,77401,77402,77403,77404,77405,77406,77407,77408,77409,77410,77411,77412,77413,77414,77415,77416,77417,77418,77419,77420,77421,77422,77423,77424,77425,77426,77427,77428,77429,77430,77431,77432,77433,77434,77435,77436,77437,77438,77439,77440,77441,77442,77443,77444,77445,77446,77447,77448,77449,77450,77451,77452,77453,77454,77455,77456,77457,77458,77459,77460,77461,77462,77463,77464,77465,77466,77467,77468,77469,77470,77471,77472,77473,77474,77475,77476,77477,77478,77479,77480,77481,77482,77483,77484,77485,77486,77487,77488,77489,77490,77491,77492,77493,77494,77495,77496,77497,77498,77499,77500,77501,77502,77503,77504,77505,77506,77507,77508,77509,77510,77511,77512,77513,77514,77515,77516,77517,77518,77519,77520,77521,77522,77523,77524,77525,77526,77527,77528,77529,77530,77531,77532,77533,77534,77535,77536,77537,77538,77539,77540,77541,77542,77543,77544,77545,77546,77547,77548,77549,77550,77551,77552,77553,77554,77555,77556,77557,77558,77559,77560,77561,77562,77563,77564,77565,77566,77567,77568,77569,77570,77571,77572,77573,77574,77575,77576,77577,77578,77579,77580,77581,77582,77583,77584,77585,77586,77587,77588,77589,77590,77591,77592,77593,77594,77595,77596,77597,77598,77599,77600,77601,77602,77603,77604,77605,77606,77607,77608,77609,77610,77611,77612,77613,77614,77615,77616,77617,77618,77619,77620,77621,77622,77623,77624,77625,77626,77627,77628,77629,77630,77631,77632,77633,77634,77635,77636,77637,77638,77639,77640,77641,77642,77643,77644,77645,77646,77647,77648,77649,77650,77651,77652,77653,77654,77655,77656,77657,77658,77659,77660,77661,77662,77663,77664,77665,77666,77667,77668,77669,77670,77671,77672,77673,77674,77675,77676,77677,77678,77679,77680,77681,77682,77683,77684,77685,77686,77687,77688,77689,77690,77691,77692,77693,77694,77695,77696,77697,77698,77699,77700,77701,77702,77703,77704,77705,77706,77707,77708,77709,77710,77711,77712,77713,77714,77715,77716,77717,77718,77719,77720,77721,77722,77723,77724,77725,77726,77727,77728,77729,77730,77731,77732,77733,77734,77735,77736,77737,77738,77739,77740,77741,77742,77743,77744,77745,77746,77747,77748,77749,77750,77751,77752,77753,77754,77755,77756,77757,77758,77759,77760,77761,77762,77763,77764,77765,77766,77767,77768,77769,77770,77771,77772,77773,77774,77775,77776,77777,77778,77779,77780,77781,77782,77783,77784,77785,77786,77787,77788,77789,77790,77791,77792,77793,77794,77795,77796,77797,77798,77799,77800,77801,77802,77803,77804,77805,77806,77807,77808,77809,77810,77811,77812,77813,77814,77815,77816,77817,77818,77819,77820,77821,77822,77823,77824,77825,77826,77827,77828,77829,77830,77831,77832,77833,77834,77835,77836,77837,77838,77839,77840,77841,77842,77843,77844,77845,77846,77847,77848,77849,77850,77851,77852,77853,77854,77855,77856,77857,77858,77859,77860,77861,77862,77863,77864,77865,77866,77867,77868,77869,77870,77871,77872,77873,77874,77875,77876,77877,77878,77879,77880,77881,77882,77883,77884,77885,77886,77887,77888,77889,77890,77891,77892,77893,77894,77895,77896,77897,77898,77899,77900,77901,77902,77903,77904,77905,77906,77907,77908,77909,77910,77911,77912,77913,77914,77915,77916,77917,77918,77919,77920,77921,77922,77923,77924,77925,77926,77927,77928,77929,77930,77931,77932,77933,77934,77935,77936,77937,77938,77939,77940,77941,77942,77943,77944,77945,77946,77947,77948,77949,77950,77951,77952,77953,77954,77955,77956,77957,77958,77959,77960,77961,77962,77963,77964,77965,77966,77967,77968,77969,77970,77971,77972,77973,77974,77975,77976,77977,77978,77979,77980,77981,77982,77983,77984,77985,77986,77987,77988,77989,77990,77991,77992,77993,77994,77995,77996,77997,77998,77999,78000,78001,78002,78003,78004,78005,78006,78007,78008,78009,78010,78011,78012,78013,78014,78015,78016,78017,78018,78019,78020,78021,78022,78023,78024,78025,78026,78027,78028,78029,78030,78031,78032,78033,78034,78035,78036,78037,78038,78039,78040,78041,78042,78043,78044,78045,78046,78047,78048,78049,78050,78051,78052,78053,78054,78055,78056,78057,78058,78059,78060,78061,78062,78063,78064,78065,78066,78067,78068,78069,78070,78071,78072,78073,78074,78075,78076,78077,78078,78079,78080,78081,78082,78083,78084,78085,78086,78087,78088,78089,78090,78091,78092,78093,78094,78095,78096,78097,78098,78099,78100,78101,78102,78103,78104,78105,78106,78107,78108,78109,78110,78111,78112,78113,78114,78115,78116,78117,78118,78119,78120,78121,78122,78123,78124,78125,78126,78127,78128,78129,78130,78131,78132,78133,78134,78135,78136,78137,78138,78139,78140,78141,78142,78143,78144,78145,78146,78147,78148,78149,78150,78151,78152,78153,78154,78155,78156,78157,78158,78159,78160,78161,78162,78163,78164,78165,78166,78167,78168,78169,78170,78171,78172,78173,78174,78175,78176,78177,78178,78179,78180,78181,78182,78183,78184,78185,78186,78187,78188,78189,78190,78191,78192,78193,78194,78195,78196,78197,78198,78199,78200,78201,78202,78203,78204,78205,78206,78207,78208,78209,78210,78211,78212,78213,78214,78215,78216,78217,78218,78219,78220,78221,78222,78223,78224,78225,78226,78227,78228,78229,78230,78231,78232,78233,78234,78235,78236,78237,78238,78239,78240,78241,78242,78243,78244,78245,78246,78247,78248,78249,78250,78251,78252,78253,78254,78255,78256,78257,78258,78259,78260,78261,78262,78263,78264,78265,78266,78267,78268,78269,78270,78271,78272,78273,78274,78275,78276,78277,78278,78279,78280,78281,78282,78283,78284,78285,78286,78287,78288,78289,78290,78291,78292,78293,78294,78295,78296,78297,78298,78299) OR FRUITID in (78300,78301,78302,78303,78304,78305,78306,78307,78308,78309,78310,78311,78312,78313,78314,78315,78316,78317,78318,78319,78320,78321,78322,78323,78324,78325,78326,78327,78328,78329,78330,78331,78332,78333,78334,78335,78336,78337,78338,78339,78340,78341,78342,78343,78344,78345,78346,78347,78348,78349,78350,78351,78352,78353,78354,78355,78356,78357,78358,78359,78360,78361,78362,78363,78364,78365,78366,78367,78368,78369,78370,78371,78372,78373,78374,78375,78376,78377,78378,78379,78380,78381,78382,78383,78384,78385,78386,78387,78388,78389,78390,78391,78392,78393,78394,78395,78396,78397,78398,78399,78400,78401,78402,78403,78404,78405,78406,78407,78408,78409,78410,78411,78412,78413,78414,78415,78416,78417,78418,78419,78420,78421,78422,78423,78424,78425,78426,78427,78428,78429,78430,78431,78432,78433,78434,78435,78436,78437,78438,78439,78440,78441,78442,78443,78444,78445,78446,78447,78448,78449,78450,78451,78452,78453,78454,78455,78456,78457,78458,78459,78460,78461,78462,78463,78464,78465,78466,78467,78468,78469,78470,78471,78472,78473,78474,78475,78476,78477,78478,78479,78480,78481,78482,78483,78484,78485,78486,78487,78488,78489,78490,78491,78492,78493,78494,78495,78496,78497,78498,78499,78500,78501,78502,78503,78504,78505,78506,78507,78508,78509,78510,78511,78512,78513,78514,78515,78516,78517,78518,78519,78520,78521,78522,78523,78524,78525,78526,78527,78528,78529,78530,78531,78532,78533,78534,78535,78536,78537,78538,78539,78540,78541,78542,78543,78544,78545,78546,78547,78548,78549,78550,78551,78552,78553,78554,78555,78556,78557,78558,78559,78560,78561,78562,78563,78564,78565,78566,78567,78568,78569,78570,78571,78572,78573,78574,78575,78576,78577,78578,78579,78580,78581,78582,78583,78584,78585,78586,78587,78588,78589,78590,78591,78592,78593,78594,78595,78596,78597,78598,78599,78600,78601,78602,78603,78604,78605,78606,78607,78608,78609,78610,78611,78612,78613,78614,78615,78616,78617,78618,78619,78620,78621,78622,78623,78624,78625,78626,78627,78628,78629,78630,78631,78632,78633,78634,78635,78636,78637,78638,78639,78640,78641,78642,78643,78644,78645,78646,78647,78648,78649,78650,78651,78652,78653,78654,78655,78656,78657,78658,78659,78660,78661,78662,78663,78664,78665,78666,78667,78668,78669,78670,78671,78672,78673,78674,78675,78676,78677,78678,78679,78680,78681,78682,78683,78684,78685,78686,78687,78688,78689,78690,78691,78692,78693,78694,78695,78696,78697,78698,78699,78700,78701,78702,78703,78704,78705,78706,78707,78708,78709,78710,78711,78712,78713,78714,78715,78716,78717,78718,78719,78720,78721,78722,78723,78724,78725,78726,78727,78728,78729,78730,78731,78732,78733,78734,78735,78736,78737,78738,78739,78740,78741,78742,78743,78744,78745,78746,78747,78748,78749,78750,78751,78752,78753,78754,78755,78756,78757,78758,78759,78760,78761,78762,78763,78764,78765,78766,78767,78768,78769,78770,78771,78772,78773,78774,78775,78776,78777,78778,78779,78780,78781,78782,78783,78784,78785,78786,78787,78788,78789,78790,78791,78792,78793,78794,78795,78796,78797,78798,78799,78800,78801,78802,78803,78804,78805,78806,78807,78808,78809,78810,78811,78812,78813,78814,78815,78816,78817,78818,78819,78820,78821,78822,78823,78824,78825,78826,78827,78828,78829,78830,78831,78832,78833,78834,78835,78836,78837,78838,78839,78840,78841,78842,78843,78844,78845,78846,78847,78848,78849,78850,78851,78852,78853,78854,78855,78856,78857,78858,78859,78860,78861,78862,78863,78864,78865,78866,78867,78868,78869,78870,78871,78872,78873,78874,78875,78876,78877,78878,78879,78880,78881,78882,78883,78884,78885,78886,78887,78888,78889,78890,78891,78892,78893,78894,78895,78896,78897,78898,78899,78900,78901,78902,78903,78904,78905,78906,78907,78908,78909,78910,78911,78912,78913,78914,78915,78916,78917,78918,78919,78920,78921,78922,78923,78924,78925,78926,78927,78928,78929,78930,78931,78932,78933,78934,78935,78936,78937,78938,78939,78940,78941,78942,78943,78944,78945,78946,78947,78948,78949,78950,78951,78952,78953,78954,78955,78956,78957,78958,78959,78960,78961,78962,78963,78964,78965,78966,78967,78968,78969,78970,78971,78972,78973,78974,78975,78976,78977,78978,78979,78980,78981,78982,78983,78984,78985,78986,78987,78988,78989,78990,78991,78992,78993,78994,78995,78996,78997,78998,78999,79000,79001,79002,79003,79004,79005,79006,79007,79008,79009,79010,79011,79012,79013,79014,79015,79016,79017,79018,79019,79020,79021,79022,79023,79024,79025,79026,79027,79028,79029,79030,79031,79032,79033,79034,79035,79036,79037,79038,79039,79040,79041,79042,79043,79044,79045,79046,79047,79048,79049,79050,79051,79052,79053,79054,79055,79056,79057,79058,79059,79060,79061,79062,79063,79064,79065,79066,79067,79068,79069,79070,79071,79072,79073,79074,79075,79076,79077,79078,79079,79080,79081,79082,79083,79084,79085,79086,79087,79088,79089,79090,79091,79092,79093,79094,79095,79096,79097,79098,79099,79100,79101,79102,79103,79104,79105,79106,79107,79108,79109,79110,79111,79112,79113,79114,79115,79116,79117,79118,79119,79120,79121,79122,79123,79124,79125,79126,79127,79128,79129,79130,79131,79132,79133,79134,79135,79136,79137,79138,79139,79140,79141,79142,79143,79144,79145,79146,79147,79148,79149,79150,79151,79152,79153,79154,79155,79156,79157,79158,79159,79160,79161,79162,79163,79164,79165,79166,79167,79168,79169,79170,79171,79172,79173,79174,79175,79176,79177,79178,79179,79180,79181,79182,79183,79184,79185,79186,79187,79188,79189,79190,79191,79192,79193,79194,79195,79196,79197,79198,79199,79200,79201,79202,79203,79204,79205,79206,79207,79208,79209,79210,79211,79212,79213,79214,79215,79216,79217,79218,79219,79220,79221,79222,79223,79224,79225,79226,79227,79228,79229,79230,79231,79232,79233,79234,79235,79236,79237,79238,79239,79240,79241,79242,79243,79244,79245,79246,79247,79248,79249,79250,79251,79252,79253,79254,79255,79256,79257,79258,79259,79260,79261,79262,79263,79264,79265,79266,79267,79268,79269,79270,79271,79272,79273,79274,79275,79276,79277,79278,79279,79280,79281,79282,79283,79284,79285,79286,79287,79288,79289,79290,79291,79292,79293,79294,79295,79296,79297,79298) OR FRUITID in (79299,79300,79301,79302,79303,79304,79305,79306,79307,79308,79309,79310,79311,79312,79313,79314,79315,79316,79317,79318,79319,79320,79321,79322,79323,79324,79325,79326,79327,79328,79329,79330,79331,79332,79333,79334,79335,79336,79337,79338,79339,79340,79341,79342,79343,79344,79345,79346,79347,79348,79349,79350,79351,79352,79353,79354,79355,79356,79357,79358,79359,79360,79361,79362,79363,79364,79365,79366,79367,79368,79369,79370,79371,79372,79373,79374,79375,79376,79377,79378,79379,79380,79381,79382,79383,79384,79385,79386,79387,79388,79389,79390,79391,79392,79393,79394,79395,79396,79397,79398,79399,79400,79401,79402,79403,79404,79405,79406,79407,79408,79409,79410,79411,79412,79413,79414,79415,79416,79417,79418,79419,79420,79421,79422,79423,79424,79425,79426,79427,79428,79429,79430,79431,79432,79433,79434,79435,79436,79437,79438,79439,79440,79441,79442,79443,79444,79445,79446,79447,79448,79449,79450,79451,79452,79453,79454,79455,79456,79457,79458,79459,79460,79461,79462,79463,79464,79465,79466,79467,79468,79469,79470,79471,79472,79473,79474,79475,79476,79477,79478,79479,79480,79481,79482,79483,79484,79485,79486,79487,79488,79489,79490,79491,79492,79493,79494,79495,79496,79497,79498,79499,79500,79501,79502,79503,79504,79505,79506,79507,79508,79509,79510,79511,79512,79513,79514,79515,79516,79517,79518,79519,79520,79521,79522,79523,79524,79525,79526,79527,79528,79529,79530,79531,79532,79533,79534,79535,79536,79537,79538,79539,79540,79541,79542,79543,79544,79545,79546,79547,79548,79549,79550,79551,79552,79553,79554,79555,79556,79557,79558,79559,79560,79561,79562,79563,79564,79565,79566,79567,79568,79569,79570,79571,79572,79573,79574,79575,79576,79577,79578,79579,79580,79581,79582,79583,79584,79585,79586,79587,79588,79589,79590,79591,79592,79593,79594,79595,79596,79597,79598,79599,79600,79601,79602,79603,79604,79605,79606,79607,79608,79609,79610,79611,79612,79613,79614,79615,79616,79617,79618,79619,79620,79621,79622,79623,79624,79625,79626,79627,79628,79629,79630,79631,79632,79633,79634,79635,79636,79637,79638,79639,79640,79641,79642,79643,79644,79645,79646,79647,79648,79649,79650,79651,79652,79653,79654,79655,79656,79657,79658,79659,79660,79661,79662,79663,79664,79665,79666,79667,79668,79669,79670,79671,79672,79673,79674,79675,79676,79677,79678,79679,79680,79681,79682,79683,79684,79685,79686,79687,79688,79689,79690,79691,79692,79693,79694,79695,79696,79697,79698,79699,79700,79701,79702,79703,79704,79705,79706,79707,79708,79709,79710,79711,79712,79713,79714,79715,79716,79717,79718,79719,79720,79721,79722,79723,79724,79725,79726,79727,79728,79729,79730,79731,79732,79733,79734,79735,79736,79737,79738,79739,79740,79741,79742,79743,79744,79745,79746,79747,79748,79749,79750,79751,79752,79753,79754,79755,79756,79757,79758,79759,79760,79761,79762,79763,79764,79765,79766,79767,79768,79769,79770,79771,79772,79773,79774,79775,79776,79777,79778,79779,79780,79781,79782,79783,79784,79785,79786,79787,79788,79789,79790,79791,79792,79793,79794,79795,79796,79797,79798,79799,79800,79801,79802,79803,79804,79805,79806,79807,79808,79809,79810,79811,79812,79813,79814,79815,79816,79817,79818,79819,79820,79821,79822,79823,79824,79825,79826,79827,79828,79829,79830,79831,79832,79833,79834,79835,79836,79837,79838,79839,79840,79841,79842,79843,79844,79845,79846,79847,79848,79849,79850,79851,79852,79853,79854,79855,79856,79857,79858,79859,79860,79861,79862,79863,79864,79865,79866,79867,79868,79869,79870,79871,79872,79873,79874,79875,79876,79877,79878,79879,79880,79881,79882,79883,79884,79885,79886,79887,79888,79889,79890,79891,79892,79893,79894,79895,79896,79897,79898,79899,79900,79901,79902,79903,79904,79905,79906,79907,79908,79909,79910,79911,79912,79913,79914,79915,79916,79917,79918,79919,79920,79921,79922,79923,79924,79925,79926,79927,79928,79929,79930,79931,79932,79933,79934,79935,79936,79937,79938,79939,79940,79941,79942,79943,79944,79945,79946,79947,79948,79949,79950,79951,79952,79953,79954,79955,79956,79957,79958,79959,79960,79961,79962,79963,79964,79965,79966,79967,79968,79969,79970,79971,79972,79973,79974,79975,79976,79977,79978,79979,79980,79981,79982,79983,79984,79985,79986,79987,79988,79989,79990,79991,79992,79993,79994,79995,79996,79997,79998,79999,80000,80001,80002,80003,80004,80005,80006,80007,80008,80009,80010,80011,80012,80013,80014,80015,80016,80017,80018,80019,80020,80021,80022,80023,80024,80025,80026,80027,80028,80029,80030,80031,80032,80033,80034,80035,80036,80037,80038,80039,80040,80041,80042,80043,80044,80045,80046,80047,80048,80049,80050,80051,80052,80053,80054,80055,80056,80057,80058,80059,80060,80061,80062,80063,80064,80065,80066,80067,80068,80069,80070,80071,80072,80073,80074,80075,80076,80077,80078,80079,80080,80081,80082,80083,80084,80085,80086,80087,80088,80089,80090,80091,80092,80093,80094,80095,80096,80097,80098,80099,80100,80101,80102,80103,80104,80105,80106,80107,80108,80109,80110,80111,80112,80113,80114,80115,80116,80117,80118,80119,80120,80121,80122,80123,80124,80125,80126,80127,80128,80129,80130,80131,80132,80133,80134,80135,80136,80137,80138,80139,80140,80141,80142,80143,80144,80145,80146,80147,80148,80149,80150,80151,80152,80153,80154,80155,80156,80157,80158,80159,80160,80161,80162,80163,80164,80165,80166,80167,80168,80169,80170,80171,80172,80173,80174,80175,80176,80177,80178,80179,80180,80181,80182,80183,80184,80185,80186,80187,80188,80189,80190,80191,80192,80193,80194,80195,80196,80197,80198,80199,80200,80201,80202,80203,80204,80205,80206,80207,80208,80209,80210,80211,80212,80213,80214,80215,80216,80217,80218,80219,80220,80221,80222,80223,80224,80225,80226,80227,80228,80229,80230,80231,80232,80233,80234,80235,80236,80237,80238,80239,80240,80241,80242,80243,80244,80245,80246,80247,80248,80249,80250,80251,80252,80253,80254,80255,80256,80257,80258,80259,80260,80261,80262,80263,80264,80265,80266,80267,80268,80269,80270,80271,80272,80273,80274,80275,80276,80277,80278,80279,80280,80281,80282,80283,80284,80285,80286,80287,80288,80289,80290,80291,80292,80293,80294,80295,80296,80297) OR FRUITID in (80298,80299,80300,80301,80302,80303,80304,80305,80306,80307,80308,80309,80310,80311,80312,80313,80314,80315,80316,80317,80318,80319,80320,80321,80322,80323,80324,80325,80326,80327,80328,80329,80330,80331,80332,80333,80334,80335,80336,80337,80338,80339,80340,80341,80342,80343,80344,80345,80346,80347,80348,80349,80350,80351,80352,80353,80354,80355,80356,80357,80358,80359,80360,80361,80362,80363,80364,80365,80366,80367,80368,80369,80370,80371,80372,80373,80374,80375,80376,80377,80378,80379,80380,80381,80382,80383,80384,80385,80386,80387,80388,80389)";
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

                        for (Object object : result) {
                            if (object instanceof Feature) {
                                Feature feature = (Feature) object;

                                Symbol symbol = null;
                                if (mapType == 1) {
                                    symbol = new SimpleMarkerSymbol(Color.RED, 20,
                                            SimpleMarkerSymbol.STYLE.CIRCLE);
                                } else {
                                    symbol = createSimpleFillSymbol();
                                }
                                Graphic graphic = new Graphic(feature.getGeometry(),
                                        symbol, feature.getAttributes());
                                mMapView.addDrawLayerGraphic(graphic);
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
                            double FRUIT_ID = (double) graphic.getAttributeValue("FRUITID");
                            List<FruitListModel> fruitList = new ArrayList<>();
                            for (FruitListModel model : mFruitList) {
                                if (FRUIT_ID == model.fruitId) {
                                    fruitList.add(model);
                                }
                            }
                            showBottomLayout(fruitList);
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dismissLoading();
                        ToastUtil.showShort("未获取信息,请重试");
                    }
                });
        dismissLoading();
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

        for (FruitListModel model : fruitList) {
            LinearLayout ll_row = createRowLL();
            List<FruitListModel.FruitAttrList> attrLists = JSON.parseArray(model.fruitAttrList,
                    FruitListModel.FruitAttrList.class);
            for (FruitListModel.FruitAttrList attr : attrLists) {
                TextView textView = createRowText();
                textView.setText(attr.attrValue);
                ll_row.addView(textView);
            }
            ll_content.addView(ll_row);
        }

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
    private void showHandoutInfoDialog(String fruitId) {
        showLoading();
        APIService.getInstance()
                .getHandoutFruit(fruitId, new SimpleSubscriber<HandoutFruitModel>() {
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
                        LinearLayout ll_title = createRowLL();
                        ll_title.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.c55f3f));
                        LinearLayout ll_data = createRowLL();
                        ll_data.setPadding(0, DensityUtils.dp2px(getActivity(), 1), 0, 0);
                        for (HandoutFruitModel.FruitAttrList attrModel : fruitAttrList) {
                            if (!attrModel.isListShow) {
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
                        dialog.setContentView(dialogView);
                        dialog.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissLoading();
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
                            mMapView.setCurrentDrawSpace(BaseMapView.SPACE_MAP_NUMBER);
                            showMapNumberDialog();
                            break;
                        case 4://行政区域查询
                            mMapView.setCurrentDrawSpace(BaseMapView.SPACE_ADMIN_REGION);
                            showAdminRegionDialog();
                            break;
                    }
                    AppCompatImageView ivSpace = mCVSpace.findViewById(R.id.aci_space);
                    ivSpace.setImageResource(R.mipmap.ic_sure_modifi);
                    showClearBtn();
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

    private void showMapNumberDialog() {
        new SimpleAlertDialog(getActivity())
                .title("图幅号查询")
                .message("可输入多个图幅号，图幅号直接用空格或逗号分隔如\n'G49E005001 G49E005002'\n或\n'G49E005001,G49E005002'")
                .setOkOnClickListener(R.string.alert_ok, (dialog1, view) -> {
                    String text = dialog1.getEditText().getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        ToastUtil.showShort("不能为空");
                        return;
                    }
                    dialog1.dismiss();
                    searchMapNumber(text);
                })
                .visiableEditText()
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
            String url = String.format("http://ddk.digitalcq.com:6080/arcgis/rest/" +
                    "services/CQGRID_2000/Mapserver/%s", i);
            String where = String.format("1=1 and 新图号 in (%s)", text);
            showLoading();
            mMapView.querySQL(getActivity(), url, where,
                    new BaseMapView.MainThreadCallback<FeatureResult>() {
                        @Override
                        public void onCallback(FeatureResult result) {
                            dismissLoading();
                            if (result.featureCount() == 0) {
                                return;
                            }
                            SimpleLineSymbol symbol =
                                    new SimpleLineSymbol(Color.RED, 2,
                                            SimpleLineSymbol.STYLE.SOLID);
                            for (Object o : result) {
                                if (o instanceof Feature) {
                                    Feature feature = (Feature) o;
                                    Graphic graphic = new Graphic(feature.getGeometry(), symbol);
                                    mMapView.addDrawLayerGraphic(graphic);
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
                                mMapView.addDrawLayerGraphic(graphic);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }
                });
    }

    //设置缓冲区的图形
    private void setBufferGeometry(int distance) {
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(Color.RED,
                distance,
                SimpleLineSymbol.STYLE.SOLID
        );
        Geometry geometry = mMapView.getCurrentDrawGraphic().getGeometry();
        Graphic graphic = new Graphic(geometry, lineSymbol);
        mMapView.addDrawLayerGraphic(graphic);
    }

    private void queryGeometry(List<FruitListModel> fruitList) {
        showLoading();
        String url = getString(R.string.polygon_feature_server_url);
        mMapView.queryGeometry(getActivity(), url,
                new BaseMapView.MainThreadCallback<FeatureResult>() {
                    @Override
                    public void onCallback(FeatureResult objects) {
                        dismissLoading();
                        restoreSpaceStatus();
                        if (objects.featureCount() == 0) {
                            ToastUtil.showShort("未查询到相关信息");
                            return;
                        }
                        List<FruitListModel> selectHandoutList = new ArrayList<>();
                        for (Object object : objects) {
                            if (object instanceof Feature) {
                                Feature feature = (Feature) object;
                                double FRUIT_ID = (double) feature.getAttributeValue(FRUITID);
                                for (FruitListModel model : fruitList) {
                                    if (FRUIT_ID == model.fruitId) {
                                        selectHandoutList.add(model);
                                    }
                                }
                            }
                        }
                        searchMapService(selectHandoutList);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        dismissLoading();
                        restoreSpaceStatus();
                    }
                });
    }

    private void restoreSpaceStatus() {
        mMapView.setCurrentDrawSpace(BaseMapView.SPACE_NONE);
        AppCompatImageView ivSpace = mCVSpace.findViewById(R.id.aci_space);
        ivSpace.setImageResource(R.mipmap.ic_kongjian);
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


}
