package android.zhixun.uiho.com.gissystem.flux.models;

import java.lang.reflect.Type;

/**
 * Created by tanyi on 2017/3/28.
 */

public class RXProgress {
    private int progress;
    private String classSimpleName;
    private Object tag;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
