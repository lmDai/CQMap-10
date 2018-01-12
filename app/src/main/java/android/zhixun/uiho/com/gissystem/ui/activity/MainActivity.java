package android.zhixun.uiho.com.gissystem.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.actions.DispatchFragmentActionTemp;
import android.zhixun.uiho.com.gissystem.flux.actions.MainActivityAction;
import android.zhixun.uiho.com.gissystem.flux.actions.UnitFragmentActionTemp;
import android.zhixun.uiho.com.gissystem.flux.custom.PushTypeId;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.OrderModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.TempSortItemModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.UserModel;
import android.zhixun.uiho.com.gissystem.flux.stores.DispatchFragmentStore;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.ui.adapter.MainBottomAdapter;
import android.zhixun.uiho.com.gissystem.ui.fragment.CheckFragment;
import android.zhixun.uiho.com.gissystem.ui.fragment.DirectoryFragment;
import android.zhixun.uiho.com.gissystem.ui.fragment.DispatchFragment;
import android.zhixun.uiho.com.gissystem.ui.fragment.UnitFragment;
import android.zhixun.uiho.com.gissystem.ui.itemY.OwnSMCHResultItem;
import android.zhixun.uiho.com.gissystem.ui.widget.DividerItemDecoration;
import android.zhixun.uiho.com.gissystem.ui.widget.VerticalRecyclerView;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.adapter.TreeRecyclerViewAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.TreeAdapterItem;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yibogame.app.RxBus;
import com.yibogame.flux.stores.Store;
import com.yibogame.ui.widget.ObjectAnimWrapper;
import com.yibogame.util.DeviceUtil;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

import static com.yibogame.app.BaseApplication.context;
import static com.yibogame.util.DeviceUtil.getScreenSize;

public class MainActivity extends BaseActivityWithStatusBar implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFragmentManager;
    private int selectedIndex;
    private RadioGroup rgBottomRadioNavigation;
    private LinearLayout llLayout, llLayoutContainer;
    private AppCompatImageView acivLayout;
    private View rlBlank;
    protected Subscription mSubscriptionEmptyAction;
    private TextView tvResultTip;

    private int mState = GONE;
    private static final int GONE = 0, MIN = 1, HALF = 2, FULL_SCREEN = 3;
    private LinearLayout llTitle;
    private ObjectAnimWrapper objectAnimWrapper;
    TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
    float actionbarHeight = actionbarSizeTypedArray.getDimensionPixelSize(0, 0);
    private VerticalRecyclerView mRecyclerView;
    private List<CompanyDetailModel> listCompany = new ArrayList<>();
    private List<OwnSMCHResultItem> listUnitDispatchForBottomRecyclerView = new ArrayList<>();
    private MainBottomAdapter mainBottomAdapter;
    private TreeRecyclerViewAdapter mTreeRecyclerViewAdapter;
    //    private List<UnitItem> mUnitItemList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int scrollToPosition;
    private DaoSession mDaoSession;
    private DispatchFragmentStore mDispatchFragmentStore;

    private DrawerLayout mDrawerLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
//        ArrayList<TreeAdapterItem> treeBeen1 = new ArrayList<>();//一级
//        List<CityBean> cityBeen = JSON.parseArray(getResources().getString(R.string.location), CityBean.class);
//        for (int i = 0; i < cityBeen.size(); i++) {
//            treeBeen1.add(new OneItem(cityBeen.get(i)));
//        }
//        recyclerView.setAdapter(new TreeRecyclerViewAdapter<>(this, treeBeen1));


        mDaoSession = ((MyBaseApplication) getApplication()).getDaoSession();

