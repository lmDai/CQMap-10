package android.zhixun.uiho.com.gissystem.ui.fragment;

import android.support.annotation.Keep;
import android.support.v4.app.Fragment;

/**
 * Created by parcool on 2016/9/2.
 * 成果分发
 */

@Keep
public class DispatchFragment extends Fragment {

}
//public class DispatchFragment extends MyBaseFragment implements
//        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
//    String[] reqPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
//            .ACCESS_COARSE_LOCATION};
//    private int requestCode = 2;
//    private View mView;
//    private Basemap basemap;
//    private ArcGISMap arcGISMap;
//    private MapView mMapView;
//    private ServiceFeatureTable mServiceFeatureTable;
//    private FeatureLayer mFeaturelayer;
//    private LocationDisplay mLocationDisplay;
//    private GraphicsOverlay graphicsOverlay;
//    private FeatureQueryResult mAllFeatureQueryResult = null;
//    private AppCompatImageView acivClear, acivSearch;
//    private EditText etSearch;
//    private String etString = "";
//    private CardView cvSearch, cvStatistics;
//    private ProgressDialog mProgressDialog;
//    private int currBaseMapType = vector;
//    private static final int topographic = 0, vector = 1, image = 2;
//    private CardView cvCancle, cvClear;
//    private AppCompatImageView calendar1, calendar2;
//    private TextView calendar1Content, calendar2Content;
//    private boolean isFirst;
//    private String CGFFDate, CGFTDate;
//    private EditText etSearchDialog, etCaseCode, etDataCode;
//    private TextView calendar1_content, calendar2_content;
//    private RelativeLayout relativeLayoutLeft, relativeLayoutRight;
//    private SketchGraphicsOverlay mSketchGraphicsOverlay;
//    protected Subscription mSubscriptionAction;
//    private LinearLayout linearLayout1, linearLayout2;
//
//    private CardView cvDetail, cvCloseDetail;
//    private LinearLayout llOne, llTwo;
//    private TextView tvCode, tvDate, tvBase, tvFormat;
//    private TextView tvCall, tvDig, tvRank, tvHCJZ;
//    private RecyclerView rvOne, rvTwo;
//    private List<String> listToy = new ArrayList<>();
//    private ToyAdapter toyAdapterOne, toyAdapterTwo;
//    //
//    private View mConditionSearch, mSpaceSearch, mClassifySearch;
//    private ImageView mIvUser;
//    private ReportHandoutListBody mBody;
//    //
//    private int currDrawType = DRAWTYPE_NONE;
//    private static final int DRAWTYPE_NONE = -1,
//            DRAWTYPE_RECTANGLE = 0,
//            DRAWTYPE_CIRCLE = 1,
//            DRAWTYPE_POLYGON = 2,
//            DRAWTYPE_SEARCHING = 3,
//            DRAWTYPE_FINISH = 4,
//            DRAWTYPE_FLASH = 5,
//            DRAWTYPE_SYMBOL = 6,
//            DRAWTYPE_ADMIN_REGION = 7;
//    int km;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//        mProgressDialog = new ProgressDialog(mContext == null ? getActivity() : mContext);
//        mProgressDialog.setTitle("提示");
//    }
//
//    public DispatchFragment() {
//        Bundle args = new Bundle();
//        args.putString("name", DispatchFragment.class.getSimpleName());
//        setArguments(args);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        if (mView == null) {
//            mView = inflater.inflate(R.layout.fragment_dispatch, container, false);
//            DaoSession mDaoSession = ((MyBaseApplication) ((Activity) mContext).getApplication()).getDaoSession();
//            mDispatchFragmentStore.setDaoSession(mDaoSession);
//            mSubscriptionAction = RxBus.getInstance().toObservable(DispatchFragmentActionTemp.class).subscribe(new Action1<DispatchFragmentActionTemp>() {
//                @Override
//                public void call(DispatchFragmentActionTemp dispatchFragmentActionTemp) {
//                    switch (dispatchFragmentActionTemp.getAction()) {
//                        case DispatchFragmentActionTemp.ACTION_SHOW:
//                            cvDetail.setVisibility(View.VISIBLE);
//                            llOne.setVisibility(View.VISIBLE);
//                            llTwo.setVisibility(View.GONE);
//                            listToy.clear();
//                            TempSortItemModel tempSortItemModel = (TempSortItemModel) dispatchFragmentActionTemp.getT();
//                            int i0 = 0;
//                            for (Map<String, String> stringStringMap : tempSortItemModel.getMapList()) {
//                                for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
//                                    LogUtil.d(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
//                                    i0++;
//                                    if (i0 <= 4) {
//                                        continue;
//                                    }
//                                    listToy.add(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
//                                }
//                            }
//                            //添加标题
//                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//                            int i = 0;
//                            linearLayout1.removeAllViews();
//                            linearLayout2.removeAllViews();
//                            for (Map<String, String> stringStringMap : tempSortItemModel.getMapList()) {
//                                for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
//                                    if (i >= 4) {
//                                        break;
//                                    }
//                                    i++;
//                                    TextView textView = new TextView(linearLayout1.getContext());
//                                    textView.setLayoutParams(layoutParams);
//                                    textView.setGravity(Gravity.CENTER);
//                                    textView.setTextColor(getResources().getColor(R.color.colorWhite));
//                                    textView.setSingleLine();
//                                    textView.setEllipsize(TextUtils.TruncateAt.END);
//                                    textView.setText(stringStringEntry.getKey());
//                                    linearLayout1.addView(textView);
//                                }
//                            }
//                            //添加标题对应的内容
//                            int i2 = 0;
//                            for (Map<String, String> stringStringMap : tempSortItemModel.getMapList()) {
//                                for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
//                                    if (i2 >= 4) {
//                                        break;
//                                    }
//                                    i2++;
//                                    TextView textView = new TextView(linearLayout2.getContext());
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
////                            if (dispatchFragmentActionTemp.getWhat() == 0) {
//                            toyAdapterOne.notifyDataSetChanged();
////                            } else if (dispatchFragmentActionTemp.getWhat() == 1) {
////                                toyAdapterTwo.notifyDataSetChanged();
////                            }
//                            break;
//                        case DispatchFragmentActionTemp.ACTION_SHOW_ONE:
//                            break;
//                        case DispatchFragmentActionTemp.ACTION_SHOW_TWO:
//                            break;
//                        case DispatchFragmentActionTemp.ACTION_HIDE_DETAIL:
//                            cvDetail.setVisibility(View.GONE);
//                            break;
//                        case DispatchFragmentActionTemp.ACTION_GET_ITEM_DATA_BY_POSITION:
//                            //这里已经不再发送RxBus来跳转Activity了，所以下面的代码已经没用了。
////                            int position = (int) dispatchFragmentActionTemp.getT();
////                            long unitID = mDispatchFragmentStore.getOwnSMCHResultItemList().get(position).getUnitKey();
//                            long unitID = (long) dispatchFragmentActionTemp.getT();
//                            MainActivityAction activityAction = new MainActivityAction(MainActivityAction.ACTION_GET_UNIT_ID);
//                            activityAction.setUnitId(unitID);
//                            RxBus.getInstance().send(activityAction);
//                            break;
//                    }
//
//                }
//            });
//
//        }
//        return mView;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initViews();
//        mProgressDialog = new ProgressDialog(mContext);
//        mProgressDialog.setTitle("提示");
//        mProgressDialog.setCancelable(true);
//        mSubscriptionAction = RxBus.getInstance().toObservable(UnitFragmentActionTemp.class)
//                .subscribe(unitFragmentActionTemp -> {
//                    switch (unitFragmentActionTemp.getAction()) {
//                        case UnitFragmentActionTemp.HIDE_SEARCH_BAR:
//                            animHideSearchBar();
//                            break;
//                        case UnitFragmentActionTemp.SHOW_SEARCH_BAR:
//                            animShowSearchBar();
//                            break;
//                        case UnitFragmentActionTemp.ACTION_SYNC_ENVELOPE:
//                            Envelope envelope = ((Geometry) unitFragmentActionTemp.getT()).getExtent();
//                            mMapView.setViewpointGeometryAsync(envelope, 200);
//                            mFeaturelayer.clearSelection();
//                            if (mAllFeatureQueryResult != null) {
//                                //遍历所有Feature
//                                for (Feature featureAll : mAllFeatureQueryResult) {
//                                    //如果两个的getCenter equals，那么就认为它们是同一个点
//                                    //同时显示它
//                                    if (featureAll.getGeometry().getExtent().getCenter().equals(envelope.getCenter())) {
//                                        mFeaturelayer.selectFeature(featureAll);
//                                    }
//                                }
//                            }
//                            break;
//                        case UnitFragmentActionTemp.ACTION_CLEAR:
//                            mDispatchFragmentStore.getListHYLB_CODE().clear();
//                            mDispatchFragmentStore.getListZCD_CODE().clear();
//                            etSearch.setText("");
//                            cvClear.setVisibility(View.GONE);
//                            RxBus.getInstance().send(new MainActivityAction(MainActivityAction.CLEAR_TEXT));
//                            showAll();
//                            break;
//                    }
//
//                });
//        initMap();
//    }
//
//    /***
//     * 动画方式隐藏导航栏
//     */
//    private void animHideSearchBar() {
//        ObjectAnimWrapper objectAnimWrapper = new ObjectAnimWrapper(cvSearch);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(objectAnimWrapper, "topMargin", objectAnimWrapper.getTopMargin(), (DeviceUtil.dip2px(mContext, 2 + 45)) * -1);
//        objectAnimator.setDuration(150);
//        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        objectAnimator.start();
//    }
//
//    /***
//     * 动画方式显示导航栏
//     */
//    private void animShowSearchBar() {
//        ObjectAnimWrapper objectAnimWrapper = new ObjectAnimWrapper(cvSearch);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(objectAnimWrapper, "topMargin", objectAnimWrapper.getTopMargin(), DeviceUtil.dip2px(mContext, 2));
//        objectAnimator.setDuration(150);
//        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        objectAnimator.start();
//    }
//
//    /***
//     * 初始化Views
//     */
//    private void initViews() {
//        //放大
//        mView.findViewById(R.id.aiv_zoom_in).setOnClickListener(view -> {
//            mMapView.setViewpointScaleAsync((mMapView.getMapScale() / 2) > 2000 ? mMapView.getMapScale() / 2 : 2000);
//            Log.d("tag", mMapView.getMapScale() + "");
//        });
//        //缩小
//        mView.findViewById(R.id.aiv_zoom_out).setOnClickListener(view -> {
//            Log.d("tag", mMapView.getMapScale() + "");
//            mMapView.setViewpointScaleAsync(mMapView.getMapScale() * 2 > 1200000 ? 1200000 : mMapView.getMapScale() * 2);//缩小不会导致崩溃
//        });
//        //为筛选添加点击事件,条件查询
//        mView.findViewById(R.id.cv_sift).setOnClickListener(this::siftSearch);
//        //我的位置
//        mView.findViewById(R.id.cv_my_location).setOnClickListener(view -> {
////            Intent intent = new Intent(mContext, TestTitleActivity.class);
////            startActivity(intent);
//            mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
//            if (!mLocationDisplay.isStarted()) {
//                mLocationDisplay.startAsync();
//            }
//        });
//        //搜索框里的放大镜按钮
//        acivSearch = (AppCompatImageView) mView.findViewById(R.id.aciv_search);
//        //搜索框里的X按钮
//        acivClear = (AppCompatImageView) mView.findViewById(R.id.aciv_clear);
//        //搜索框里的输入框
//        etSearch = (EditText) mView.findViewById(R.id.et_search);
//        etSearch.setImeActionLabel("搜索", EditorInfo.IME_ACTION_DONE);
//        etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_DONE) {
//                    acivSearch.performClick();
//                }
//                return false;
//            }
//        });
//        etSearch.addTextChangedListener(new TextWatcher() {
//
//            VectorDrawableCompat a = VectorDrawableCompat.create(getResources(), R.drawable.ic_search_black_24dp, mContext.getTheme());
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
////                LogUtil.e("------>"+charSequence.toString());
//                if (!editable.toString().equals("")) {
//                    acivClear.setVisibility(View.VISIBLE);
//                    assert a != null;
//                    a.setTint(mContext.getResources().getColor(R.color.colorPrimary)); //设置单一的颜色
//                    acivSearch.setImageDrawable(a);
//                } else {
//                    acivClear.setVisibility(View.GONE);
//                    assert a != null;
//                    a.setTint(mContext.getResources().getColor(R.color.secondaryText)); //设置单一的颜色
//                    acivSearch.setImageDrawable(a);
//                }
//                etString = editable.toString();
//            }
//        });
//        acivClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                etSearch.setText("");
//            }
//        });
//        //点击搜索按钮
//        acivSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                initEtSearch();
//            }
//        });
//        //整个搜索框
//        cvSearch = (CardView) mView.findViewById(R.id.cv_search);
//        //为切换地图添加点击事件
//        mView.findViewById(R.id.cv_layer).setOnClickListener(view -> {
//            View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_map_switch, (ViewGroup) mView, false);//layout_switch_layer //layout_result_dispatch_filter
//            RadioGroup rgLayerSwitch = (RadioGroup) contentView.findViewById(R.id.rg_layer_switch);
//            switch (currBaseMapType) {
//                case topographic:
//                    ((RadioButton) rgLayerSwitch.findViewById(R.id.rb_topographic)).setChecked(true);
//                    break;
//                case vector:
//                    ((RadioButton) rgLayerSwitch.findViewById(R.id.rb_vector)).setChecked(true);
//                    break;
//                case image:
//                    ((RadioButton) rgLayerSwitch.findViewById(R.id.rb_image)).setChecked(true);
//                    break;
//            }
//            rgLayerSwitch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                ArcGISTiledLayer arcGISTiledLayer;
//                //                Basemap basemap;
////                ArcGISMap arcGISMap;
//
//                @Override
//                public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                    switch (i) {
//                        case R.id.rb_topographic:
//                            //地形图
////                            RxBus.getInstance().send(new MainActivityAction(MainActivityAction.SWITCH_TO_TOPOGRAPHIC));
//                            arcGISTiledLayer = new ArcGISTiledLayer(getString(R.string.tdt_dem_base_map_url));
////                            basemap = new Basemap(arcGISTiledLayer);
//                            basemap.getBaseLayers().clear();
//                            basemap.getBaseLayers().add(arcGISTiledLayer);
////                            arcGISMap = new ArcGISMap(basemap);
//                            arcGISMap.setInitialViewpoint(new Viewpoint(29.55, 106.55, 100000));
//                            arcGISMap.setMaxScale(1);
//                            arcGISMap.setMinScale(5000000);
//                            currBaseMapType = topographic;
//                            break;
//                        case R.id.rb_vector:
//                            //矢量图
////                            RxBus.getInstance().send(new MainActivityAction(MainActivityAction.SWITCH_TO_VECTOR));
////                            arcGISTiledLayer = new ArcGISTiledLayer("http://www.digitalcq.com/RemoteRest/services/CQMap_VEC/MapServer");
//                            arcGISTiledLayer = new ArcGISTiledLayer(getString(R.string.tdt_vec_base_map_url));
////                            basemap = new Basemap(arcGISTiledLayer);
////                            arcGISMap = new ArcGISMap(basemap);
//                            basemap.getBaseLayers().clear();
//                            basemap.getBaseLayers().add(arcGISTiledLayer);
//                            arcGISMap.setInitialViewpoint(new Viewpoint(29.55, 106.55, 100000));
//                            arcGISMap.setMaxScale(1);
//                            arcGISMap.setMinScale(5000000);
//                            currBaseMapType = vector;
//                            break;
//                        case R.id.rb_image:
//                            //影像图(卫星图)
////                            RxBus.getInstance().send(new MainActivityAction(MainActivityAction.SWITCH_TO_IMAGE));
//                            arcGISTiledLayer = new ArcGISTiledLayer(getString(R.string.tdt_img_base_map_url));
////                            basemap = new Basemap(arcGISTiledLayer);
////                            arcGISMap = new ArcGISMap(basemap);
//                            basemap.getBaseLayers().clear();
//                            basemap.getBaseLayers().add(arcGISTiledLayer);
//                            arcGISMap.setInitialViewpoint(new Viewpoint(29.55, 106.55, 100000));
//                            arcGISMap.setMaxScale(1);
//                            arcGISMap.setMinScale(5000000);
//                            currBaseMapType = image;
//                            break;
//                    }
//                    if (arcGISMap != null) {
//                        mMapView.setMap(arcGISMap);
//                    }
//                    DialogUtil.getInstance().dismiss();
//                }
//            });
//            DialogUtil.getInstance().showAnchorDialog(contentView, view);
//        });
//        ///////////////////////////////////////////
//        cvDetail = (CardView) mView.findViewById(R.id.cv_detail);//整个大容器
//        cvCloseDetail = (CardView) mView.findViewById(R.id.cv_close_detail);//关闭按钮
//        cvCloseDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cvDetail.setVisibility(View.GONE);
//            }
//        });
//        llOne = (LinearLayout) mView.findViewById(R.id.ll_one);//第一个小层
//        llTwo = (LinearLayout) mView.findViewById(R.id.ll_two);//第二个小层
//        //第一个里的需要填充的TextViews
//        tvCode = (TextView) mView.findViewById(R.id.tv_code);
//        tvDate = (TextView) mView.findViewById(R.id.tv_date);
//        tvBase = (TextView) mView.findViewById(R.id.tv_base);
//        tvFormat = (TextView) mView.findViewById(R.id.tv_format);
//        //第二个里的需要填充的TextViews
//        tvCall = (TextView) mView.findViewById(R.id.tv_call);
//        tvDig = (TextView) mView.findViewById(R.id.tv_dig);
//        tvRank = (TextView) mView.findViewById(R.id.tv_rank);
//        tvHCJZ = (TextView) mView.findViewById(R.id.tv_HCJZ);
//        rvOne = (RecyclerView) mView.findViewById(R.id.rv_one);
//        rvTwo = (RecyclerView) mView.findViewById(R.id.rv_two);
//        GridLayoutManager gridLayoutManagerOne = new GridLayoutManager(mContext, 2);
//        GridLayoutManager gridLayoutManagerTwo = new GridLayoutManager(mContext, 2);
//        rvOne.setLayoutManager(gridLayoutManagerOne);
//        rvTwo.setLayoutManager(gridLayoutManagerTwo);
//        toyAdapterOne = new ToyAdapter(mContext);
//        toyAdapterTwo = new ToyAdapter(mContext);
//        toyAdapterOne.setData(listToy);
//        toyAdapterTwo.setData(listToy);
//        linearLayout1 = (LinearLayout) mView.findViewById(R.id.linearLayout1);
//        linearLayout2 = (LinearLayout) mView.findViewById(R.id.linearLayout2);
//        rvOne.setAdapter(toyAdapterOne);
//        rvTwo.setAdapter(toyAdapterTwo);
//        //
//        mSpaceSearch = mView.findViewById(R.id.cv_spaceSearch);
//        mClassifySearch = mView.findViewById(R.id.cv_classifySearch);
//        mSpaceSearch.setOnClickListener(this::onSpaceSearchClick);
//        mClassifySearch.setOnClickListener(this::onClassifySearch);
//        cvCancle = (CardView) mView.findViewById(R.id.cv_cancel);
//        cvCancle.setOnClickListener(this::onCancelClick);
//        mIvUser = mView.findViewById(R.id.iv_user);
//        mIvUser.setOnClickListener(v -> ((MainActivity) getActivity()).openDrawer());
//    }
//
//    private void onCancelClick(View view) {
//        restoreDrawQuery();
//    }
//
//    //条件查询
//    private void siftSearch(View view) {
//        View contentView = LayoutInflater
//                .from(mContext)
//                .inflate(R.layout.layout_result_dispatch_filter,
//                        (ViewGroup) ((Activity) mContext).getWindow().getDecorView().getRootView(),
//                        false);
////                RecyclerView recyclerView1 = (RecyclerView) contentView.findViewById(R.id.recyclerView1);
//        calendar1 = (AppCompatImageView) contentView.findViewById(R.id.calendar1);
//        calendar2 = (AppCompatImageView) contentView.findViewById(R.id.calendar2);
//        relativeLayoutLeft = (RelativeLayout) contentView.findViewById(R.id.relativeLayoutLeft);
//        relativeLayoutRight = (RelativeLayout) contentView.findViewById(R.id.relativeLayoutRight);
//
//        calendar1Content = (TextView) contentView.findViewById(R.id.calendar1_content);
//        calendar2Content = (TextView) contentView.findViewById(R.id.calendar2_content);
//
////            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        calendar2Content.setText("结束时间");
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
////            String dateString = year + "-" + month + "-" + day;
//        calendar1Content.setText("开始时间");
//
//        relativeLayoutLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isFirst = true;
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd1 = DatePickerDialog.newInstance(DispatchFragment.this,
//                        now.get(Calendar.YEAR),
//                        now.get(Calendar.MONTH) - 1,
//                        now.get(Calendar.DAY_OF_MONTH)
//                );
//                dpd1.setVersion(DatePickerDialog.Version.VERSION_2);
//                dpd1.show(getFragmentManager(), "Datepickerdialog");
//            }
//        });
//        relativeLayoutRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isFirst = false;
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd2 = DatePickerDialog.newInstance(
//                        DispatchFragment.this,
//                        now.get(Calendar.YEAR),
//                        now.get(Calendar.MONTH),
//                        now.get(Calendar.DAY_OF_MONTH)
//                );
//                dpd2.setVersion(DatePickerDialog.Version.VERSION_2);
//                dpd2.show(getFragmentManager(), "Datepickerdialog");
//            }
//        });
//        AppCompatButton acbQuery = (AppCompatButton) contentView.findViewById(R.id.acb_query);
//        initEtSearchForDialog(contentView);
//        initAcbSearchForDialog(acbQuery);
//        DialogUtil.getInstance().showAnchorDialog(contentView, view);
//    }
//
//    /**
//     * 空间查询
//     */
//    public void onSpaceSearchClick(View view) {
//        //画完，或者没画
//        if (currDrawType == DRAWTYPE_NONE || currDrawType == DRAWTYPE_FINISH) {
//            View contentView = LayoutInflater
//                    .from(mContext)
//                    .inflate(R.layout.dialog_dispatch_draw_type, (ViewGroup) mView,
//                            false);//layout_switch_layer //layout_result_dispatch_filter
//            for (int i = 0; i < ((ViewGroup) contentView).getChildCount(); i++) {
//                ((ViewGroup) contentView).getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mSketchGraphicsOverlay.clear();
//                        switch (view.getId()) {
//                            case R.id.tv_circle://画圆
//                                if (currDrawType != DRAWTYPE_CIRCLE) {
//                                    ToastUtil.showShort("画圆暂未实现！");
//                                    return;
//                                }
//                                currDrawType = DRAWTYPE_CIRCLE;
//                                mSketchGraphicsOverlay.setDrawingMode(SketchGraphicsOverlay.DrawingMode.CIRCLE);
//                                break;
//                            case R.id.tv_rectangle://矩形选择
//                                currDrawType = DRAWTYPE_RECTANGLE;
//                                mSketchGraphicsOverlay.setDrawingMode(SketchGraphicsOverlay.DrawingMode.RECTANGLE);
//                                showCancelButton();
//                                break;
//                            case R.id.tv_polygon://多边形
//                                currDrawType = DRAWTYPE_POLYGON;
//                                mSketchGraphicsOverlay.setDrawingMode(SketchGraphicsOverlay.DrawingMode.POLYGON);
//                                showCancelButton();
//                                break;
//                            case R.id.tv_flash://缓冲区
//                                currDrawType = DRAWTYPE_FLASH;
//                                mSketchGraphicsOverlay.setDrawingMode(SketchGraphicsOverlay.DrawingMode.POLYLINE);
//                                showCancelButton();
//                                break;
//                            case R.id.tv_symbol:
//                                showSymbolDialog();
//                                break;
//                            case R.id.tv_adminRegion:
////                                ToastUtil.showShort("tv_adminRegion");
//                                showAdminRegion();
//                                break;
//                        }
//                    }
//                });
//            }
//            DialogUtil.getInstance().showAnchorDialog(contentView, view);
//        } else if (currDrawType == DRAWTYPE_SEARCHING) {//正在搜索
////            mProgressDialog.setMessage("搜索中！");
////            mProgressDialog.show();
//
//            //画形状
//        } else if (currDrawType == DRAWTYPE_CIRCLE || currDrawType == DRAWTYPE_RECTANGLE ||
//                currDrawType == DRAWTYPE_POLYGON) {
//            searchPolygonToData();
//        } else if (currDrawType == DRAWTYPE_FLASH) {//缓冲区查询
////            if (mSketchGraphicsOverlay.getCurrentPolygon() == null) {
////                ToastUtil.showShort("请多次点击地图圈选出需要查询的区域后再搜索");
////                return;
////            }
//            if (km != 0) {
////                    ToastUtil.showShort("查询数据");
//                searchPolygonToData();
//                km = 0;
//                return;
//            }
//            QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
//            builder.setTitle(R.string.alert_title)
//                    .setInputType(InputType.TYPE_CLASS_NUMBER)
//                    .addAction(R.string.alert_ok, (qmuiDialog, i) -> {
//                        String numText = builder.getEditText().getText().toString();
//                        if (TextUtils.isEmpty(numText)) {
//                            ToastUtil.showShort("范围不能为空");
//                            return;
//                        }
//                        km = Integer.parseInt(numText);
//                        if (km > 0 && km < 100) {
//                            qmuiDialog.dismiss();
////                                ToastUtil.showShort("画缓冲区的形状");
//                            mSketchGraphicsOverlay.setBufferGeometry(km);
//                        } else {
//                            ToastUtil.showShort("范围为0-100公里");
//                        }
//
//                    })
//                    .addAction(R.string.alert_cancel, (qmuiDialog, i) -> {
//                        qmuiDialog.dismiss();
//                    })
//                    .setPlaceholder("请输入范围(0-100公里)")
//                    .show();
//        } else if (currDrawType == DRAWTYPE_SYMBOL) {//图幅号查询
//            ToastUtil.showShort("图幅号查询");
//            searchPolygonToData();
////            searchGeometryOrSql(null);
//        } else if (currDrawType == DRAWTYPE_ADMIN_REGION) {//行政区域查询
////            searchGeometryOrSql(null);
//            searchPolygonToData();
//        }
//    }
//
//    private void showCancelButton() {
//        cvCancle.setVisibility(View.VISIBLE);
//        DialogUtil.getInstance().dismiss();
////                        ((TextView) mSpaceSearch.findViewById(R.id.tv_space)).setText("搜索");
//        ((AppCompatImageView) mSpaceSearch.findViewById(R.id.aci_space))
//                .setImageResource(R.mipmap.ic_sure_modifi);
//    }
//
//    private void showSymbolDialog() {
//        new SimpleAlertDialog(getActivity())
//                .title("图幅号查询")
//                .message("可输入多个图幅号，图幅号直接用空格或逗号分隔如\n'G49E005001 G49E005002'\n或\n'G49E005001,G49E005002'")
//                .setOkOnClickListener(R.string.alert_ok, (dialog1, view) -> {
//                    String text = dialog1.getEditText().getText().toString();
//                    if (TextUtils.isEmpty(text)) {
//                        ToastUtil.showShort("不能为空");
//                        return;
//                    }
//                    currDrawType = DRAWTYPE_SYMBOL;
//                    showCancelButton();
//                    searchSymbol(text);
//                })
//                .visiableEditText()
//                .setCancelOnClickListener(R.string.alert_cancel, null)
//                .alert();
//    }
//
//    private int lastPosition = -1;
//
//    //行政区域查询show dialog
//    private void showAdminRegion() {
//        lastPosition = -1;
//        Map<Object, Object> map = new HashMap<>();
//        APIService.getInstance()
//                .getAreaList(map, new SimpleSubscriber<List<AreaModel>>() {
//                    @Override
//                    public void onResponse(List<AreaModel> response) {
//                        View dialogView = View.inflate(getActivity(), R.layout.dialog_admin_region,
//                                null);
//                        RecyclerView rv = dialogView.findViewById(R.id.recycler_view);
//                        Button btn_ok = dialogView.findViewById(R.id.btn_ok);
//                        rv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
//                        AdminRegionAdapter adapter = new AdminRegionAdapter(getActivity(), response);
//                        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
////                                ToastUtil.showShort("onItemClick");
////                                TextView textView = view.findViewById(R.id.tv_title);
//                                if (position == lastPosition) return;
//                                if (response.get(position).isChecked()) {
//                                    response.get(position).setChecked(false);
////                                    textView.setSelected(false);
//                                } else {
//                                    response.get(position).setChecked(true);
//                                    if (lastPosition != -1) {
//                                        response.get(lastPosition).setChecked(false);
//                                    }
////                                    textView.setSelected(true);
//                                }
//                                adapter.notifyDataSetChanged();
//                                lastPosition = position;
//                            }
//
//                            @Override
//                            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                                return false;
//                            }
//                        });
//                        rv.setAdapter(adapter);
//                        AppCompatDialog dialog = new AppCompatDialog(getActivity());
//                        dialog.setContentView(dialogView);
//                        dialog.show();
//                        btn_ok.setOnClickListener(v -> {
//                            if (lastPosition == -1) {
//                                ToastUtil.showShort("请选择一个区域");
//                                return;
//                            }
//                            dialog.dismiss();
//                            currDrawType = DRAWTYPE_ADMIN_REGION;
//                            showCancelButton();
//                            searchAdminRegion(response.get(lastPosition).getAreaName());
//                        });
//                    }
//                });
//    }
//
//    //行政区域查询
//    private void searchAdminRegion(String areaStr) {
//        String urlStr = getString(R.string.query_admin_region);
//        QueryParameters query = new QueryParameters();
//        String where = String.format("1=1 and MC='%s'", areaStr);
////        String where = "1=1";
//        query.setWhereClause(where);
//        query.setReturnGeometry(true);
//        query.setOutSpatialReference(mMapView.getSpatialReference());
//
//        ServiceFeatureTable sft = new ServiceFeatureTable(urlStr);
//        final ListenableFuture<FeatureQueryResult> future = sft.queryFeaturesAsync(query);
//        future.addDoneListener(() -> {
//            try {
//                FeatureQueryResult features = future.get();
//                if (features == null || !features.iterator().hasNext()) {
//                    ToastUtil.showShort("未查询到数据");
////                    LogUtil.i("未查询到数据");
//                    return;
//                }
//                LogUtil.i("查询到数据");
//                SimpleLineSymbol symbol =
//                        new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
//                                Color.RED, 2);
//                for (Feature feature : features) {
//                    Graphic graphic = new Graphic(feature.getGeometry(), symbol);
//                    mSketchGraphicsOverlay.setCurrentPolygon(graphic);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    //图幅号查询
//    private void searchSymbol(String text) {
//        if (text.contains(",")) {
//            String[] split = text.split(",");
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < split.length; i++) {
//                sb.append(String.format("'%s',", split[i]));
//            }
//            text = sb.substring(0, sb.lastIndexOf(","));
//        }
//        if (text.contains(" ")) {
//            String[] split = text.split(" ");
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < split.length; i++) {
//                sb.append(String.format("'%s',", split[i]));
//            }
//            text = sb.substring(0, sb.lastIndexOf(","));
//        }
//        for (int i = 0; i < 7; i++) {
//            QueryParameters query = new QueryParameters();
////            query.setWhereClause("1=1");
//            String where = String.format("1=1 and 新图号 in (%s)", text);
//            query.setWhereClause(where);
////            LogUtil.i("where = " + where);
//            query.setReturnGeometry(true);
//            query.setOutSpatialReference(mMapView.getSpatialReference());
//            String urlStr = String.format("http://ddk.digitalcq.com:6080/arcgis/rest/" +
//                    "services/CQGRID_2000/Mapserver/%s", i);
//            ServiceFeatureTable sft = new ServiceFeatureTable(urlStr);
//            final ListenableFuture<FeatureQueryResult> future = sft.queryFeaturesAsync(query);
//            future.addDoneListener(() -> {
////                LogUtil.i("回调成功");
//                try {
//                    FeatureQueryResult features = future.get();
//                    if (features == null || !features.iterator().hasNext()) {
////                        ToastUtil.showShort("未查询到数据");
//                        return;
//                    }
//                    SimpleLineSymbol symbol =
//                            new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
//                                    Color.RED, 2);
//                    for (Feature feature : features) {
//                        Graphic graphic = new Graphic(feature.getGeometry(), symbol);
//                        mSketchGraphicsOverlay.setCurrentPolygon(graphic);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
////                    ToastUtil.showShort("Exception");
//                }
//            });
//
//        }
//    }
//
//    private void restoreDrawQuery() {
////        ((TextView) mSpaceSearch.findViewById(R.id.tv_space)).setText("空间");
//        ((AppCompatImageView) mSpaceSearch.findViewById(R.id.aci_space))
//                .setImageResource(R.mipmap.ic_kongjian);
//        currDrawType = DRAWTYPE_NONE;
//        mSketchGraphicsOverlay.clear();
//        cvCancle.setVisibility(View.GONE);
//    }
//
//    //用polygon搜索数据
//    private void searchPolygonToData() {
//        Observable.create((Observable.OnSubscribe<String>) subscriber -> {
//            if (mSketchGraphicsOverlay.getCurrentPolygon() == null) {
//                if (currDrawType == DRAWTYPE_CIRCLE) {
//                    subscriber.onError(new Throwable("请滑动手指画出需要查询区域的圆形后再搜索"));
//                } else if (currDrawType == DRAWTYPE_RECTANGLE) {
//                    subscriber.onError(new Throwable("请滑动手指画出需要查询区域的矩形后再搜索"));
//                } else if (currDrawType == DRAWTYPE_POLYGON) {
//                    subscriber.onError(new Throwable("请多次点击地图圈选出需要查询的区域后再搜索！"));
//                } else if (currDrawType == DRAWTYPE_FLASH) {
//                    subscriber.onError(new Throwable("请多次点击地图圈选出需要查询的区域后再搜索！"));
//                } else if (currDrawType == DRAWTYPE_SYMBOL) {
//                    subscriber.onError(new Throwable("请输入图幅号后再搜索！"));
//                    restoreDrawQuery();
//                } else if (currDrawType == DRAWTYPE_ADMIN_REGION) {
//                    subscriber.onError(new Throwable("请选择区域后再搜索！"));
//                    restoreDrawQuery();
//                }
//
//            } else {
//                subscriber.onNext("START");
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(() -> {
////                            mMySketchGraphicsOverlayEventListener.onDrawingFinished();
//                    mProgressDialog.setMessage("搜索中……");
//                    mProgressDialog.show();
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//                        ToastUtil.showShort("搜索完成！");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        dismissProgressDialog();
//                        ToastUtil.showLong(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        searchGeometryOrSql(null);
//                    }
//                });
//    }
//
//    /**
//     * 分类查询
//     */
//    public void onClassifySearch(View view) {
////        ToastUtil.showShort("分类查询");
//
//        View dialogRoot = LayoutInflater.from(getActivity())
//                .inflate(R.layout.dialog_classify_search, ((ViewGroup) mView), false);
//        TagFlowLayout typeFlowLayout = dialogRoot.findViewById(R.id.flow_type);
////        RecyclerView rvType = dialogRoot.findViewById(R.id.rv_type);
//
//        APIService.getInstance().getfruitCategoryList(
//                new SimpleSubscriber<List<FruitCategoryListModel>>() {
//                    @Override
//                    public void onResponse(List<FruitCategoryListModel> response) {
//
//                        TagAdapter<FruitCategoryListModel> flowTypeAdapter = new TagAdapter<FruitCategoryListModel>(response) {
//                            @Override
//                            public View getView(FlowLayout flowLayout, int i, FruitCategoryListModel o) {
//                                TextView textView = (TextView) View.inflate(getActivity(), R.layout.item_flow_type, null);
//                                textView.setText(o.categoryName);
//                                return textView;
//                            }
//
//                            @Override
//                            public void onSelected(int position, View view) {
//                                super.onSelected(position, view);
//                                mBody = new ReportHandoutListBody();
//                                if (response == null) {
//                                    return;
//                                }
//                                FruitCategoryListModel model = response.get(position);
//                                if (model == null) {
//                                    return;
//                                }
//                                mBody.setFruitCategoryId(model.fruitCategoryId);
//                                switchType(model, dialogRoot);
//                            }
//                        };
//                        typeFlowLayout.setAdapter(flowTypeAdapter);
//                        flowTypeAdapter.setSelectedList(0);
//                    }
//                });
//
//
//        DialogUtil.getInstance().showAnchorDialog(dialogRoot, view);
//    }
//
//    //切换成果类型
//    private void switchType(FruitCategoryListModel model, View dialogRoot) {
//        APIService.getInstance().gethandoutConditionByFCList(model.fruitCategoryId,
//                new SimpleSubscriber<List<GethandoutConditionByFCModel>>() {
//                    @Override
//                    public void onResponse(List<GethandoutConditionByFCModel> response) {
//                        if (response == null || response.isEmpty()) {
//                            return;
//                        }
//
//                        LinearLayout llContainer = dialogRoot.findViewById(R.id.ll_container);
//                        llContainer.removeAllViews();
//                        mBody.attrValueList.clear();
//
//                        for (GethandoutConditionByFCModel gethandoutConditionByFCModel : response) {
//
//                            View item = LayoutInflater.from(dialogRoot.getContext())
//                                    .inflate(R.layout.dispatch_dialog_item, llContainer, false);
//
//                            TagFlowLayout tagFlowLayout = (TagFlowLayout) LayoutInflater
//                                    .from(dialogRoot.getContext())
//                                    .inflate(R.layout.dispatch_dialog_item_tag_flow_layout,
//                                            llContainer, false);
//
//                            TextView tv1 = item.findViewById(R.id.tv_attrName);
//                            tv1.setText(gethandoutConditionByFCModel.attrName);
//                            if (gethandoutConditionByFCModel.fruitCateGoryAttrVal == null) {
//                                gethandoutConditionByFCModel.fruitCateGoryAttrVal =
//                                        new ArrayList<>();
//                            }
//                            gethandoutConditionByFCModel.fruitCateGoryAttrVal
//                                    .add(0, new GethandoutConditionByFCModel
//                                            .FruitCateGoryAttrVal("全部"));
//
//                            TagAdapter<GethandoutConditionByFCModel.FruitCateGoryAttrVal> ta1
//                                    = new TagAdapter<GethandoutConditionByFCModel.FruitCateGoryAttrVal>(gethandoutConditionByFCModel.fruitCateGoryAttrVal) {
//                                @Override
//                                public View getView(FlowLayout flowLayout, int i,
//                                                    GethandoutConditionByFCModel.FruitCateGoryAttrVal o) {
//                                    TextView textView = (TextView) View.inflate(getActivity(),
//                                            R.layout.item_flow_type, null);
//                                    textView.setText(o.attrValue);
//                                    return textView;
//                                }
//
//                                @Override
//                                public void onSelected(int position, View view) {
//                                    super.onSelected(position, view);
//                                    if (position == 0) return;
//                                    long fruitCategoryAttrId = gethandoutConditionByFCModel.fruitCategoryAttrId;
//                                    String attrValue = gethandoutConditionByFCModel.fruitCateGoryAttrVal.get(position).attrValue;
//                                    mBody.attrValueList
//                                            .add(new ReportHandoutListBody
//                                                    .AttrValueList(fruitCategoryAttrId, attrValue));
//                                }
//                            };
//                            tagFlowLayout.setAdapter(ta1);
//                            ta1.setSelectedList(0);
//
//                            llContainer.addView(item);
//                            llContainer.addView(tagFlowLayout);
//                        }
//
//                        Button btn_search = dialogRoot.findViewById(R.id.btn_search);
//                        //
//                        btn_search.setOnClickListener(v -> {
//                            reportHandoutList();
//                        });
//                    }
//                });
//    }
//
//    //查询分类
//    private void reportHandoutList() {
//        APIService.getInstance().getReportHandoutList(mBody, new SimpleSubscriber<List<ReportHandoutListModel>>() {
//            @Override
//            public void onResponse(List<ReportHandoutListModel> response) {
//
//                if (response == null || response.isEmpty()) return;
//                StringBuilder sb = new StringBuilder();
//                sb.append("FRUITID");
//                sb.append(" in ");
//                sb.append("(");
//                int mapType = 0;
//                for (ReportHandoutListModel reportHandoutListModel : response) {
//
//                    for (ReportHandoutListModel.FruitCategoryList fruitCategoryList :
//                            reportHandoutListModel.fruitCategoryList) {
//
//                        mapType = fruitCategoryList.mapType;
//                        for (ReportHandoutListModel.FruitCategoryList.FruitList fruitList :
//                                fruitCategoryList.fruitList) {
//
//                            long fruitId = fruitList.fruitId;
//                            sb.append(String.valueOf(fruitId));
//                            sb.append(",");
//                        }
//
//                    }
//
//                }
//
//                String whereClause = sb.substring(0, sb.lastIndexOf(",")) + ")";
//                LogUtil.i("whereClause == " + whereClause);
//
////                searchGeometryOrSql(whereClause);
//                searchType(mapType, whereClause);
//                DialogUtil.getInstance().dismiss();
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                ToastUtil.showShort(e.getMessage());
//                DialogUtil.getInstance().dismiss();
//            }
//        });
//    }
//
//    /***
//     * 初始化地图
//     */
//    private void initMap() {
//        mMapView = (MapView) mView.findViewById(R.id.mapView2);
////        ArcGISVectorTiledLayer arcGISVectorTiledLayer = new ArcGISVectorTiledLayer("http://www.digitalcq.com/RemoteRest/services/CQMap_VEC/MapServer");
//        ArcGISTiledLayer arcGISTiledLayer = new ArcGISTiledLayer(getString(R.string.tdt_vec_base_map_url));
////        ArcGISMapImageLayer arcGISMapImageLayer = new ArcGISMapImageLayer("http://www.digitalcq.com/RemoteRest/services/CQMap_VEC/MapServer");
//        basemap = new Basemap(arcGISTiledLayer);
//        arcGISMap = new ArcGISMap(basemap);
//        mMapView.setAttributionTextVisible(false);
//        arcGISMap.setInitialViewpoint(new Viewpoint(29.55, 106.55, 100000));
//        arcGISMap.setMaxScale(1);
//        arcGISMap.setMinScale(5000000);
//        mMapView.setMap(arcGISMap);
//        mServiceFeatureTable = new ServiceFeatureTable(getString(R.string.feature_server_url));
//        mFeaturelayer = new FeatureLayer(mServiceFeatureTable);
//        mFeaturelayer.setOpacity(0.8f);
////        mFeaturelayer.setLabelsEnabled(true);
//
//        mFeaturelayer.setSelectionColor(Color.argb(220, 255, 0, 0));
//        mFeaturelayer.setSelectionWidth(5);
//        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, mContext.getResources().getColor(R.color.colorPrimary), 10);
//        mFeaturelayer.setRenderer(new SimpleRenderer(simpleMarkerSymbol));//new UniqueValueRenderer()
//        arcGISMap.getOperationalLayers().add(mFeaturelayer);
//        mLocationDisplay = mMapView.getLocationDisplay();
//        mLocationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
//            @Override
//            public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {
//                if (dataSourceStatusChangedEvent.isStarted())
//                    return;
//                if (dataSourceStatusChangedEvent.getError() == null)
//                    return;
//                boolean permissionCheck1 = ContextCompat.checkSelfPermission(mContext, reqPermissions[0]) ==
//                        PackageManager.PERMISSION_GRANTED;
//                boolean permissionCheck2 = ContextCompat.checkSelfPermission(mContext, reqPermissions[1]) ==
//                        PackageManager.PERMISSION_GRANTED;
//                if (!(permissionCheck1 && permissionCheck2)) {
//                    ActivityCompat.requestPermissions((Activity) mContext, reqPermissions, requestCode);
//                } else {
//                    String message = String.format("Error in DataSourceStatusChangedListener: %s", dataSourceStatusChangedEvent
//                            .getSource().getLocationDataSource().getError().getMessage());
//                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        graphicsOverlay = addGraphicsOverlay(mMapView);
//        initAllFeatureQueryResult();
//        cvClear = (CardView) mView.findViewById(R.id.cv_clear);
//        cvClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDispatchFragmentStore.getListHYLB_CODE().clear();
//                mDispatchFragmentStore.getListZCD_CODE().clear();
//                etSearch.setText("");
//                cvClear.setVisibility(View.GONE);
//                RxBus.getInstance().send(new MainActivityAction(MainActivityAction.CLEAR_TEXT));
//                showAll();
//            }
//        });
//
//        MySketchGraphicsOverlayEventListener mMySketchGraphicsOverlayEventListener = new MySketchGraphicsOverlayEventListener();
//        mSketchGraphicsOverlay = new SketchGraphicsOverlay(mMapView, mMySketchGraphicsOverlayEventListener);
//        //设置地图上的marker点击事件（只有当处于不是polygon画图且空白区域里才触发事件）
//        mSketchGraphicsOverlay.setIOnEmptyClickListener(new SketchGraphicsOverlay.IOnEmptyClickListener() {
//            @Override
//            public void onEmptyClick(MotionEvent event) {
////                ToastUtil.showShort("onEmptyClick");
//                // get the point that was clicked and convert it to a point in map coordinates
//                final Point clickPoint = mMapView.screenToLocation(new android.graphics.Point(Math.round(event.getX()), Math.round(event.getY())));
//                // create a selection tolerance
//                int tolerance = 10;
//                double mapTolerance = tolerance * mMapView.getUnitsPerDensityIndependentPixel();
//                // use tolerance to create an envelope to querySearch
//                Envelope envelope = new Envelope(clickPoint.getX() - mapTolerance, clickPoint.getY() - mapTolerance, clickPoint.getX() + mapTolerance, clickPoint.getY() + mapTolerance, arcGISMap.getSpatialReference());
//                QueryParameters query = new QueryParameters();
//                query.setGeometry(envelope);
//                // request all available attribute fields
//                final ListenableFuture<FeatureQueryResult> future = mServiceFeatureTable.queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
//                // add done loading listener to fire when the selection returns
//                future.addDoneListener(() -> {
//                    //call get on the future to get the result
//                    FeatureQueryResult result = null;
//                    try {
//                        result = future.get();
//                        Iterator<Feature> iterator = result.iterator();
//                        Feature feature;
//                        if (iterator.hasNext()) {
//                            //如果已经点到Symbol，那么清空之前已选中的
//                            mFeaturelayer.clearSelection();
//                        }
//                        if (iterator.hasNext()) {
//                            feature = iterator.next();
////                                Envelope envelope = feature.getGeometry().getExtent();
//                            //发送到MainActivity，然后让MainActivity来选中recyclerView的某项item
//                            MainActivityAction<OwnSMCHResultItem> mainActivityAction =
//                                    new MainActivityAction<>(MainActivityAction.ACTION_SELECT_ITEM,
//                                            mDispatchFragmentStore.getOwnSMCHResultItemList());
//                            mainActivityAction.setFeature(feature);
//                            mFeaturelayer.selectFeature(feature);
//                            RxBus.getInstance().send(mainActivityAction);
//                        }
//                    } catch (InterruptedException | ExecutionException e1) {
//                        e1.printStackTrace();
//                    }
//                    // create an Iterator
//
//                });
//            }
//        });
//
//    }
//
//
//    /***
//     * 初始化顶部全局搜索框
//     */
//    private void initEtSearch() {
//        if (etSearch.getText().toString().equals("")) {
//            ToastUtil.showShort("请输入单位名称或组织机构代码后再搜索");
//            return;
//        }
////        Map<Object, Object> map = new HashMap<>();
////        if (!TextUtils.isEmpty(etSearch.getText().toString())) {
////            map.put("key", etSearch.getText().toString());
////        }
////        APIService.getInstance().getOrderList(map, new DoOnSubscriber<List<OrderModel>>() {
////            @Override
////            public void doOnSubscriber() {
////
////            }
////
////            @Override
////            public void onCompleted() {
////
////            }
////
////            @Override
////            public void onNext(List<OrderModel> orderModelList) {
////                StringBuilder stringBuilder = new StringBuilder();
////                for (int i = 0; i < orderModelList.size(); i++) {
//////                    LogUtil.i(companyModels.get(i).getCompanyName()+","+companyModels.get(i).getOrganizationCode());
////                    stringBuilder.append(Config.ARCGIS_UNITID);
////                    stringBuilder.append(" = ");
////                    stringBuilder.append(orderModelList.get(i).getCompanyId());
////                    if (i != orderModelList.size() - 1) {
////                        stringBuilder.append(" or ");
////                    }
////                }
////                searchGeometryOrSql(stringBuilder.toString());
////                try {
////                    DeviceUtil.closeKeyboard(etSearch, mContext);
////                } catch (Exception e) {
////                    LogUtil.e("关闭输入法失败！错误信息：" + e.getMessage());
////                }
////            }
////
////            @Override
////            public void onError(Throwable e) {
////                super.onError(e);
////                ToastUtil.showShort(e.getMessage());
////            }
////        });
//
//
////
////        aaaString sql = "name like '%" + etSearch.getText().toString() + "%' or qydm like '%" + etString + "%'";
////        searchGeometryOrSql(sql);
//
////        RxBus.getInstance().send(new MainActivityAction(MainActivityAction.CLEAR_TEXT));
//        mProgressDialog.setMessage("搜索中……");
//        mProgressDialog.show();
//        mDispatchFragmentActionsCreator.buildSql(etSearch.getText().toString(), "", "", 0, 0);
//        try {
//            DeviceUtil.closeKeyboard(etSearch, mContext);
//        } catch (Exception e) {
//            LogUtil.e("关闭输入法失败！错误信息：" + e.getMessage());
//        }
//    }
//
//    private void initEtSearchForDialog(View contentView) {
//        etCaseCode = (EditText) contentView.findViewById(R.id.et_case_code);//报件编号
//        etDataCode = (EditText) contentView.findViewById(R.id.et_data_code);//图幅数/数据编号
//        etSearchDialog = (EditText) contentView.findViewById(R.id.et_search);
//        calendar1_content = (TextView) contentView.findViewById(R.id.calendar1_content);
//        calendar2_content = (TextView) contentView.findViewById(R.id.calendar2_content);
//
//        etSearchDialog.setImeActionLabel("确定", EditorInfo.IME_ACTION_DONE);
//        if (!TextUtils.isEmpty(etString)) {
//            etSearchDialog.setText(etString);
//            etSearchDialog.setSelection(etString.length());
//        }
//        etSearchDialog.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                etString = editable.toString();
//                etSearch.setText(etString);
//                etSearch.setSelection(etString.length());
//            }
//        });
//    }
//
//    //条件查询确定按钮点击事件
//    private void initAcbSearchForDialog(AppCompatButton acbQuery) {
//        //设置'查询'按钮功能
//        acbQuery.setOnClickListener(view -> {
//            long data1 = 0, data2 = 0;
//            try {
//                data1 = DateUtil.stringToLong("yyyy-MM-dd", calendar1_content.getText().toString());
//            } catch (Exception e) {
////                    e.printStackTrace();
//            }
//            try {
//                data2 = DateUtil.stringToLong("yyyy-MM-dd", calendar2_content.getText().toString());
//            } catch (Exception e) {
////                    e.printStackTrace();
//            }
//            if (data1 != 0 && data2 == 0) {
//                ToastUtil.showShort("请选择结束时间");
//                return;
//            }
//            if (data1 == 0 && data2 != 0) {
//                ToastUtil.showShort("请选择开始时间");
//                return;
//            }
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
//        });
//    }
//
//    /***
//     * 搜索。如果sql为空那么就搜索多边形，否则搜索sql
//     */
//    private void searchGeometryOrSql(String sql) {
////        currDrawType = DRAWTYPE_SEARCHING;
//        mFeaturelayer.clearSelection();
//        cvClear.setVisibility(View.VISIBLE);
//        QueryParameters query;
//        if (TextUtils.isEmpty(sql)) {
//            query = new QueryParameters();
//            query.setGeometry(mSketchGraphicsOverlay.getCurrentPolygon().getGeometry());
//        } else {
//            query = new QueryParameters();
//            query.setWhereClause(sql);
//        }
//        //如果query不为空
//        querySearch(query);
//        mProgressDialog.dismiss();
//    }
//
//    private void searchType(int mapType, String whereClause) {
//        if (mapType == 0) return;
//        mFeaturelayer.clearSelection();
//        QueryParameters query = new QueryParameters();
//        query.setWhereClause(whereClause);
//
//        if (mapType == 1) {//点查询
//            mServiceFeatureTable = new ServiceFeatureTable(getString(R.string.point_feature_server_url));
//        } else {//面查询
//            mServiceFeatureTable = new ServiceFeatureTable(getString(R.string.polygon_feature_server_url));
//        }
//        mFeaturelayer = new FeatureLayer(mServiceFeatureTable);
//        querySearch(query);
//        mProgressDialog.dismiss();
//    }
//
//    private void querySearch(QueryParameters query) {
//        //已经隐藏所有，接下来再按条件查询，然后显示出来。
//        // call select features
////        ServiceFeatureTable.QueryFeatureFields queryFeatureFields
////                = ServiceFeatureTable.QueryFeatureFields.LOAD_ALL;
//        final ListenableFuture<FeatureQueryResult> future
//                = mServiceFeatureTable.queryFeaturesAsync(query);
////        final ListenableFuture<FeatureQueryResult> future = mServiceFeatureTable.queryFeaturesAsync(query);
//        // add done loading listener to fire when the selection returns
//        future.addDoneListener(() -> {
//            try {
//                LogUtil.i("回调成功");
//                // call get on the future to get the result
////                ToastUtil.showShort("try");
//                FeatureQueryResult result = future.get();
//                if (result == null || !result.iterator().hasNext()) {
//                    ToastUtil.showShort("未查询到任何信息");
//                    dismissProgressDialog();
//                    restoreDrawQuery();
//                    return;
//                }
//                hideAll();
//                for (Feature feature : result) {
//                    LogUtil.i("name=" + feature.getAttributes().get("name"));
//                    Envelope envelope = feature.getGeometry().getExtent();
//                    mMapView.setViewpointGeometryAsync(envelope, 200);
//                    mFeaturelayer.setFeatureVisible(feature, true);
//                    //Select the feature
//                    mFeaturelayer.selectFeature(feature);
//                    TextSymbol bassRockSymbol = new TextSymbol(10,
//                            feature.getAttributes().get("name").toString(),
//                            mContext.getResources().getColor(R.color.colorPrimary),
//                            TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.BOTTOM);
//                    Graphic bassRockGraphic = new Graphic(feature.getGeometry(), bassRockSymbol);
//                    graphicsOverlay.getGraphics().add(bassRockGraphic);
//                    LogUtil.e("查询到的点：" + feature.getGeometry().toJson());
////                    MainActivityAction action  = new MainActivityAction<>(MainActivityAction.ACTION_REFRESH_DATA_DISPATCH,
////                            mDispatchFragmentStore.getOwnSMCHResultItemList());
////                    RxBus.getInstance()
////                            .send(action);
//                }
////                if (query.getWhereClause().equals("")) {
////                    currDrawType = DRAWTYPE_FINISH;
////                }
//                dismissProgressDialog();
//                LogUtil.d("askldjflasjflasjdfl;asjklfjas;ldfjklaskjf2222");
//                for (Object object : mDispatchFragmentStore.getOwnSMCHResultItemList()) {
//                    OwnSMCHResultItem ownSMCHResultItem = (OwnSMCHResultItem) object;
//                    LogUtil.i("---------->" + ownSMCHResultItem.getData().getCompanyName());
//                }
//
//                buildUnitList(result);
//                restoreDrawQuery();
////                    RxBus.getInstance().send(new MainActivityAction<>(MainActivityAction.ACTION_REFRESH_DATA_DISPATCH, buildUnitList(result)));
//            } catch (Exception e) {
//                restoreDrawQuery();
//                Log.e(mContext.getResources().getString(R.string.app_name),
//                        "Feature search failed for: " + ". Error=" + e.getMessage());
//            }finally {
//                if (query.getWhereClause().equals("")) {
//                    currDrawType = DRAWTYPE_FINISH;
//                }
//            }
//
//        });
//    }
//
//    /***
//     * 隐藏所有点
//     */
//    private void hideAll() {
//        if (mAllFeatureQueryResult != null) {
//            for (Feature feature : mAllFeatureQueryResult) {
//                mFeaturelayer.setFeatureVisible(feature, false);
//                graphicsOverlay.getGraphics().clear();
//            }
//        }
//    }
//
//    /***
//     * 切换到显示单位图层
//     */
//    private void showAll() {
//        try {
//            if (mAllFeatureQueryResult != null) {
//                for (Feature featureAll : mAllFeatureQueryResult) {
//                    mFeaturelayer.clearSelection();
//                    mFeaturelayer.setFeatureVisible(featureAll, true);
//                    TextSymbol bassRockSymbol =
//                            new TextSymbol(10, featureAll.getAttributes().get("name").toString(),
//                                    mContext.getResources().getColor(R.color.colorPrimary),
//                                    TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.BOTTOM);
//                    Graphic bassRockGraphic = new Graphic(featureAll.getGeometry(), bassRockSymbol);
//                    graphicsOverlay.getGraphics().add(bassRockGraphic);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        unitLayer.setVisible(true);
//    }
//
//    private void buildUnitList(FeatureQueryResult featureQueryResult) {
//
//        List<CompanyDetailModel> companyDetailModels = new ArrayList<>();
//        Iterator<Feature> iterator = featureQueryResult.iterator();
//        Feature feature;
//        List<Map<String, Object>> companyIdList = new ArrayList<>();
//        while (iterator.hasNext()) {
//            feature = iterator.next();
//            int arcGis = Integer.parseInt(feature.getAttributes().get(Config.ARCGIS_UNITID).toString());
//            Map<String, Object> mapTemp = new HashMap<>();
//            mapTemp.put("unitId", arcGis);
//            mapTemp.put("geometry", feature.getGeometry());
//            companyIdList.add(mapTemp);
//        }
//
//        Observable.from(companyIdList)
//                .flatMap(map -> APIService.getInstance().getCompanyDetailObservable(map.get("unitId").toString()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DoOnSubscriber<CompanyDetailModel>() {
//                    @Override
//                    public void doOnSubscriber() {
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        RxBus.getInstance()
//                                .send(new MainActivityAction<>(MainActivityAction
//                                        .ACTION_REFRESH_DATA, companyDetailModels));
//                    }
//
//                    @Override
//                    public void onNext(CompanyDetailModel companyDetailModel) {
//                        LogUtil.e("--->" + companyDetailModel.toString());
//                        for (Map<String, Object> stringObjectMap : companyIdList) {
//                            if (stringObjectMap.get("unitId").equals(companyDetailModel.getCompanyId())) {
//                                companyDetailModel.setGeometry((Geometry) stringObjectMap.get("geometry"));
//                                break;
//                            }
//                        }
//                        companyDetailModels.add(companyDetailModel);
//                    }
//                });
//
//
////        ToastUtil.showShort("这里还没改！buildUnitList()");
////        List<UnitModel> unitModelList = new ArrayList<>();
////        Iterator<Feature> iterator = featureQueryResult.iterator();
////        Feature feature;
////        while (iterator.hasNext()) {
////            feature = iterator.next();
////            UnitModel unitModel = new UnitModel();
////            unitModel.setAddress(feature.getAttributes().get("ADDR").toString());
////            unitModel.setArcGisId(Integer.parseInt(feature.getAttributes().get(Config.ARCGIS_UNITID).toString()));
//////            unitModel.setCertificateId();
//////            unitModel.setCheckResultId();//保密检查结果表的外键id
//////            unitModel.setHolderId();//持证人表的外键id
////            unitModel.setHYLB_CODE(feature.getAttributes().get("HYLB_CODE").toString());//行业类别_代码
////            unitModel.setHYLB_NAME(feature.getAttributes().get("HYLB_NAME").toString());//行业类别_名称
////            unitModel.setLocation(feature.getGeometry().getExtent().getCenter());
////            unitModel.setLXR(feature.getAttributes().get("LXR").toString());//联系人
////            unitModel.setLXSJ(feature.getAttributes().get("LXSJ").toString());//联系手机
////            unitModel.setName(feature.getAttributes().get("NAME").toString());//公司名称
////            unitModel.setQYDM(feature.getAttributes().get("QYDM").toString());//企业代码
////            unitModel.setYZBM(feature.getAttributes().get("YZBM").toString());//邮政编码
////            unitModel.setZCD_CODE(feature.getAttributes().get("ZCD_CODE").toString());//注册地_代码
////            unitModel.setZCD_NAME(feature.getAttributes().get("ZCD_NAME").toString());//注册地_名称
////            unitModel.setZJDH(feature.getAttributes().get("ZJDH").toString());//座机电话
////            unitModel.setGeometry(feature.getGeometry());
////            unitModelList.add(unitModel);
////        }
//
//    }
//
//    private void dismissProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
//    }
//
//    private GraphicsOverlay addGraphicsOverlay(MapView mapView) {
//        //create the graphics overlay
//        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
//        //add the overlay to the map view
//        mapView.getGraphicsOverlays().add(graphicsOverlay);
//        return graphicsOverlay;
//    }
//
//    private void initAllFeatureQueryResult() {
//        if (mAllFeatureQueryResult == null) {
//            QueryParameters query = new QueryParameters();
//            query.setWhereClause("1=1");
//            final ListenableFuture<FeatureQueryResult> future = mServiceFeatureTable.queryFeaturesAsync(query);
//            // add done loading listener to fire when the selection returns
//            future.addDoneListener(() -> {
//                try {
//                    mAllFeatureQueryResult = future.get();
//                    for (Feature feature : mAllFeatureQueryResult) {
//                        if (feature.getAttributes().get("name") == null) {
//                            LogUtil.d("it's null:" + feature.getAttributes());
//                            continue;
//                        }
//                        TextSymbol bassRockSymbol = new TextSymbol(10, feature.getAttributes().get("name").toString(), mContext.getResources().getColor(R.color.colorPrimary), TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.BOTTOM);
//                        Graphic bassRockGraphic = new Graphic(feature.getGeometry(), bassRockSymbol);
//                        graphicsOverlay.getGraphics().add(bassRockGraphic);
//                        LogUtil.e("showAll->" + feature.getAttributes().get("id") + "," + feature.getAttributes().get("name"));
//                    }
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            });
//
//        }
//    }
//
//    private DispatchFragmentActionsCreator mDispatchFragmentActionsCreator;
//    private DispatchFragmentStore mDispatchFragmentStore;
//
//    @Override
//    protected Store initActionsCreatorAndStore() {
//        mDispatchFragmentActionsCreator = new DispatchFragmentActionsCreator(mDispatcher);
//        mDispatchFragmentStore = new DispatchFragmentStore(mDispatcher);
//        return mDispatchFragmentStore;
//    }
//
//    @Override
//    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {
//        switch (storeChangeEvent.getAction().getType()) {
//            case DispatchFragmentAction.GET_SIFT_ZCD_DATA:
////                unitZCDAdapter.notifyDataSetChanged();
//                break;
//            case DispatchFragmentAction.ZCD_ADAPTER_ON_ITEM_CLICK:
////                unitZCDAdapter.notifyDataSetChanged();
//                break;
//            case DispatchFragmentAction.GET_SIFT_LB_DATA:
////                unitLBAdapter.notifyDataSetChanged();
//                break;
//            case DispatchFragmentAction.LB_ADAPTER_ON_ITEM_CLICK:
////                unitLBAdapter.notifyDataSetChanged();
//                break;
//            case DispatchFragmentAction.BUILD_SQL:
//                //开始搜索
////                searchGeometryOrSql(mDispatchFragmentStore.getSql());
////                LogUtil.e("sql--->" + mDispatchFragmentStore.getSql());
////                dismissProgressDialog();
////                if (mDispatchFragmentStore.getOwnSMCHResultItemList().size() <= 0) {
////                    ToastUtil.showLong("未查询到任何信息");
////                    return;
////                }
//                searchGeometryOrSql(mDispatchFragmentStore.getSql());
//                DialogUtil.getInstance().dismiss();
//                break;
//            case DispatchFragmentAction.NO_DATA:
//                dismissProgressDialog();
//                break;
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mSubscriptionAction != null && !mSubscriptionAction.isUnsubscribed()) {
//            mSubscriptionAction.unsubscribe();
//        }
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        LogUtil.e("dispatchFragment->hidden=" + hidden);
//        if (hidden) {
////            mMapView.setVisibility(View.GONE);
//        } else {
////            mMapView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        int month = monthOfYear + 1;
//        if (isFirst) {
//            CGFFDate = year + "-" + month + "-" + dayOfMonth;
//            calendar1Content.setText(CGFFDate);
//        } else {
//            CGFTDate = year + "-" + month + "-" + dayOfMonth;
//            String dateTime = CGFTDate;
//
//            Calendar c = Calendar.getInstance();
//
//            try {
//                c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateTime));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            long DateStringXZ = c.getTimeInMillis();
//            Date date = new Date();
//            long DateStringNow = date.getTime();
//            if (DateStringNow >= DateStringXZ) {
//                calendar2Content.setText(CGFTDate);
//            } else {
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                String dateString = formatter.format(date);
//                calendar2Content.setText(dateString);
//            }
//        }
//    }
//
//    @Override
//    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//
//    }
//
//    private class MySketchGraphicsOverlayEventListener implements SketchGraphicsOverlayEventListener {
//
//        @Override
//        public void onUndoStateChanged(boolean undoEnabled) {
//            // Set the undo button's enabled/disabled state based on the event boolean
////            mUndoButton.setEnabled(undoEnabled);
////            mUndoButton.setClickable(undoEnabled);
//        }
//
//        @Override
//        public void onRedoStateChanged(boolean redoEnabled) {
//            // Set the redo button's enabled/disabled state based on the event boolean
////            mRedoButton.setEnabled(redoEnabled);
////            mRedoButton.setClickable(redoEnabled);
//        }
//
//        @Override
//        public void onClearStateChanged(boolean clearEnabled) {
//            // Set the clear button's enabled/disabled state based on the event boolean
////            mClearButton.setEnabled(clearEnabled);
////            mClearButton.setClickable(clearEnabled);
//        }
//
//        @Override
//        public void onDrawingFinished() {
//            // Reset the selected state of the drawing buttons when a drawing is finished
//        }
//    }
//}
