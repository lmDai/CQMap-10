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

//    public static void showSuccess(String msg) {
//        if (toast != null) {
//            toast.cancel();
//
//        }
//        toast = new Toast(BaseApplication.context);
//        toast.setGravity(Gravity.FILL, 0, 0);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        View toastView = LayoutInflater.from(BaseApplication.context).inflate(R.layout.toast, null);
////        toastView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
//        TextViewPlus textViewPlus = (TextViewPlus) toastView.findViewById(R.id.tvp_msg);
//        textViewPlus.setText(msg);
//        toast.setView(toastView);
//        toast.show();
//    }
}
