package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by zp on 2016/11/30.
 */

public class CheckListModel {
    private String title;
    private int type;
    private String state;

    public CheckListModel() {
    }

    public CheckListModel(String title, int type, String state) {
        this.title = title;
        this.type = type;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
