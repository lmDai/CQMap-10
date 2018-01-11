package com.yibogame.app;

/**
 * Created by parcool on 2016/1/1.
 * <p>
 * 全局处理异常.
 * <p>
 * 全局处理异常.
 * <p>
 * 全局处理异常.
 * <p>
 * 全局处理异常.
 */

/**
 * 全局处理异常.
 */

import android.content.Context;
import android.os.Looper;

import com.yibogame.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * 在Application中统一捕获异常，保存到文件中下次再打开时上传
 *
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;

    private Context mContext;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param context 上下文
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else { // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//            }
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(0);
//            ActivityManager activityMgr= (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
//            activityMgr.killBackgroundProcesses(mContext.getPackageName());

//            Intent startMain=new Intent(Intent.ACTION_MAIN);
//            startMain.addCategory(Intent.CATEGORY_HOME);
//            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(startMain);
//            System.exit(0);
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @return true代表处理该异常，不再向上抛异常，
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     * 简单来说就是true不会弹出那个错误提示框，false就会弹出
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            LogUtil.e("ex is null!");
            return false;
        }

        final StackTraceElement[] stack = ex.getStackTrace();
        final String message = ex.getMessage();


        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
//                ToastUtil.showLong(mContext.getResources().getString(R.string.crash_exit));
                doSave(stack, getPhoneInfo() + message);
                Looper.loop();
            }

            private void doSave(final StackTraceElement[] stack,
                                final String message) {
                LogUtil.e("Save start!");
                // 保存出错信息到文件
                String fileName = "crash-" + new Date().getTime() + ".log";
                File file = new File(BaseApplication.logDir);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file1 = new File(file, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file1, true);
                    fos.write(message.getBytes());
                    for (int i = 0; i < stack.length; i++) {
                        fos.write(stack[i].toString().getBytes());
                    }

                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                }
                LogUtil.e("Save Finish");
            }

        }.start();
        ex.printStackTrace();
        return true;
    }

    /**
     * 获取手机信息
     *
     * @return
     */
    public String getPhoneInfo() {
        String phoneInfo = "手机信息:\nProduct: " + android.os.Build.PRODUCT;
        phoneInfo += "\nCPU_ABI: " + android.os.Build.CPU_ABI;
        phoneInfo += "\nTAGS: " + android.os.Build.TAGS;
        phoneInfo += "\nVERSION_CODES.BASE: "
                + android.os.Build.VERSION_CODES.BASE;
        phoneInfo += "\nMODEL: " + android.os.Build.MODEL;
        phoneInfo += "\nSDK: " + android.os.Build.VERSION.SDK_INT;
        phoneInfo += "\nVERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += "\nDEVICE: " + android.os.Build.DEVICE;
        phoneInfo += "\nDISPLAY: " + android.os.Build.DISPLAY;
        phoneInfo += "\nBRAND: " + android.os.Build.BRAND;
        phoneInfo += "\nBOARD: " + android.os.Build.BOARD;
        phoneInfo += "\nFINGERPRINT: " + android.os.Build.FINGERPRINT;
        phoneInfo += "\nID: " + android.os.Build.ID;
        phoneInfo += "\nMANUFACTURER: " + android.os.Build.MANUFACTURER;
        phoneInfo += "\nUSER: " + android.os.Build.USER + "\n错误信息:\n";
        return phoneInfo;
    }
}