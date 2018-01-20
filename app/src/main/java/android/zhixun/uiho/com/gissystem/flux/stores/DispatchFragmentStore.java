package android.zhixun.uiho.com.gissystem.flux.stores;

import android.text.TextUtils;
import android.zhixun.uiho.com.gissystem.flux.actions.DispatchFragmentAction;
import android.zhixun.uiho.com.gissystem.flux.models.CQPrefectureModel;
import android.zhixun.uiho.com.gissystem.flux.models.IndustryCategoryModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.CQPrefectureModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.greendao_gen.IndustryCategoryModelDao;
import android.zhixun.uiho.com.gissystem.ui.itemY.OwnSMCHResultItem;

import com.yibogame.flux.actions.Action;
import com.yibogame.flux.dispatcher.Dispatcher;
import com.yibogame.flux.stores.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by parcool on 2016/12/16.
 */

public class DispatchFragmentStore extends Store {


    private static final long serialVersionUID = -159790047162393360L;
    private List<String> listHYLB_CODE = new ArrayList<>();
    private List<String> listZCD_CODE = new ArrayList<>();
    private DaoSession mDaoSession;
    private List<CQPrefectureModel> cqPrefectureModelList = new ArrayList<>();
    private List<IndustryCategoryModel> industryCategoryModelList = new ArrayList<>();
    private String sql = "";
    private List<OwnSMCHResultItem> listOwnSMCHResultItem = new ArrayList<>();

    public List<OwnSMCHResultItem> getOwnSMCHResultItemList() {
        return listOwnSMCHResultItem;
    }

    public void setDaoSession(DaoSession mDaoSession) {
        this.mDaoSession = mDaoSession;
    }

    public List<CQPrefectureModel> getCqPrefectureModelList() {
        return cqPrefectureModelList;
    }

    public List<IndustryCategoryModel> getIndustryCategoryModelList() {
        return industryCategoryModelList;
    }

    public List<String> getListHYLB_CODE() {
        return listHYLB_CODE;
    }

    public List<String> getListZCD_CODE() {
        return listZCD_CODE;
    }

    public String getSql() {
        return sql;
    }

    public DispatchFragmentStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onAction(Action action) {
        switch (action.getType()) {
            case DispatchFragmentAction.GET_SIFT_ZCD_DATA:
                getSiftZCDData(action);
                break;
            case DispatchFragmentAction.ZCD_ADAPTER_ON_ITEM_CLICK:
                int positionZCD = (int) action.getData().get("position");
                onZCDAdapterItemClick(action, positionZCD);
                break;
            case DispatchFragmentAction.GET_SIFT_LB_DATA:
                getSiftLBData(action);
                break;
            case DispatchFragmentAction.LB_ADAPTER_ON_ITEM_CLICK:
                int positionLB = (int) action.getData().get("position");
                onLBAdapterItemClick(action, positionLB);
                break;
            case DispatchFragmentAction.BUILD_SQL:
                String searchStr = action.getData().get("searchStr").toString();
                String caseCode = action.getData().get("caseCode").toString();
                String dataCode = action.getData().get("dataCode").toString();
                long startTime = Long.parseLong(action.getData().get("startTime").toString());
                long endTime = Long.parseLong(action.getData().get("endTime").toString());
                buildSql(action, searchStr, caseCode, dataCode, startTime, endTime);
                break;
            case DispatchFragmentAction.NO_DATA:

                break;
        }
    }

