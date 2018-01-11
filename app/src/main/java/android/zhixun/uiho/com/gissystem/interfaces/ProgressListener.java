package android.zhixun.uiho.com.gissystem.interfaces;

/**
 * Created by tanyi on 2017/3/28.
 */

public interface ProgressListener {
    void onProgress(long bytesRead, long contentLength, boolean done);
}
