package android.zhixun.uiho.com.gissystem.interfaces;

/**
 * Created by parcool on 2016/10/14.
 */

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