    private void buildSql(Action action, String searchStr, String caseCode, String dataCode, long startTime, long endTime) {
//        params-【参数类】属性：page页码，rows页条数，
// key单位名/机构代码，
// reportNo报件编号，
// reportTimeBegin开始时间，
// reportTimeEnd结束时间，
// companyId单位id，
// otherKey图号/点号（需要后台系统配置查询）
        Map<Object, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(searchStr)) {
            map.put("key", searchStr);
        }
        if (!TextUtils.isEmpty(caseCode)) {
            map.put("reportNo", caseCode);
        }
        if (!TextUtils.isEmpty(dataCode)) {
            map.put("otherKey", dataCode);
        }
        if (startTime != 0) {
            map.put("reportTimeBegin", startTime);
        }
        if (endTime != 0) {
            map.put("reportTimeEnd", endTime);
        }
//        APIService.getInstance().getOrderList(map, new DoOnSubscriber<List<OrderModel>>() {
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
//            public void onNext(List<OrderModel> orderModelList) {
//                StringBuilder stringBuilder = new StringBuilder();
//                for (int i = 0; i < orderModelList.size(); i++) {
////                    LogUtil.i(companyModels.get(i).getCompanyName()+","+companyModels.get(i).getOrganizationCode());
//                    stringBuilder.append(Config.ARCGIS_UNITID);
//                    stringBuilder.append(" = ");
//                    stringBuilder.append(orderModelList.get(i).getCompanyId());
//                    if (i != orderModelList.size() - 1) {
//                        stringBuilder.append(" or ");
//                    }
//                }
//                sql = stringBuilder.toString();
//
//                listOwnSMCHResultItem.clear();
//                for (OrderModel orderModel : orderModelList) {
//                    listOwnSMCHResultItem.add(new OwnSMCHResultItem(orderModel));
//                }
//                LogUtil.d("askldjflasjflasjdfl;asjklfjas;ldfjklaskjf3333");
//                for (Object object : listOwnSMCHResultItem) {
//                    OwnSMCHResultItem ownSMCHResultItem = (OwnSMCHResultItem) object;
//                    LogUtil.i("---------->"+ownSMCHResultItem.getData().getCompanyName());
//                }
//                StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//                emitStoreChange(storeChangeEvent);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                action.setType(DispatchFragmentAction.NO_DATA);
//                StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//                emitStoreChange(storeChangeEvent);
//                ToastUtil.showShort(e.getMessage());
//            }
//        });



//        if (action != null) {
//            sql = "id =";
//            UnitModelDao unitModelDao = mDaoSession.getUnitModelDao();
//            QueryBuilder<UnitModel> qbUnitModel = unitModelDao.queryBuilder();
//            AchievementModelDao achievementModelDao = mDaoSession.getAchievementModelDao();
//            QueryBuilder<AchievementModel> qbAchievement = achievementModelDao.queryBuilder();
//            String caseSql = "";
//            if (!TextUtils.isEmpty(caseCode)) {
//                caseSql = " T.BJ_CODE LIKE '%" + caseCode + "%'";
//            }
//
//            if (startTime != 0 && endTime != 0) {
//                if (TextUtils.isEmpty(caseCode)) {
//                    caseSql += " (T.TIME >= " + startTime + " AND T.TIME <= " + endTime + ")";
//                } else {
//                    caseSql += " AND (T.TIME >=" + startTime + " AND T.TIME <=" + endTime + ")";
//                }
//            }
//
//            String searchSql = "";
//            if (!caseSql.equals("")) {
//                if (!TextUtils.isEmpty(searchStr)) {
//                    searchSql = " AND B.NAME LIKE '%" + searchStr + "%'";
//                }
//            } else {
//                if (!TextUtils.isEmpty(searchStr)) {
//                    searchSql = " B.NAME LIKE '%" + searchStr + "%'";
//                }
//            }
//            //新图号
//            String dataSql = "";
//            if (!searchSql.equals("") || !caseSql.equals("")) {
//                if (!TextUtils.isEmpty(dataCode)) {
//                    dataSql = " AND C.XTH LIKE '%" + dataCode + "%'";
//                }
//            } else {
//                if (!TextUtils.isEmpty(dataCode)) {
//                    dataSql = " C.XTH LIKE '%" + dataCode + "%'";
//                }
//            }
//
//            String preSql = "LEFT JOIN UNIT_MODEL B ON " +
//                    "T.UNIT_KEY=B._id LEFT JOIN CGSORT_ONE_MODEL C ON " +
//                    "C.CRKEY=B._id";
//
//            if (!dataSql.equals("") || !searchSql.equals("") || !caseSql.equals("")) {
//                preSql += " WHERE";
//            }
//
//            String sql = preSql + caseSql + searchSql + dataSql;
//            LogUtil.e("sql----->" + sql);
//            Query<AchievementModel> qb2 = achievementModelDao.queryRawCreate(sql);
//            if (!TextUtils.isEmpty(searchStr)) {
//                qbUnitModel.where(UnitModelDao.Properties.Name.like("%" + searchStr + "%")).distinct();
//            }
//            Join<UnitModel, AchievementModel> joinNew = qbUnitModel.join(UnitModelDao.Properties.Id, AchievementModel.class, AchievementModelDao.Properties.UnitKey);
//            Join<AchievementModel, CGSortOneModel> joinTemp = qbAchievement.join(joinNew, UnitModelDao.Properties.Id, CGSortOneModel.class, CGSortOneModelDao.Properties.CRKey);
//            if (!TextUtils.isEmpty(dataCode)) {
//                joinTemp.where(CGSortOneModelDao.Properties.XTH.like("%" + dataCode + "%"));
//            }
//            if (!TextUtils.isEmpty(caseCode)) {
//                qbAchievement.where(AchievementModelDao.Properties.BjCode.like("%" + caseCode + "%"));
//            }
//
//            listOwnSMCHResultItem.clear();
//            List<AchievementModel> listTemp = qb2.list();
//            List<AchievementModel> listWithoutDup = new ArrayList<AchievementModel>(new HashSet<AchievementModel>(listTemp));
//            for (AchievementModel achievementModel : listWithoutDup) {
//                LogUtil.e("BjCode->" + achievementModel.getBjCode());
//            }
//            listOwnSMCHResultItem.addAll(listWithoutDup);
//            for (int i = 0; i < listOwnSMCHResultItem.size(); i++) {
//                AchievementModel achievementModel = listOwnSMCHResultItem.get(i);
//                if (i != listOwnSMCHResultItem.size() - 1) {
//                    sql += (achievementModel.getId()) + " or id=";
//                } else {
//                    sql += achievementModel.getId();
//                }
//            }
//            LogUtil.e("sql=" + sql);
//            StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
//            emitStoreChange(storeChangeEvent);
//        }

    }

    private void getSiftZCDData(Action action) {
        //查询注册地
        CQPrefectureModelDao cqPrefectureModelDao = mDaoSession.getCQPrefectureModelDao();
        cqPrefectureModelList.clear();
        cqPrefectureModelList.addAll(cqPrefectureModelDao.queryBuilder().build().list());
        //如果注册地还没选，那就默认让它选择全部
        if (listZCD_CODE.size() == 0) {
            for (CQPrefectureModel cqPrefectureModel : cqPrefectureModelList) {
                cqPrefectureModel.setChecked(false);
            }
            cqPrefectureModelList.get(0).setChecked(true);
        } else {
            //如果注册地已经选择，那么选中它
            for (CQPrefectureModel cqPrefectureModel : cqPrefectureModelList) {
                cqPrefectureModel.setChecked(false);
                for (String s : listZCD_CODE) {
                    if (cqPrefectureModel.getCode().equals(s)) {
                        cqPrefectureModel.setChecked(true);
                        break;
                    }
                }
            }
        }
        StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
        emitStoreChange(storeChangeEvent);
    }

    private void getSiftLBData(Action action) {
        IndustryCategoryModelDao industryCategoryModelDao = mDaoSession.getIndustryCategoryModelDao();
        industryCategoryModelList.clear();
        industryCategoryModelList.addAll(industryCategoryModelDao.queryBuilder().build().list());
        //如果类别还没选，那就默认让它选择全部
        if (listHYLB_CODE.size() == 0) {
            for (IndustryCategoryModel industryCategoryModel : industryCategoryModelList) {
                industryCategoryModel.setChecked(false);
            }
            industryCategoryModelList.get(0).setChecked(true);
        } else {
            //如果类别已经选择，那么选中它
            for (IndustryCategoryModel industryCategoryModel : industryCategoryModelList) {
                industryCategoryModel.setChecked(false);
                for (String s : listHYLB_CODE) {
                    if (industryCategoryModel.getCode().equals(s)) {
                        industryCategoryModel.setChecked(true);
                        break;
                    }
                }
            }
        }
        StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
        emitStoreChange(storeChangeEvent);
    }


    private void onZCDAdapterItemClick(Action action, int position) {
        if (position == 0) {//如果选择的全部
            //取消所有其他选择
            for (CQPrefectureModel list : cqPrefectureModelList) {
                list.setChecked(false);
            }
            //让'全部'选中
            cqPrefectureModelList.get(0).setChecked(true);
            listZCD_CODE.clear();
        } else {//如果选择的不是'全部'
            //让'全部'不选择，让当前这个取反
            cqPrefectureModelList.get(0).setChecked(false);
            cqPrefectureModelList.get(position).setChecked(!cqPrefectureModelList.get(position).isChecked());
            if (cqPrefectureModelList.get(position).isChecked()) {
                //赋值注册地代码
                listZCD_CODE.add(cqPrefectureModelList.get(position).getCode());
            } else {
                //删除注册地代码
                listZCD_CODE.remove(cqPrefectureModelList.get(position).getCode());
            }
        }

        StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
        emitStoreChange(storeChangeEvent);
    }

    private void onLBAdapterItemClick(Action action, int position) {
        if (position == 0) {//如果选择的全部
            //取消所有其他选择
            for (IndustryCategoryModel list : industryCategoryModelList) {
                list.setChecked(false);
            }
            //让'全部'选中
            industryCategoryModelList.get(0).setChecked(true);
            listHYLB_CODE.clear();
        } else {//如果选择的不是'全部'
            //让'全部'不选择，让当前这个取反
            industryCategoryModelList.get(0).setChecked(false);
            industryCategoryModelList.get(position).setChecked(!industryCategoryModelList.get(position).isChecked());
            if (industryCategoryModelList.get(position).isChecked()) {
                //赋值类别代码
                listHYLB_CODE.add(industryCategoryModelList.get(position).getCode());
            } else {
                //删除注册地代码
                listHYLB_CODE.remove(industryCategoryModelList.get(position).getCode());
            }
        }
        StoreChangeEvent storeChangeEvent = new StoreChangeEvent(action);
        emitStoreChange(storeChangeEvent);
    }


    @Override
    public void onUnRegister() {

    }
}