//        CRActiveUserModelDao crActiveUserModelDao = mDaoSession.getCRActiveUserModelDao();
//        crActiveUserModelDao.queryBuilder().limit(1).orderDesc(CRActiveUserModelDao.Properties.Id);
//        CRActiveUserModel crActiveUserModel = crActiveUserModelDao.queryBuilder().build().unique();
//        ToastUtil.showLong(crActiveUserModel.getNickname() + "," + crActiveUserModel.getUserId() +
// "," + crActiveUserModel.getUserUnitId());


        actionbarSizeTypedArray.recycle();
        initViews();
        initFragments(savedInstanceState);
        mSubscriptionEmptyAction = RxBus.getInstance().toObservable(MainActivityAction.class)
                .subscribe(new Action1<MainActivityAction>() {
                    @Override
                    public void call(MainActivityAction mainActivityAction) {
                        switch (mainActivityAction.getAction()) {
                            case MainActivityAction.ANIM_TO_HALF:
                                animToHalf();
                                break;
                            case MainActivityAction.CLEAR_TEXT:
                                animToGone();
                                break;
                            case MainActivityAction.ACTION_REFRESH_DATA:
                                listCompany.clear();
                                listCompany.addAll(mainActivityAction.getList());
                                if (!(mRecyclerView.getWrapAdapter().getAdapter() instanceof MainBottomAdapter)) {
                                    mRecyclerView.setAdapter(mainBottomAdapter);
                                } else {
                                    mainBottomAdapter.notifyDataSetChanged();
                                }

                                animToHalf();
                                break;
                            case MainActivityAction.ACTION_REFRESH_DATA_DISPATCH:
//                        buildUnitListFromAchievementList(mainActivityAction, new IOnComplete() {
//                            @Override
//                            public void onComplete() {
//                                if (!(mRecyclerView.getWrapAdapter().getAdapter() instanceof TreeRecyclerViewAdapter)) {
//                                    mRecyclerView.setAdapter(mTreeRecyclerViewAdapter);
//                                } else {
//                                    mTreeRecyclerViewAdapter.notifyDataSetChanged();
//                                }
//                                animToHalf();
//                            }
//
//                            @Override
//                            public void onError(Throwable throwable) {
//
//                            }
//                        });
                                LogUtil.d("askldjflasjflasjdfl;asjklfjas;ldfjklaskjf");
                                for (Object object : mainActivityAction.getList()) {
                                    OwnSMCHResultItem ownSMCHResultItem = (OwnSMCHResultItem) object;
                                    LogUtil.i("---------->" + ownSMCHResultItem.getData().getCompanyName());
                                }
                                listUnitDispatchForBottomRecyclerView.addAll(mainActivityAction.getList());
                                if (!(mRecyclerView.getWrapAdapter().getAdapter() instanceof TreeRecyclerViewAdapter)) {
                                    mRecyclerView.setAdapter(mTreeRecyclerViewAdapter);
                                } else {
                                    mTreeRecyclerViewAdapter.notifyDataSetChanged();
                                }
                                animToHalf();
                                break;
                            case MainActivityAction.ACTION_SELECT_ITEM://当选中了某个条目
//                                boolean listUnitContainFlag = false;
//                                for (int i = 0; i < listCompany.size(); i++) {
//                                    if (listCompany.get(i).getGeometry().getExtent().getCenter()
//                                            .equals(mainActivityAction.getFeature().getGeometry()
//                                                    .getExtent().getCenter())) {
//                                        listUnitContainFlag = true;
//                                        animToHalf();
//                                        Observable.just(i)
//                                                .delay(0, TimeUnit.MILLISECONDS)
//                                                .subscribeOn(Schedulers.io())
//                                                .observeOn(AndroidSchedulers.mainThread())
//                                                .doOnNext(new Action1<Integer>() {
//                                                    @Override
//                                                    public void call(Integer integer) {
//                                                        scrollToPosition = integer + mRecyclerView.getWrapAdapter().getHeadersCount();
//                                                        if (scrollToPosition != 0) {
//                                                            linearLayoutManager.scrollToPositionWithOffset(scrollToPosition, 0);
//                                                        }
//                                                    }
//                                                })
//                                                .delay(0, TimeUnit.MILLISECONDS)
//                                                .subscribeOn(Schedulers.io())
//                                                .observeOn(AndroidSchedulers.mainThread())
//                                                .subscribe(new Subscriber<Integer>() {
//                                                    @Override
//                                                    public void onCompleted() {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onError(Throwable e) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onNext(Integer integer) {
//                                                        ObjectAnimator anim = ObjectAnimator
//                                                                .ofObject(linearLayoutManager
//                                                                                .findViewByPosition(scrollToPosition),
//                                                                        "backgroundColor", new ArgbEvaluator(),
//                                                                        getResources().getColor(R.color.colorPrimaryLight),
//                                                                        Color.WHITE, getResources().getColor(R.color.colorPrimaryLight), Color.WHITE);
//                                                        anim.setDuration(2000);
//                                                        anim.start();
////                                        ToastUtil.showShort("已滚动");
//                                                    }
//                                                });
//
//                                        break;
//                                    }
//
//                                }
//                                if (!listUnitContainFlag) {
//                                    MyBaseApplication myBaseApplication = (MyBaseApplication) getApplication();
////                            int arcGisId = Integer.parseInt(mainActivityAction.getFeature().getAttributes()
//// .get(Config.ARCGIS_UNITID).toString());
//                                    Object o = mainActivityAction.getFeature()
//                                            .getAttributes().get(Config.ARCGIS_UNITID);
////                                    if (o == null) {
////                                        o = mainActivityAction.getFeature()
////                                                .getAttributes().get(Config.FRUIT_ID);
////                                    }
//                                    if (o == null) return;
//                                    int companyId = Integer.parseInt(o.toString());
////                            UnitModelDao unitModelDao = mDaoSession.getUnitModelDao();
////                            myBaseApplication.setUnitModel(unitModelDao.queryBuilder().where(UnitModelDao.Properties.ArcGisId.eq(arcGisId)).unique());
//                                    APIService.getInstance().getCompanyDetailObservable(String.valueOf(companyId))
//                                            .subscribeOn(Schedulers.io())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                            .subscribe(new Subscriber<CompanyDetailModel>() {
//                                                @Override
//                                                public void onCompleted() {
//
//                                                }
//
//                                                @Override
//                                                public void onError(Throwable e) {
//                                                    ToastUtil.showShort("暂无数据");
//                                                }
//
//                                                @Override
//                                                public void onNext(CompanyDetailModel companyDetailModel) {
//                                                    myBaseApplication.setCompanyDetailModel(companyDetailModel);
//                                                    Intent intent = new Intent(MainActivity.this, UnitDetailActivity.class);
//                                                    startActivity(intent);
//                                                }
//                                            });
//                                }
                                break;

                            case MainActivityAction.ACTION_SELECT_ITEM_DISPATCH://成果分发条目点击
//                        ToastUtil.showShort("点击已被注释");
//                        buildUnitListFromAchievementList(mainActivityAction, new IOnComplete() {
//                            @Override
//                            public void onComplete() {
//                                boolean isShowListFlag = false;
//                                for (int i = 0; i < listUnitDispatchForBottomRecyclerView.size(); i++) {
//                                    isShowListFlag = true;
//                                    CompanyDetailModel unitModel = listUnitDispatchForBottomRecyclerView.get(i);
//                                    if (unitModel.getGeometry() == null) {
//                                        continue;
//                                    }
//                                    if (unitModel.getGeometry().getExtent().getCenter().equals(mainActivityAction.getFeature().getGeometry().getExtent().getCenter())) {
//                                        animToHalf();
//                                        Observable.just(i)
//                                                .delay(0, TimeUnit.MILLISECONDS)
//                                                .subscribeOn(Schedulers.io())
//                                                .observeOn(AndroidSchedulers.mainThread())
//                                                .doOnNext(new Action1<Integer>() {
//                                                    @Override
//                                                    public void call(Integer integer) {
//                                                        scrollToPosition = integer + mRecyclerView.getWrapAdapter().getHeadersCount();
//                                                        if (scrollToPosition != 0) {
//                                                            linearLayoutManager.scrollToPositionWithOffset(scrollToPosition, 0);
//                                                        }
//                                                    }
//                                                })
//                                                .delay(0, TimeUnit.MILLISECONDS)
//                                                .subscribeOn(Schedulers.io())
//                                                .observeOn(AndroidSchedulers.mainThread())
//                                                .subscribe(new Subscriber<Integer>() {
//                                                    @Override
//                                                    public void onCompleted() {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onError(Throwable e) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onNext(Integer integer) {
//                                                        ObjectAnimator anim = ObjectAnimator.ofObject(linearLayoutManager.findViewByPosition(scrollToPosition), "backgroundColor", new ArgbEvaluator(), getResources().getColor(R.color.colorPrimaryLight), Color.WHITE, getResources().getColor(R.color.colorPrimaryLight), Color.WHITE);
//                                                        anim.setDuration(2000);
//                                                        anim.start();
////                                        ToastUtil.showShort("已滚动");
//                                                    }
//                                                });
//
//                                        break;
//                                    }
//                                }
//
//                                if (!isShowListFlag) {
//                                    MyBaseApplication myBaseApplication = (MyBaseApplication) getApplication();
////                                    int arcGisId = Integer.parseInt(mainActivityAction.getFeature().getAttributes().get(Config.ARCGIS_UNITID).toString());
//                                    int companyId = Integer.parseInt(mainActivityAction.getFeature().getAttributes().get(Config.ARCGIS_UNITID).toString());
////                                    UnitModelDao unitModelDao = mDaoSession.getUnitModelDao();
////                                    UnitModel unitModel = unitModelDao.queryBuilder().where(UnitModelDao.Properties.ArcGisId.eq(arcGisId)).unique();
//                                    APIService.getInstance().getCompanyDetailObservable(String.valueOf(companyId))
//                                            .subscribeOn(Schedulers.io())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                            .subscribe(new Subscriber<CompanyDetailModel>() {
//                                                @Override
//                                                public void onCompleted() {
//
//                                                }
//
//                                                @Override
//                                                public void onError(Throwable e) {
//
//                                                }
//
//                                                @Override
//                                                public void onNext(CompanyDetailModel companyDetailModel) {
//                                                    myBaseApplication.setCompanyDetailModel(companyDetailModel);
//                                                    Intent intent = new Intent(MainActivity.this, OwnSecretResultActivity.class);
//                                                    intent.putExtra("company", (long) companyDetailModel.getCompanyId());
//                                                    startActivity(intent);
//                                                }
//                                            });
////                                    myBaseApplication.setUnitModel(unitModel);
//
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable throwable) {
//
//                            }
//                        });
                                break;
                            case MainActivityAction.ACTION_GET_UNIT_ID:
                                //这个没走RxBus.send了，所以这里不会收到了，以后再来删
                        }

                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            finish();
            return false;
        }
        Intent intent = new Intent();
//        intent.setClass(this, ResultDeclareMessageActivity.class);
        switch (item.getItemId()) {
            case R.id.menu_resultDeclareMessage://成果申请消息
                intent.setClass(this, ResultDeclareMessageActivity.class);
                intent.putExtra(MainActivity.DATA, PushTypeId.RESULT_DECLAR_MESSAGE);
                break;
            case R.id.menu_prepareCheckMessage://预审消息
                intent.setClass(this, ResultDeclareMessageActivity.class);
                intent.putExtra(MainActivity.DATA, PushTypeId.PREPARE_MESSAGE);
                break;
            case R.id.menu_resultFolderUpdateMessage://结果目录更新消息
                intent.setClass(this, ResultDeclareMessageActivity.class);
                intent.putExtra(MainActivity.DATA, PushTypeId.RESULT_FOLDER_UPDATE_MESSAGE);
                break;
            case R.id.menu_secrecyInspectMessage://保密检查消息
                intent.setClass(this, ResultDeclareMessageActivity.class);
                intent.putExtra(MainActivity.DATA, PushTypeId.SECRECY_INSPECT_MESSAGE);
                break;
            case R.id.menu_instructions://使用说明
                intent.setClass(this, InstructionsActivity.class);
                break;
        }
        startActivity(intent);
        mDrawerLayout.closeDrawer(Gravity.START);
        return false;
    }

    /***
     * 这个接口设计得很奇怪。。。
     */
    private interface IOnComplete {
        void onComplete();

        void onError(Throwable throwable);
    }

    private void buildUnitListFromAchievementList(MainActivityAction mainActivityAction, IOnComplete iOnComplete) {
        ToastUtil.showShort("还未做，已注释掉了");
        listUnitDispatchForBottomRecyclerView.clear();
        listUnitDispatchForBottomRecyclerView.addAll(mainActivityAction.getList());
//        mUnitItemList.clear();
//        List<AchievementModel> achievementModelList = mainActivityAction.getList();
//        Observable.from(achievementModelList)
//                .flatMap(new Func1<AchievementModel, Observable<CompanyDetailModel>>() {
//                    @Override
//                    public Observable<CompanyDetailModel> call(AchievementModel achievementModel) {
//                        mUnitItemList.add(new UnitItem(achievementModel, mDaoSession));
//                        return APIService.getInstance().getCompanyDetailObservable(achievementModel.getUnitKey().toString());
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<CompanyDetailModel>() {
//                    @Override
//                    public void onCompleted() {
//                        iOnComplete.onComplete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        iOnComplete.onError(e);
//                    }
//
//                    @Override
//                    public void onNext(CompanyDetailModel companyDetailModel) {
//                        listUnitDispatchForBottomRecyclerView.add(companyDetailModel);
//                    }
//                });
    }

    /***
     * 初始化Views
     */
    private void initViews() {
        rgBottomRadioNavigation = (RadioGroup) findViewById(R.id.rg_bottom_radio_navigation);
        mRecyclerView = (VerticalRecyclerView) findViewById(R.id.recyclerViewUnit);
//        //添加分割线
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST);
//        dividerItemDecoration.setShowFirstLine(true);
//        dividerItemDecoration.setShowLastLine(true);
//        mRecyclerView.addItemDecoration(dividerItemDecoration);
        rgBottomRadioNavigation.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_tab_unit:
                    selectedIndex = 0;
//                    showFragment(UnitFragment.class);
                    showFagmentByReplace(UnitFragment.class);
                    break;
                case R.id.rb_tab_dispatch:
                    selectedIndex = 1;
//                    showFragment(DispatchFragment.class);
                    showFagmentByReplace(DispatchFragment.class);
                    break;
                case R.id.rb_tab_directory:
                    selectedIndex = 2;
//                    showFragment(DirectoryFragment.class);
                    showFagmentByReplace(DirectoryFragment.class);
                    break;
                case R.id.rb_tab_check:
                    selectedIndex = 3;
//                    showFragment(CheckFragment.class);
                    showFagmentByReplace(CheckFragment.class);
                    break;
            }
        });
        llTitle = (LinearLayout) findViewById(R.id.ll_title);
        llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animToHalf();
            }
        });
        findViewById(R.id.aiv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animToMin();
            }
        });
        objectAnimWrapper = new ObjectAnimWrapper(llTitle);
        objectAnimWrapper.setTopMargin((int) (actionbarHeight * -1));
        llTitle.setVisibility(View.VISIBLE);
        //初始化layout层
        llLayout = (LinearLayout) findViewById(R.id.ll_layout);
        llLayoutContainer = (LinearLayout) findViewById(R.id.ll_layout_container);
        acivLayout = (AppCompatImageView) findViewById(R.id.aiv_layout);
        rlBlank = findViewById(R.id.rl_blank);
        rlBlank.setOnClickListener(view -> animToMin());
        rlBlank.setClickable(false);
        tvResultTip = (TextView) findViewById(R.id.tv_result_tip);
        tvResultTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mState == HALF) {
                    animToFullScreen();
                } else {
                    animToHalf();
                }
            }
        });
        acivLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mState == HALF) {
                    animToFullScreen();
                } else {
                    animToHalf();
                }
            }
        });

        mainBottomAdapter = new MainBottomAdapter(MainActivity.this, listCompany);
        mainBottomAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                try {
//                    Envelope envelope = listCompany.get((position - mRecyclerView.getWrapAdapter().getHeadersCount()) > 0 ? position - mRecyclerView.getWrapAdapter().getHeadersCount() : 0).getGeometry().getExtent();
//                    //RxBus.send(envelope)到UnitFragment来实现点击的效果
//                    RxBus.getInstance().send(new UnitFragmentActionTemp<>(UnitFragmentActionTemp.ACTION_SYNC_ENVELOPE, envelope));
//                } catch (Exception e) {
//                    ToastUtil.showShort("无法定位该点，数据不匹配~");
//                    e.printStackTrace();
//                }
            }
        });
        mainBottomAdapter.setOnItemClickListenerDetail(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, UnitDetailActivity.class);
                CompanyDetailModel companyDetailModel = listCompany.get((position - mRecyclerView.getWrapAdapter().getHeadersCount()) > 0 ? position - mRecyclerView.getWrapAdapter().getHeadersCount() : 0);
