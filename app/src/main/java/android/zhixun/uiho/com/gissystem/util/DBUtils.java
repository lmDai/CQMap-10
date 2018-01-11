package android.zhixun.uiho.com.gissystem.util;

import android.app.Activity;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;

/**
 * Created by parcool on 2016/12/5.
 */
public class DBUtils {
    private static DBUtils ourInstance = new DBUtils();

    public static DBUtils getInstance() {
        return ourInstance;
    }

    private DaoSession daoSession;
    private DBUtils() {

    }
    private void checkDaoSession(Activity activity){
        if (daoSession==null){
            // get the note DAO
            daoSession = ((MyBaseApplication) activity.getApplication()).getDaoSession();
        }
    }

//    public <T> void add(Activity activity,T t){
//        checkDaoSession(activity);
//        daoSession.getUserModelDao().insert(t);
//    }




}
