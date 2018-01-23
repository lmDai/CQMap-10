package com.yibogame.util;

import android.widget.Toast;

import com.yibogame.app.BaseApplication;


/**
 * Created by parcool on 2016/1/1.
 */
public class ToastUtil {

    public static Toast toast;

    public static void showShort(CharSequence msg) {

        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(BaseApplication.context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLong(CharSequence msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(BaseApplication.context, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void showEmpty() {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(BaseApplication.context, "暂无数据", Toast.LENGTH_SHORT);
        toast.show();
    }
}
