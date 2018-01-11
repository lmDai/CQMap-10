package com.yibogame.util;

import android.content.Context;
import android.util.Log;


import com.yibogame.app.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parcool on 2015/12/29.
 */
public class LogUtil {
    private static final int DEBUG = 1;
    private static final int WARNING = 2;
    private static final int INFO = 3;
    private static final int ERROR = 4;
    private static final int maxLengthPerLine = 3300;

    public static void d(String tag, String str) {
        if (BaseApplication.isDebugModel) {
            splitString(tag, DEBUG, str);
        }
    }

    public static void d(Context context, String str) {
        if (BaseApplication.isDebugModel) {
            splitString(context, DEBUG, str);
        }
    }

    public static void d(String str) {
        if (BaseApplication.isDebugModel) {
            splitString(DEBUG, str);
        }
    }

    public static void e(String tag, String str) {
        if (BaseApplication.isDebugModel) {
            splitString(tag, ERROR, str);
        }
    }

    public static void e(String str) {
        if (BaseApplication.isDebugModel) {
            splitString(ERROR, str);
        }
    }

    public static void e(Context context, String str) {
        if (BaseApplication.isDebugModel) {
            splitString(context, ERROR, str);
        }
    }

    public static void w(String tag, String str) {
        if (BaseApplication.isDebugModel) {
            splitString(tag, WARNING, str);
        }
    }

    public static void w(String str) {
        if (BaseApplication.isDebugModel) {
            splitString(WARNING, str);
        }
    }

    public static void w(Context context, String str) {
        if (BaseApplication.isDebugModel) {
            splitString(context, WARNING, str);
        }
    }

    public static void i(String tag, String str) {
        if (BaseApplication.isDebugModel) {
            splitString(tag, INFO, str);
        }
    }

    public static void i(String str) {
        if (BaseApplication.isDebugModel) {
            splitString(INFO, str);
        }
    }

    public static void i(Context context, String str) {
        if (BaseApplication.isDebugModel) {
            splitString(context, INFO, str);
        }
    }



    /***
     * 因为logcat有时要显示json的时候显示不完整。所以写了这么一个方法①
     * @param str
     * @return
     */
    private static List<String> split(String str) {
        List<String> list = new ArrayList<String>();
        while (str.length() > 0) {
            if (str.length() > maxLengthPerLine) {
                list.add(str.substring(0, maxLengthPerLine));// + "-->"
                str = str.substring(maxLengthPerLine);
            } else {
                list.add(str);
                str = "";
            }
        }
        return list;
    }

    /***
     * 因为logcat有时要显示json的时候显示不完整。所以写了这么一个方法②
     *
     * @param type
     * @param str
     */
    private static void splitString(int type, String str) {
        List<String> list = split(str);
        switch (type) {
            case DEBUG:
                for (int i = 0; i < list.size(); i++) {
                    Log.d(BaseApplication.context.getClass().getSimpleName(), list.get(i));
                }
                break;
            case INFO:
                for (int i = 0; i < list.size(); i++) {
                    Log.i(BaseApplication.context.getClass().getSimpleName(), list.get(i));
                }
                break;
            case WARNING:
                for (int i = 0; i < list.size(); i++) {
                    Log.w(BaseApplication.context.getClass().getSimpleName(), list.get(i));
                }
                break;
            case ERROR:
                for (int i = 0; i < list.size(); i++) {
                    Log.e(BaseApplication.context.getClass().getSimpleName(), list.get(i));
                }
                break;
        }
    }

    /***
     * 因为logcat有时要显示json的时候显示不完整。所以写了这么一个方法②
     *
     * @param type
     * @param str
     */
    private static void splitString(Context context, int type, String str) {
        List<String> list = split(str);
        switch (type) {
            case DEBUG:
                for (int i = 0; i < list.size(); i++) {
                    Log.d(context.getClass().getSimpleName(), list.get(i));
                }
                break;
            case INFO:
                for (int i = 0; i < list.size(); i++) {
                    Log.i(context.getClass().getSimpleName(), list.get(i));
                }
                break;
            case WARNING:
                for (int i = 0; i < list.size(); i++) {
                    Log.w(context.getClass().getSimpleName(), list.get(i));
                }
                break;
            case ERROR:
                for (int i = 0; i < list.size(); i++) {
                    Log.e(context.getClass().getSimpleName(), list.get(i));
                }
                break;
        }
    }

    /***
     * 因为logcat有时要显示json的时候显示不完整。所以写了这么一个方法②
     *
     * @param type
     * @param str
     */
    private static void splitString(String tag, int type, String str) {
        List<String> list = split(str);
        switch (type) {
            case DEBUG:
                for (int i = 0; i < list.size(); i++) {
                    Log.d(tag, list.get(i));
                }
                break;
            case INFO:
                for (int i = 0; i < list.size(); i++) {
                    Log.i(tag, list.get(i));
                }
                break;
            case WARNING:
                for (int i = 0; i < list.size(); i++) {
                    Log.w(tag, list.get(i));
                }
                break;
            case ERROR:
                for (int i = 0; i < list.size(); i++) {
                    Log.e(tag, list.get(i));
                }
                break;
        }
    }
}
