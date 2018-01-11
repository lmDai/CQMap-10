//package android.zhixun.uiho.com.gissystem.flux.stores;
//
//import android.text.TextUtils;
//import android.zhixun.uiho.com.gissystem.app.Config;
//import android.zhixun.uiho.com.gissystem.flux.actions.UnitFragmentAction;
//import android.zhixun.uiho.com.gissystem.flux.models.AchievementModel;
//import android.zhixun.uiho.com.gissystem.flux.models.CGSortOneModel;
//import android.zhixun.uiho.com.gissystem.flux.models.CGSortTwoModel;
//import android.zhixun.uiho.com.gissystem.flux.models.UnitModel;
//import android.zhixun.uiho.com.gissystem.flux.models.api.AreaModel;
//import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
//import android.zhixun.uiho.com.gissystem.flux.models.api.IndustryCategoryModel;
//import android.zhixun.uiho.com.gissystem.greendao_gen.AchievementModelDao;
//import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortOneModelDao;
//import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortTwoModelDao;
//import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
//import android.zhixun.uiho.com.gissystem.greendao_gen.IndustryCategoryModelDao;
//import android.zhixun.uiho.com.gissystem.greendao_gen.UnitModelDao;
//import android.zhixun.uiho.com.gissystem.rest.APIService;
//
//import com.esri.arcgisruntime.data.Feature;
//import com.esri.arcgisruntime.data.FeatureQueryResult;
//import com.yibogame.app.DoOnSubscriber;
//import com.yibogame.flux.actions.Action;
//import com.yibogame.flux.dispatcher.Dispatcher;
//import com.yibogame.flux.stores.Store;
//import com.yibogame.util.DateUtil;
//import com.yibogame.util.DeviceUtil;
//import com.yibogame.util.LogUtil;
//import com.yibogame.util.ToastUtil;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import rx.Subscription;
//
///**
// * Created by parcool on 2016/12/16.
// */
//
//public class UnitFragmentStore extends Store {
//
//
//    private static final long serialVersionUID = -159790047162393360L;
//    private List<Integer> listHYLB_CODE = new ArrayList<>();
//    private List<Integer> listZCD_CODE = new ArrayList<>();
//    private DaoSession mDaoSession;
//    private List<AreaModel> areaModelList = new ArrayList<>();
//    private List<IndustryCategoryModel> industryCategoryModelList = new ArrayList<>();
//    private String sql = "";
//    private FeatureQueryResult mAllFeatureQueryResult = null;
//
//    public FeatureQueryResult getAllFeatureQueryResult() {
//        return mAllFeatureQueryResult;
//    }
//
//    public void setAllFeatureQueryResult(FeatureQueryResult mAllFeatureQueryResult) {
//        this.mAllFeatureQueryResult = mAllFeatureQueryResult;
//    }
//
//    public void setDaoSession(DaoSession mDaoSession) {
//        this.mDaoSession = mDaoSession;
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    public List<AreaModel> getAreaModelList() {
//        return areaModelList;
//    }
//
//    public List<IndustryCategoryModel> getIndustryCategoryModelList() {
//        return industryCategoryModelList;
//    }
//
//    public List<Integer> getListHYLB_CODE() {
//        return listHYLB_CODE;
//    }
//
//    public List<Integer> getListZCD_CODE() {
//        return listZCD_CODE;
//    }
//
//    public String getSql() {
//        return sql;
//    }
//
//    public UnitFragmentStore(Dispatcher dispatcher) {
//        super(dispatcher);
//    }
//
//    @Override
//    public void onAction(Action action) {
//        switch (action.getType()) {
//            case UnitFragmentAction.GET_SIFT_ZCD_DATA:
//                getSiftZCDData(action);
//                break;
//            case UnitFragmentAction.ZCD_ADAPTER_ON_ITEM_CLICK:
//                int positionZCD = (int) action.getData().get("position");
//                onZCDAdapterItemClick(action, positionZCD);
//                break;
//            case UnitFragmentAction.GET_SIFT_LB_DATA:
//                getSiftLBData(action);
//                break;
//            case UnitFragmentAction.LB_ADAPTER_ON_ITEM_CLICK:
//                int positionLB = (int) action.getData().get("position");
//                onLBAdapterItemClick(action, positionLB);
//                break;
//            case UnitFragmentAction.BUILD_SQL:
//                String searchStr = action.getData().get("searchStr").toString();
//                int industryCategoryId = (int) action.getData().get("industryCategoryId");
//                int areaId = (int) action.getData().get("areaId");
//                buildSql(action, searchStr, industryCategoryId, areaId);
//                break;
//            case UnitFragmentAction.ADD_UNIT_DATA:
//                addUnitData(action);
//                break;
//        }
//    }
//
//    private void buildSql(Action action, String searchStr, int industryCategoryId, int areaId) {
//        Map<Object, Object> map = new HashMap<>();
//        if (!TextUtils.isEmpty(searchStr)) {
//            map.put("key", searchStr);
//        }
//        if (industryCategoryId != -1) {
//            map.put("industryCategoryId", industryCategoryId);
//        }
//        if (areaId != -1) {
//            map.put("areaId", areaId);
//        }
//        APIService.getInstance().getCompanyList(map, new DoOnSubscriber<List<CompanyDetailModel>>() {
//            @Override
//            public void doOnSubscriber() {
//
//            }
//
//            @Override
//            public void onCompleted() {
//                StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//                emitStoreChange(storeChangeEvent);
//            }
//
//            @Override
//            public void onNext(List<CompanyDetailModel> companyModels) {
//                StringBuilder stringBuilder = new StringBuilder();
//                for (int i = 0; i < companyModels.size(); i++) {
////                    LogUtil.i(companyModels.get(i).getCompanyName()+","+companyModels.get(i).getOrganizationCode());
//                    stringBuilder.append(Config.ARCGIS_UNITID);
//                    stringBuilder.append(" = ");
//                    stringBuilder.append(companyModels.get(i).getCompanyId());
//                    if (i != companyModels.size() - 1) {
//                        stringBuilder.append(" or ");
//                    }
//                }
//                sql = stringBuilder.toString();
////                LogUtil.i(stringBuilder.toString());
////                String sql = "name like '%" + etSearch.getText().toString() + "%' or qydm like '%" + etString + "%'";
////                searchGeometryOrSql(stringBuilder.toString());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                action.setType(UnitFragmentAction.NO_DATA);
//                StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//                emitStoreChange(storeChangeEvent);
//                ToastUtil.showShort(e.getMessage());
//            }
//        });
//
//
////        if (!TextUtils.isEmpty(searchStr)) {
////            sql = "(name like '%" + searchStr + "%' or qydm like '%" + searchStr + "%')";
////        } else {
////            sql = "1=1";
////        }
////        //注册地
////        for (int i = 0; i < listZCD_CODE.size(); i++) {
////            if (i == 0) {
////                sql += " and (";
////            }
////            if (i < listZCD_CODE.size() - 1) {
////                sql += "zcd_code='" + listZCD_CODE.get(i) + "' or ";
////            } else {
////                sql += "zcd_code='" + listZCD_CODE.get(i) + "')";
////            }
////        }
////        //类别
////        for (int i = 0; i < listHYLB_CODE.size(); i++) {
////            if (i == 0) {
////                sql += " and (";
////            }
////            if (i < listHYLB_CODE.size() - 1) {
////                sql += "hylb_code='" + listHYLB_CODE.get(i) + "' or ";
////            } else {
////                sql += "hylb_code='" + listHYLB_CODE.get(i) + "')";
////            }
////        }
//
//    }
//
//    private void getSiftZCDData(Action action) {
//        Map<Object, Object> map = new HashMap<>();
//        Subscription subscription = APIService.getInstance().getAreaList(map, new DoOnSubscriber<List<AreaModel>>() {
//            @Override
//            public void doOnSubscriber() {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onNext(List<AreaModel> areaModels) {
//                areaModelList.clear();
//                AreaModel areaModel = new AreaModel();
//                areaModel.setAreaId(-1);
//                areaModel.setAreaName("全部");
//                areaModelList.add(areaModel);
//                areaModelList.addAll(areaModels);
//                //查询注册地
////        CQPrefectureModelDao cqPrefectureModelDao = mDaoSession.getCQPrefectureModelDao();
////        areaModelList.clear();
////        areaModelList.addAll(cqPrefectureModelDao.queryBuilder().build().list());
//                //如果注册地还没选，那就默认让它选择全部
//                if (listZCD_CODE.size() == 0) {
//                    for (AreaModel cqPrefectureModel : areaModelList) {
//                        cqPrefectureModel.setChecked(false);
//                    }
//                    areaModelList.get(0).setChecked(true);
//                } else {
//                    //如果注册地已经选择，那么选中它
//                    for (AreaModel cqPrefectureModel : areaModelList) {
//                        cqPrefectureModel.setChecked(false);
//                        for (Integer s : listZCD_CODE) {
//                            if (cqPrefectureModel.getAreaId() == s) {
//                                cqPrefectureModel.setChecked(true);
//                                break;
//                            }
//                        }
//                    }
//                }
//                StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//                emitStoreChange(storeChangeEvent);
//            }
//        });
//
//    }
//
//    private void getSiftLBData(Action action) {
//        Map<Object, Object> map = new HashMap<>();
//        Subscription subscription = APIService.getInstance().getIndustryCategoryList(map, new DoOnSubscriber<List<IndustryCategoryModel>>() {
//            @Override
//            public void doOnSubscriber() {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onNext(List<IndustryCategoryModel> industryCategoryModels) {
//                industryCategoryModelList.clear();
//                IndustryCategoryModel industryCategoryModelTemp = new IndustryCategoryModel();
//                industryCategoryModelTemp.setIndustryCategoryId(-1);
//                industryCategoryModelTemp.setIndustryCategoryName("全部");
//                industryCategoryModelList.add(industryCategoryModelTemp);
//                industryCategoryModelList.addAll(industryCategoryModels);
//                //如果类别还没选，那就默认让它选择全部
//                if (listHYLB_CODE.size() == 0) {
//                    for (IndustryCategoryModel industryCategoryModel : industryCategoryModelList) {
//                        industryCategoryModel.setChecked(false);
//                    }
//                    industryCategoryModelList.get(0).setChecked(true);
//                } else {
//                    //如果类别已经选择，那么选中它
//                    for (IndustryCategoryModel industryCategoryModel : industryCategoryModelList) {
//                        industryCategoryModel.setChecked(false);
//                        for (Integer s : listHYLB_CODE) {
//                            if (industryCategoryModel.getIndustryCategoryId() == s) {
//                                industryCategoryModel.setChecked(true);
//                                break;
//                            }
//                        }
//                    }
//                }
//                StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//                emitStoreChange(storeChangeEvent);
//            }
//        });
////        IndustryCategoryModelDao industryCategoryModelDao = mDaoSession.getIndustryCategoryModelDao();
////        industryCategoryModelList.clear();
////        industryCategoryModelList.addAll(industryCategoryModelDao.queryBuilder().build().list());
//    }
//
//
//    private void onZCDAdapterItemClick(Action action, int position) {
//        if (position == 0) {//如果选择的全部
//            //取消所有其他选择
//            for (AreaModel list : areaModelList) {
//                list.setChecked(false);
//            }
//            //让'全部'选中
//            areaModelList.get(0).setChecked(true);
//            listZCD_CODE.clear();
//        } else {//如果选择的不是'全部'
//            //让'全部'不选择，让当前这个取反
//            areaModelList.get(0).setChecked(false);
//            areaModelList.get(position).setChecked(!areaModelList.get(position).isChecked());
//            if (areaModelList.get(position).isChecked()) {
//                //赋值注册地代码
//                listZCD_CODE.add(areaModelList.get(position).getAreaId());
//            } else {
//                //删除注册地代码
//                listZCD_CODE.remove((Integer) areaModelList.get(position).getAreaId());
//            }
//        }
//
//        StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//        emitStoreChange(storeChangeEvent);
//    }
//
//    private void onLBAdapterItemClick(Action action, int position) {
//        if (position == 0) {//如果选择的全部
//            //取消所有其他选择
//            for (IndustryCategoryModel list : industryCategoryModelList) {
//                list.setChecked(false);
//            }
//            //让'全部'选中
//            industryCategoryModelList.get(0).setChecked(true);
//            listHYLB_CODE.clear();
//        } else {//如果选择的不是'全部'
//            //让'全部'不选择，让当前这个取反
//            industryCategoryModelList.get(0).setChecked(false);
//            industryCategoryModelList.get(position).setChecked(!industryCategoryModelList.get(position).isChecked());
//            if (industryCategoryModelList.get(position).isChecked()) {
//                //赋值类别代码
//                listHYLB_CODE.add(industryCategoryModelList.get(position).getIndustryCategoryId());
//            } else {
//                //删除注册地代码
//                listHYLB_CODE.remove((Integer) industryCategoryModelList.get(position).getIndustryCategoryId());
//            }
//        }
//        StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//        emitStoreChange(storeChangeEvent);
//    }
//
//
//    /***
//     * 添加单位信息
//     *
//     * @param action
//     */
//    private void addUnitData(Action action) {
//        if (!mDaoSession.loadAll(UnitModel.class).isEmpty()) {
//            StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//            emitStoreChange(storeChangeEvent);
//            return;
//        }
//        UnitModelDao unitModelDao = mDaoSession.getUnitModelDao();
//        for (Feature feature : mAllFeatureQueryResult) {
//            UnitModel unitModel = new UnitModel();
//            for (Map.Entry<String, Object> stringObjectEntry : feature.getAttributes().entrySet()) {
//                LogUtil.i("key=" + stringObjectEntry.getKey() + ",value=" + stringObjectEntry.getValue());
//            }
//
////            unitModel.setArcGisId(Integer.parseInt(feature.getAttributes().get(Config.ARCGIS_UNITID).toString()));
////            unitModel.setAddress(feature.getAttributes().get("ADDR").toString());
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
////            unitModelDao.insert(unitModel);
//        }
//        addAchievement();
//        StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//        emitStoreChange(storeChangeEvent);
//    }
//
//    /***
//     * 添加成果信息
//     */
//    private void addAchievement() {
//        if (!mDaoSession.loadAll(AchievementModel.class).isEmpty()) {
//            return;
//        }
//        AchievementModelDao achievementModelDao = mDaoSession.getAchievementModelDao();
//        UnitModelDao unitModelDao = mDaoSession.getUnitModelDao();
//        List<UnitModel> unitModelList = unitModelDao.queryBuilder().build().list();
//        for (UnitModel unitModel : unitModelList) {
//            Random random = new Random();
//            int achievementCount = random.nextInt(2) + 1;//给当前公司添加1-3条成果
//            for (int i = 0; i < achievementCount; i++) {
//                AchievementModel achievementModel = new AchievementModel();
//                achievementModel.setBjCode(Config.BJ_CODE[(int) (unitModel.getId() - 1)] + unitModel.getId() + i);
//                //给成果1添加1-5个产品
//                int sortOneCount = random.nextInt(4) + 1;
//                for (int j = 0; j < sortOneCount; j++) {
//                    addResultOneType(unitModel.getId());
//                }
//                //给成果2添加1-5个产品
//                int sortTwoCount = random.nextInt(4) + 1;
//                for (int j = 0; j < sortTwoCount; j++) {
//                    addResultTwoType(unitModel.getId());
//                }
//                achievementModel.setTime(DateUtil.stringToLong("yyyy-MM-dd", Config.ACHIEVEMENT_TIME[(int) (unitModel.getId() - 1)]));
//                achievementModel.setUnitKey(unitModel.getId());
//                achievementModelDao.insert(achievementModel);
//            }
//        }
////        List<AchievementModel> achievementModelList = achievementModelDao.queryBuilder().build().list();
//    }
//
//    /***
//     * 添加成果分类一
//     */
//
//    private long addResultOneType(long crKey) {
////      if(mDaoSession.loadAll(CGSortOneModel.class).isEmpty()){
//        CGSortOneModelDao cgSortOneModelDao = mDaoSession.getCGSortOneModelDao();
//        //长江重庆航道测绘处
//        CGSortOneModel cgSortOneModel = new CGSortOneModel();
//        cgSortOneModel.setCRKey(crKey);
//        Random random = new Random();
//        cgSortOneModel.setXTH(Config.XIN_TU_HAO[random.nextInt(Config.XIN_TU_HAO.length)] + (random.nextInt(8) + 1));
//        cgSortOneModel.setSCDate(Config.PRODUCT_TIME[random.nextInt(Config.PRODUCT_TIME.length)]);
//        cgSortOneModel.setDDJZ(Config.DADI_JZ[random.nextInt(Config.DADI_JZ.length)]);
//        cgSortOneModel.setSJGS(Config.DATA_FORMAT[random.nextInt(Config.DATA_FORMAT.length)]);
//        return cgSortOneModelDao.insert(cgSortOneModel);
////      }
//    }
//
//    /***
//     * 添加成果分类二
//     */
//    private long addResultTwoType(long crKey) {
//        CGSortTwoModelDao cgSortTwoModelDao = mDaoSession.getCGSortTwoModelDao();
//        //长江重庆航道测绘处
//        CGSortTwoModel cgSortTwoModel = new CGSortTwoModel();
//        cgSortTwoModel.setCRKey((int) crKey);
//        Random random = new Random();
//        cgSortTwoModel.setCall(Config.CALL[random.nextInt(Config.CALL.length)]);
//        cgSortTwoModel.setDit(Config.DIT[random.nextInt(Config.DIT.length)]);
//        cgSortTwoModel.setRank(Config.RANK[random.nextInt(Config.RANK.length)]);
//        cgSortTwoModel.setHCJZ(Config.HEIGHT_DATUM[random.nextInt(Config.HEIGHT_DATUM.length)]);
//        return cgSortTwoModelDao.insert(cgSortTwoModel);
//    }
//
//
//    @Override
//    public void onUnRegister() {
//
//    }
//}
