package android.zhixun.uiho.com.gissystem.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.zhixun.uiho.com.gissystem.AppCrash;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.UserModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoMaster;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;

import com.alibaba.fastjson.JSON;
import com.esri.android.runtime.ArcGISRuntime;
import com.yibogame.app.BaseApplication;
import com.yibogame.util.SPUtil;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

//import android.support.multidex.MultiDex;

/**
 * Created by parcool on 2016/11/3.
 */

public class MyBaseApplication extends BaseApplication {

    private static MyBaseApplication mAppliction = new MyBaseApplication();

    private static final String TAG = MyBaseApplication.class.getSimpleName();
//    private UnitModel UnitModel;

    //    public android.zhixun.uiho.com.gissystem.flux.models.UnitModel getUnitModel() {
//        return UnitModel;
//    }
//
//    public void setUnitModel(android.zhixun.uiho.com.gissystem.flux.models.UnitModel unitModel) {
//        UnitModel = unitModel;
//    }
    public static MyBaseApplication getInstance() {
        return mAppliction;
    }

    private CompanyDetailModel companyDetailModel;

    public CompanyDetailModel getCompanyDetailModel() {
        return companyDetailModel;
    }

    public void setCompanyDetailModel(CompanyDetailModel companyDetailModel) {
        this.companyDetailModel = companyDetailModel;
    }

    @Override
    protected String getAppName() {
        return getResources().getString(R.string.app_name);
    }

    @Override
    protected boolean getDebugModel() {
        return true;
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    protected Application getBaseApplication() {
        return this;
    }

    @Override
    public String getLogDir() {
        return Config.LOG_DIR;
    }

    @Override
    protected String getMyCacheDir() {
        return Config.CACHE_DIR;
    }

    @Override
    protected String getUpdateDir() {
        return Config.UPDATE_DIR;
    }

    @Override
    protected String getMapDir() {
        return Config.MAP_DIR;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCrash.getInstance().init(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        ArcGISRuntime.setClientId("5SKIXc21JlankElJ");
        //"runtimestandard,101,rux00000,none,XXXXXXX";
        ArcGISRuntime.License.setLicense("runtimelite,1000,rud4807136520,none,FA0RJAY3FY2M2J7EZ039");
//        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud4807136520,none,FA0RJAY3FY2M2J7EZ039");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private UserModel userModel;

    public UserModel getUserModel() {
        if (userModel == null) {
            userModel = JSON.parseObject(SPUtil.getInstance()
                            .getString("UserModelOfJson"), UserModel.class);
        }
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
