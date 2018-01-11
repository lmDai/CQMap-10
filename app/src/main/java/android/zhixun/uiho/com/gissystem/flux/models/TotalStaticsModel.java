package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by zp on 2016/12/1.
 */

public class TotalStaticsModel {
    private String type;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TotalStaticsModel(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
