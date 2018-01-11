package android.zhixun.uiho.com.gissystem.greendao_gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import android.zhixun.uiho.com.gissystem.flux.models.CRModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRSubmitServerModel;
import android.zhixun.uiho.com.gissystem.flux.models.AchievementModel;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortTwoModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRActiveUserModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRImageModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRCheckPersonModel;
import android.zhixun.uiho.com.gissystem.flux.models.CQPrefectureModel;
import android.zhixun.uiho.com.gissystem.flux.models.UserModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRResultTypeModel;
import android.zhixun.uiho.com.gissystem.flux.models.IndustryCategoryModel;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortOneModel;
import android.zhixun.uiho.com.gissystem.flux.models.UnitModel;

import android.zhixun.uiho.com.gissystem.greendao_gen.CRModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRSubmitServerModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.AchievementModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortTwoModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRActiveUserModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRImageModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRCheckPersonModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CQPrefectureModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.UserModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRResultTypeModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.IndustryCategoryModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortOneModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.UnitModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cRModelDaoConfig;
    private final DaoConfig cRSubmitServerModelDaoConfig;
    private final DaoConfig achievementModelDaoConfig;
    private final DaoConfig cGSortTwoModelDaoConfig;
    private final DaoConfig cRActiveUserModelDaoConfig;
    private final DaoConfig cRImageModelDaoConfig;
    private final DaoConfig cRCheckPersonModelDaoConfig;
    private final DaoConfig cQPrefectureModelDaoConfig;
    private final DaoConfig userModelDaoConfig;
    private final DaoConfig cRResultTypeModelDaoConfig;
    private final DaoConfig industryCategoryModelDaoConfig;
    private final DaoConfig cGSortOneModelDaoConfig;
    private final DaoConfig unitModelDaoConfig;

    private final CRModelDao cRModelDao;
    private final CRSubmitServerModelDao cRSubmitServerModelDao;
    private final AchievementModelDao achievementModelDao;
    private final CGSortTwoModelDao cGSortTwoModelDao;
    private final CRActiveUserModelDao cRActiveUserModelDao;
    private final CRImageModelDao cRImageModelDao;
    private final CRCheckPersonModelDao cRCheckPersonModelDao;
    private final CQPrefectureModelDao cQPrefectureModelDao;
    private final UserModelDao userModelDao;
    private final CRResultTypeModelDao cRResultTypeModelDao;
    private final IndustryCategoryModelDao industryCategoryModelDao;
    private final CGSortOneModelDao cGSortOneModelDao;
    private final UnitModelDao unitModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cRModelDaoConfig = daoConfigMap.get(CRModelDao.class).clone();
        cRModelDaoConfig.initIdentityScope(type);

        cRSubmitServerModelDaoConfig = daoConfigMap.get(CRSubmitServerModelDao.class).clone();
        cRSubmitServerModelDaoConfig.initIdentityScope(type);

        achievementModelDaoConfig = daoConfigMap.get(AchievementModelDao.class).clone();
        achievementModelDaoConfig.initIdentityScope(type);

        cGSortTwoModelDaoConfig = daoConfigMap.get(CGSortTwoModelDao.class).clone();
        cGSortTwoModelDaoConfig.initIdentityScope(type);

        cRActiveUserModelDaoConfig = daoConfigMap.get(CRActiveUserModelDao.class).clone();
        cRActiveUserModelDaoConfig.initIdentityScope(type);

        cRImageModelDaoConfig = daoConfigMap.get(CRImageModelDao.class).clone();
        cRImageModelDaoConfig.initIdentityScope(type);

        cRCheckPersonModelDaoConfig = daoConfigMap.get(CRCheckPersonModelDao.class).clone();
        cRCheckPersonModelDaoConfig.initIdentityScope(type);

        cQPrefectureModelDaoConfig = daoConfigMap.get(CQPrefectureModelDao.class).clone();
        cQPrefectureModelDaoConfig.initIdentityScope(type);

        userModelDaoConfig = daoConfigMap.get(UserModelDao.class).clone();
        userModelDaoConfig.initIdentityScope(type);

        cRResultTypeModelDaoConfig = daoConfigMap.get(CRResultTypeModelDao.class).clone();
        cRResultTypeModelDaoConfig.initIdentityScope(type);

        industryCategoryModelDaoConfig = daoConfigMap.get(IndustryCategoryModelDao.class).clone();
        industryCategoryModelDaoConfig.initIdentityScope(type);

        cGSortOneModelDaoConfig = daoConfigMap.get(CGSortOneModelDao.class).clone();
        cGSortOneModelDaoConfig.initIdentityScope(type);

        unitModelDaoConfig = daoConfigMap.get(UnitModelDao.class).clone();
        unitModelDaoConfig.initIdentityScope(type);

        cRModelDao = new CRModelDao(cRModelDaoConfig, this);
        cRSubmitServerModelDao = new CRSubmitServerModelDao(cRSubmitServerModelDaoConfig, this);
        achievementModelDao = new AchievementModelDao(achievementModelDaoConfig, this);
        cGSortTwoModelDao = new CGSortTwoModelDao(cGSortTwoModelDaoConfig, this);
        cRActiveUserModelDao = new CRActiveUserModelDao(cRActiveUserModelDaoConfig, this);
        cRImageModelDao = new CRImageModelDao(cRImageModelDaoConfig, this);
        cRCheckPersonModelDao = new CRCheckPersonModelDao(cRCheckPersonModelDaoConfig, this);
        cQPrefectureModelDao = new CQPrefectureModelDao(cQPrefectureModelDaoConfig, this);
        userModelDao = new UserModelDao(userModelDaoConfig, this);
        cRResultTypeModelDao = new CRResultTypeModelDao(cRResultTypeModelDaoConfig, this);
        industryCategoryModelDao = new IndustryCategoryModelDao(industryCategoryModelDaoConfig, this);
        cGSortOneModelDao = new CGSortOneModelDao(cGSortOneModelDaoConfig, this);
        unitModelDao = new UnitModelDao(unitModelDaoConfig, this);

        registerDao(CRModel.class, cRModelDao);
        registerDao(CRSubmitServerModel.class, cRSubmitServerModelDao);
        registerDao(AchievementModel.class, achievementModelDao);
        registerDao(CGSortTwoModel.class, cGSortTwoModelDao);
        registerDao(CRActiveUserModel.class, cRActiveUserModelDao);
        registerDao(CRImageModel.class, cRImageModelDao);
        registerDao(CRCheckPersonModel.class, cRCheckPersonModelDao);
        registerDao(CQPrefectureModel.class, cQPrefectureModelDao);
        registerDao(UserModel.class, userModelDao);
        registerDao(CRResultTypeModel.class, cRResultTypeModelDao);
        registerDao(IndustryCategoryModel.class, industryCategoryModelDao);
        registerDao(CGSortOneModel.class, cGSortOneModelDao);
        registerDao(UnitModel.class, unitModelDao);
    }
    
    public void clear() {
        cRModelDaoConfig.clearIdentityScope();
        cRSubmitServerModelDaoConfig.clearIdentityScope();
        achievementModelDaoConfig.clearIdentityScope();
        cGSortTwoModelDaoConfig.clearIdentityScope();
        cRActiveUserModelDaoConfig.clearIdentityScope();
        cRImageModelDaoConfig.clearIdentityScope();
        cRCheckPersonModelDaoConfig.clearIdentityScope();
        cQPrefectureModelDaoConfig.clearIdentityScope();
        userModelDaoConfig.clearIdentityScope();
        cRResultTypeModelDaoConfig.clearIdentityScope();
        industryCategoryModelDaoConfig.clearIdentityScope();
        cGSortOneModelDaoConfig.clearIdentityScope();
        unitModelDaoConfig.clearIdentityScope();
    }

    public CRModelDao getCRModelDao() {
        return cRModelDao;
    }

    public CRSubmitServerModelDao getCRSubmitServerModelDao() {
        return cRSubmitServerModelDao;
    }

    public AchievementModelDao getAchievementModelDao() {
        return achievementModelDao;
    }

    public CGSortTwoModelDao getCGSortTwoModelDao() {
        return cGSortTwoModelDao;
    }

    public CRActiveUserModelDao getCRActiveUserModelDao() {
        return cRActiveUserModelDao;
    }

    public CRImageModelDao getCRImageModelDao() {
        return cRImageModelDao;
    }

    public CRCheckPersonModelDao getCRCheckPersonModelDao() {
        return cRCheckPersonModelDao;
    }

    public CQPrefectureModelDao getCQPrefectureModelDao() {
        return cQPrefectureModelDao;
    }

    public UserModelDao getUserModelDao() {
        return userModelDao;
    }

    public CRResultTypeModelDao getCRResultTypeModelDao() {
        return cRResultTypeModelDao;
    }

    public IndustryCategoryModelDao getIndustryCategoryModelDao() {
        return industryCategoryModelDao;
    }

    public CGSortOneModelDao getCGSortOneModelDao() {
        return cGSortOneModelDao;
    }

    public UnitModelDao getUnitModelDao() {
        return unitModelDao;
    }

}
