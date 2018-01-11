package com.yibogame.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.CallSuper;



import java.io.File;

/**
 * Created by Parcool on 2016/4/5.
 */
public abstract class BaseApplication extends Application {

    public static Application instance;
    public static Context context;
    public static String logDir,cacheDir,updateDir,mapDir;

    protected abstract String getAppName();
    protected abstract boolean getDebugModel();
    public static boolean isDebugModel;
    protected abstract Context getContext();
    protected abstract Application getBaseApplication();
    public abstract String getLogDir();
    protected abstract String getMyCacheDir();//android.content.ContextWrapper has the same method name
    protected abstract String getUpdateDir();
    protected abstract String getMapDir();


    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        instance = getBaseApplication();
        context = getContext();
        isDebugModel = getDebugModel();
//        initCrashHandler();
//        Fresco.initialize(context);
//        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
    }

    private void initCrashHandler() {
        logDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + getAppName() + File.separator + getLogDir();
        cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + getAppName() + File.separator + getMyCacheDir();
        updateDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + getAppName() + File.separator + getUpdateDir();
        mapDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + getAppName() + File.separator + getMapDir();
        //初始化CrashHandler
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(context);
//        File fileLog = new File(logDir);
//        if (!fileLog.exists()) {
//            fileLog.mkdirs();
//        }
//
//        File fileCache = new File(cacheDir);
//        if (!fileCache.exists()) {
//            fileCache.mkdirs();
//        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;




}