//                intent.putExtra("unitModel", unitModel);
                MyBaseApplication myBaseApplication = (MyBaseApplication) getApplication();
//                myBaseApplication.setUnitModel(unitModel);
                myBaseApplication.setCompanyDetailModel(companyDetailModel);
                startActivity(intent);
            }
        });
//        mUnitItemList.clear();
        mTreeRecyclerViewAdapter = new TreeRecyclerViewAdapter(MainActivity.this,
                listUnitDispatchForBottomRecyclerView, 1);
        mTreeRecyclerViewAdapter.setOnTreeDetailClickListener((node, position, sonItem, mListMap, tat) -> {

            Intent intent = new Intent(MainActivity.this, ResultDispatchDetailActivity.class);
            OrderModel achievementModel = (OrderModel) node.getData();

            intent.putExtra("companyName", achievementModel.getCompanyName());
            intent.putExtra("company", (long) achievementModel.getCompanyId());
            intent.putExtra("HandoutId", achievementModel.getHandoutId());
            startActivity(intent);

//                LogUtil.e("----------position" + position + "-------sonItem" + sonItem + "--------mListMap" + mListMap + "---------tat" + tat);
//                try {
//                    Intent intent = new Intent(MainActivity.this, ResultDispatchDetailActivity.class);
//                    AchievementModel achievementModel = (AchievementModel) node.getData();
//                    intent.putExtra("company", achievementModel.getUnitKey().longValue());
//                    intent.putExtra("achievementModelId", achievementModel.getId().longValue());
//                    startActivity(intent);
////                    RxBus.getInstance().send(new DispatchFragmentActionTemp<>(DispatchFragmentActionTemp.ACTION_GET_ITEM_DATA_BY_POSITION, chaievementModel.getUnitKey()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                int positionM = 0;
//                int item = 0;
//                if (position == 0) {
//                    positionM = 0;
//                } else if (position > 0) {
//                    for (int i = 0; i < position; i++) {
//                        int a = mListMap.get(i);
//                        item += a;
//
//                    }
//                    positionM = position - item;
//                }
//
//                RxBus.getInstance().send(new DispatchFragmentActionTemp<>(DispatchFragmentActionTemp.ACTION_GET_ITEM_DATA_BY_POSITION, positionM));
        });
        mTreeRecyclerViewAdapter.setOnTreeItemClickListener(new TreeRecyclerViewAdapter.OnTreeItemClickListener() {
            @Override
            public void onClick(TreeAdapterItem node, int position) {
                if (node.getAllChilds() == null || node.getAllChilds().size() == 0) {//点击的为item
                    if (node.getData() instanceof TempSortItemModel) {//点击的为数据
                        LogUtil.e("点击的第" + position + "个item" + ",数据为：" + node.getData().toString());
                        animToHalf();
                        RxBus.getInstance()
                                .send(new DispatchFragmentActionTemp<>(DispatchFragmentActionTemp.ACTION_SHOW,
                                        node.getData(), position));
//                        if (node.getData() instanceof CGSortOneModel) {
//                            RxBus.getInstance().send(new DispatchFragmentActionTemp<>(DispatchFragmentActionTemp.ACTION_SHOW_ONE, node.getData()));
//                        } else {
//                            RxBus.getInstance().send(new DispatchFragmentActionTemp<>(DispatchFragmentActionTemp.ACTION_SHOW_TWO, node.getData()));
//                        }

                    } else {
                        LogUtil.e("点击的为标题");
                    }
                }
            }
        });

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.refreshComplete();
            }
        });
        mRecyclerView.setLoadingMoreEnabled(false);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        //添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        dividerItemDecoration.setShowLastLine(false);
        dividerItemDecoration.setShowFirstLine(false);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mainBottomAdapter);
        //
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);

        ImageView mIvAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.iv_avatar);
        TextView mTvUserName = mNavigationView.getHeaderView(0).findViewById(R.id.tv_userName);
        UserModel userModel = MyBaseApplication.getInstance().getUserModel();
