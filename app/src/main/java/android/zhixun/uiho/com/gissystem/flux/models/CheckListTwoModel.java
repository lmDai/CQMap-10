package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by zp on 2016/11/30.
 */

public class CheckListTwoModel {
    private String title;
    private String content;

    public CheckListTwoModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
