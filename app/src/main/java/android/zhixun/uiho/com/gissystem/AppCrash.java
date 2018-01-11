package android.zhixun.uiho.com.gissystem;

import android.content.Context;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by simpepeng on 2017/8/2.
 */

public class AppCrash implements Thread.UncaughtExceptionHandler {

    private static AppCrash appCrash = new AppCrash();

    private Context mContext;
    private Thread.UncaughtExceptionHandler defaultExceptionHandler;

    private AppCrash() {

    }

    public static AppCrash getInstance() {
        return appCrash;
    }

    public void init(Context context) {
        this.mContext = context;
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        try {
            if (null != defaultExceptionHandler && !BuildConfig.DEBUG) {
//                LogUtils.d("uncaughtException");
                defaultExceptionHandler.uncaughtException(t, ex);
//                MobclickAgent.reportError(mContext, ex);
            } else {
                handleException(ex);
//                LogUtils.d("handleException");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleException(Throwable ex) {
        ex.printStackTrace();
        String message;
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();// 记得关闭
        message = writer.toString();

        Intent intent = new Intent(mContext, AppCashActivity.class);
        intent.putExtra(AppCashActivity.EXCEPTION_MSG, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

//        System.exit(1);//关闭已奔溃的app进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
