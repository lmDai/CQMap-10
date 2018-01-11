package com.yibogame.util;

import android.app.Activity;
import android.content.SharedPreferences;

import com.yibogame.R;
import com.yibogame.app.BaseApplication;

/**
 * Created by parcool on 2016/8/25.
 */
public class SPUtil {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private static SPUtil ourInstance = new SPUtil();

    public static SPUtil getInstance() {
        return ourInstance;
    }

    private SPUtil() {
        sharedPreferences = BaseApplication.context.getSharedPreferences(BaseApplication.context.getString(R.string.app_name), Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void put(String key,Object object){
        if (object instanceof String){
            editor.putString(key,object.toString());
        }else if(object instanceof Boolean){
            editor.putBoolean(key, (Boolean) object);
        }else if(object instanceof Integer){
            editor.putInt(key, (Integer) object);
        }else if(object instanceof Long){
            editor.putLong(key, (Long) object);
        }else if(object instanceof Float){
            editor.putFloat(key, (Float) object);
        }
        editor.commit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    public float  getFloat(String key){
        return sharedPreferences.getFloat(key,0f);
    }

    public long getLong(String key){
        return sharedPreferences.getLong(key,0l);
    }

}