//        mIvAvatar.setImageResource(R.mipmap.arrow_bottom);
        mTvUserName.setText(userModel.getName());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedIndex", selectedIndex);
    }

    /***
     * 初始化Fragments
     *
     * @param savedInstanceState
     */
    private void initFragments(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            createAndAddFragment(UnitFragment.class);
        } else {
            selectedIndex = savedInstanceState.getInt("selectedIndex");
            rgBottomRadioNavigation.check(rgBottomRadioNavigation.getChildAt(selectedIndex).getId());
            switch (selectedIndex) {
                case 0:
//                    showFragment(UnitFragment.class);
                    showFagmentByReplace(UnitFragment.class);
                    break;
                case 1:
                    showFagmentByReplace(DirectoryFragment.class);
//                    showFragment(DispatchFragment.class);
                    break;
                case 2:
//                    showFragment(DirectoryFragment.class);
                    showFagmentByReplace(DirectoryFragment.class);
                    break;
                case 3:
//                    showFragment(CheckFragment.class);
                    showFagmentByReplace(CheckFragment.class);
                    break;
            }

        }
    }

    /***
     * 展开的动画
     */
    private void animToHalf() {
        if (mState == HALF) {
            return;
        }
        rlBlank.setClickable(true);
        llLayout.measure(0, 0);
        //Layout
        ObjectAnimWrapper wrapperLayout = new ObjectAnimWrapper(llLayout);
        //acivLayout
        ObjectAnimWrapper wrapperACIVLayout = new ObjectAnimWrapper(acivLayout);
        ObjectAnimator animatorLayout, animatorACIVLayout = null;
        switch (mState) {
            case GONE:
                mainBottomAdapter.notifyDataSetChanged();
                animatorLayout = ObjectAnimator.ofInt(wrapperLayout, "topMargin",
                        0, (getScreenSize(this).y -
                                DeviceUtil.getStatusHeight(this)) / 3 * -1);
                break;
            case MIN:
                animatorLayout = ObjectAnimator.ofInt(wrapperLayout, "topMargin",
                        wrapperLayout.getTopMargin(), (getScreenSize(this).y -
                                DeviceUtil.getStatusHeight(this)) / 3 * -1);
                break;
            case FULL_SCREEN:
                animatorLayout = ObjectAnimator.ofInt(wrapperLayout, "topMargin",
                        wrapperLayout.getTopMargin(), (getScreenSize(this).y -
                                DeviceUtil.getStatusHeight(this)) / 3 * -1);
                animatorACIVLayout = ObjectAnimator.ofInt(wrapperACIVLayout, "height",
                        0, DeviceUtil.dip2px(this, 24));
                animatorACIVLayout.setDuration(300);
                animatorACIVLayout.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            default:
                return;
        }
        animatorLayout.setDuration(300);
        animatorLayout.setInterpolator(new AccelerateDecelerateInterpolator());
        //'查看结果'
        ObjectAnimWrapper wrapperTvResult = new ObjectAnimWrapper(tvResultTip);
        ObjectAnimator animatorTvResult;
        if (mState == MIN) {
            animatorTvResult = ObjectAnimator.ofInt(wrapperTvResult, "height",
                    DeviceUtil.dip2px(this, 32), 0);
            animatorTvResult.setDuration(200);
        } else {
            animatorTvResult = ObjectAnimator.ofInt(wrapperTvResult, "height", 0, 0);
            animatorTvResult.setDuration(0);
        }
        animatorTvResult.setInterpolator(new LinearInterpolator());
        //AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        if (animatorACIVLayout != null) {
            animatorSet.playTogether(animatorTvResult, animatorLayout, animatorACIVLayout);
        } else {
            animatorSet.playTogether(animatorTvResult, animatorLayout);
        }
        mState = HALF;
        RxBus.getInstance().send(new UnitFragmentActionTemp(UnitFragmentActionTemp.SHOW_SEARCH_BAR));
        animHideTitle();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                wrapperLayout.setTopMargin((getScreenSize(MainActivity.this).y -
                        DeviceUtil.getStatusHeight(MainActivity.this)) / 3 * -1);
                wrapperTvResult.setHeight(0);
                wrapperACIVLayout.setHeight(DeviceUtil.dip2px(MainActivity.this, 24));
                if (scrollToPosition != 0) {
                    linearLayoutManager.scrollToPositionWithOffset(scrollToPosition, 0);
                }
            }
        });
        animatorSet.start();
    }

    private void animToFullScreen() {
        if (mState == FULL_SCREEN) {
            return;
        }
        rlBlank.setClickable(false);
        //acivLayout
        ObjectAnimWrapper wrapperACIVLayout = new ObjectAnimWrapper(acivLayout);
        int maxHeight = (int) (DeviceUtil.getScreenSize(this).y -
                DeviceUtil.getStatusHeight(this) - actionbarHeight);
        ObjectAnimWrapper wrapperLayout = new ObjectAnimWrapper(llLayout);
        ObjectAnimator animatorLayout = ObjectAnimator.ofInt(wrapperLayout,
                "topMargin", wrapperLayout.getTopMargin(), maxHeight * -1);
//        LogUtil.e("wrapperLayout.getTopMargin()" + wrapperLayout.getTopMargin() + ",maxHeight=" + maxHeight);
        //acivLayout
        ObjectAnimator animatorACIVLayout = ObjectAnimator.ofInt(wrapperACIVLayout,
                "height", wrapperACIVLayout.getHeight(), 0);
        //AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animatorLayout, animatorACIVLayout);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                wrapperLayout.setTopMargin(maxHeight * -1);
                wrapperACIVLayout.setHeight(0);
            }
        });
        animatorSet.start();
        mState = FULL_SCREEN;
        RxBus.getInstance().send(new UnitFragmentActionTemp(UnitFragmentActionTemp.HIDE_SEARCH_BAR));
        animShowTitle();
        RxBus.getInstance().send(new DispatchFragmentActionTemp<>(DispatchFragmentActionTemp.ACTION_CLEAR, null));
    }

    /***
     * 关闭的动画
     */
    private void animToGone() {
        if (mState == GONE) {
            return;
        }
        listCompany.clear();
        listUnitDispatchForBottomRecyclerView.clear();
        rlBlank.setClickable(false);
        ObjectAnimWrapper objectAnimWrapper = new ObjectAnimWrapper(llLayout);
//        objectAnimWrapper.setTopMargin(DeviceUtil.getScreenSize(this).y);
        ObjectAnimator objectAnimator;
        objectAnimator = ObjectAnimator.ofInt(objectAnimWrapper, "topMargin",
                objectAnimWrapper.getTopMargin(), 0);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        objectAnimator.start();
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimWrapper wrapperACIVLayout = null;
        if (mState == FULL_SCREEN) {
            wrapperACIVLayout = new ObjectAnimWrapper(acivLayout);
            ObjectAnimator animatorACIVLayout = ObjectAnimator.ofInt(wrapperACIVLayout,
                    "height", 0, DeviceUtil.dip2px(this, 24));
            animatorACIVLayout.setDuration(300);
            animatorACIVLayout.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.playTogether(objectAnimator, animatorACIVLayout);
        } else {
            animatorSet.playTogether(objectAnimator);
        }
        ObjectAnimWrapper finalWrapperACIVLayout = wrapperACIVLayout;
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimWrapper.setTopMargin(0);
                if (finalWrapperACIVLayout != null) {
                    finalWrapperACIVLayout.setHeight(DeviceUtil.dip2px(MainActivity.this, 24));
                }
            }
        });
        animatorSet.start();
        mState = GONE;
        RxBus.getInstance().send(new UnitFragmentActionTemp(UnitFragmentActionTemp.SHOW_SEARCH_BAR));
        RxBus.getInstance().send(new DispatchFragmentActionTemp(DispatchFragmentActionTemp.ACTION_HIDE_DETAIL));
        animHideTitle();
        scrollToPosition = 0;
    }

    private void animToMin() {
        if (mState == MIN) {
            return;
        }
        rlBlank.setClickable(false);
        //Layout
        ObjectAnimWrapper wrapperLayout = new ObjectAnimWrapper(llLayout);
        ObjectAnimator animatorLayout = ObjectAnimator.ofInt(wrapperLayout, "topMargin",
                (getScreenSize(this).y - DeviceUtil.getStatusHeight(this)) / 3 * -1,
                DeviceUtil.dip2px(this, -56));
        animatorLayout.setDuration(300);
        animatorLayout.setInterpolator(new AccelerateDecelerateInterpolator());
        //'查看结果'
        ObjectAnimWrapper wrapperTvResult = new ObjectAnimWrapper(tvResultTip);
        ObjectAnimator animatorTvResult = ObjectAnimator.ofInt(wrapperTvResult,
                "height", 0, DeviceUtil.dip2px(this, 32));
        animatorTvResult.setDuration(200);
        animatorTvResult.setInterpolator(new LinearInterpolator());
        //AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimWrapper wrapperACIVLayout = null;
        if (mState == FULL_SCREEN) {
            wrapperACIVLayout = new ObjectAnimWrapper(acivLayout);
            ObjectAnimator animatorACIVLayout = ObjectAnimator.ofInt(wrapperACIVLayout,
                    "height", 0, DeviceUtil.dip2px(this, 24));
            animatorACIVLayout.setDuration(300);
            animatorACIVLayout.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.playTogether(animatorLayout, animatorTvResult, animatorACIVLayout);
        } else {
            animatorSet.playTogether(animatorLayout, animatorTvResult);
        }
        ObjectAnimWrapper finalWrapperACIVLayout = wrapperACIVLayout;
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                wrapperLayout.setTopMargin(DeviceUtil.dip2px(MainActivity.this, -56));
                wrapperTvResult.setHeight(DeviceUtil.dip2px(MainActivity.this, 32));
                if (finalWrapperACIVLayout != null) {
                    finalWrapperACIVLayout.setHeight(DeviceUtil
                            .dip2px(MainActivity.this, 24));
                }
            }
        });
        animatorSet.start();
        mState = MIN;
        RxBus.getInstance().send(new UnitFragmentActionTemp(UnitFragmentActionTemp.SHOW_SEARCH_BAR));
        animHideTitle();
    }


    /***
     * 动画方式隐藏导航栏
     */
    private void animHideTitle() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(objectAnimWrapper,
                "topMargin", objectAnimWrapper.getTopMargin(),
                llTitle.getMeasuredHeight() * -1);
        objectAnimator.setDuration(150);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimWrapper.setTopMargin(llTitle.getMeasuredHeight() * -1);
            }
        });
        objectAnimator.start();
    }

    /***
     * 动画方式显示导航栏
     */
    private void animShowTitle() {
        TextView tvTitle = (TextView) llTitle.findViewById(R.id.toolbar_title);
        tvTitle.setText("搜索结果");
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(objectAnimWrapper,
                "topMargin", llTitle.getMeasuredHeight() * -1, 0);
        objectAnimator.setDuration(150);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                objectAnimWrapper.setTopMargin(0);
            }
        });
        objectAnimator.start();
    }

    /***
     * 显示fragment
     *
     * @param clazz
     */
    private void showFragment(Class<? extends Fragment> clazz) {
        hideAllFragment();
        Fragment myBaseFragment = (Fragment) mFragmentManager.findFragmentByTag(clazz.getSimpleName());
        if (myBaseFragment == null) {
            myBaseFragment = createAndAddFragment(clazz);
        }
        mFragmentManager.beginTransaction().show(myBaseFragment).commit();
//        mFragmentManager.beginTransaction().attach(myBaseFragment).commit();
    }

    private android.support.v4.app.Fragment showFagmentByReplace(Class<? extends android.support.v4.app.Fragment> clazz) {
        if (mFragmentManager.findFragmentByTag(clazz.getSimpleName()) != null) {
            return (android.support.v4.app.Fragment) mFragmentManager.findFragmentByTag(clazz.getSimpleName());
        }
        try {
            android.support.v4.app.Fragment fragment = clazz.newInstance();
//            mFragmentManager.beginTransaction().add(R.id.contentContainer, myBaseFragment,
// myBaseFragment.getArguments().getString("name")).commit();
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.contentContainer, fragment,
                            fragment.getArguments().getString("name")).commit();
            return fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * 创建并且Add fragment
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    private Fragment createAndAddFragment(Class<? extends Fragment> clazz) {
        if (mFragmentManager.findFragmentByTag(clazz.getSimpleName()) != null) {
            return (Fragment) mFragmentManager.findFragmentByTag(clazz.getSimpleName());
        }
        try {
            Fragment myBaseFragment = clazz.newInstance();
            mFragmentManager.beginTransaction()
                    .add(R.id.contentContainer, myBaseFragment,
                    myBaseFragment.getArguments().getString("name"))
                    .commit();
            return myBaseFragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * 隐藏所有fragment
     */
    private void hideAllFragment() {
        String[] fragmentsClassNameArray = {UnitFragment.class.getSimpleName(),
                DirectoryFragment.class.getSimpleName(), DirectoryFragment.class.getSimpleName(),
                CheckFragment.class.getSimpleName()};
        for (String fragmentsClassName : fragmentsClassNameArray) {
            Fragment fragment = mFragmentManager.findFragmentByTag(fragmentsClassName);
            if (fragment != null) {
                mFragmentManager.beginTransaction().hide(fragment).commit();
//                mFragmentManager.beginTransaction().detach(fragment).commit();
            }
        }
    }

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscriptionEmptyAction != null && !mSubscriptionEmptyAction.isUnsubscribed()) {
            mSubscriptionEmptyAction.unsubscribe();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        } else if (mState != GONE) {
            animToGone();
            RxBus.getInstance().send(new UnitFragmentActionTemp<>(UnitFragmentActionTemp.ACTION_CLEAR));
        } else {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出！", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                super.onBackPressed();
            }
        }
    }

    //退出时的时间
    private long mExitTime;

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    public void showBottomNav(){
        rgBottomRadioNavigation.setVisibility(View.VISIBLE);
    }

    public void hideBottomNav(){
        rgBottomRadioNavigation.setVisibility(View.GONE);
    }

}
