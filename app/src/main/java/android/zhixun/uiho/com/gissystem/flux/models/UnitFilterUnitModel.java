package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by zp on 2016/12/2.
 */

public class UnitFilterUnitModel {
    private String all;
    private String city;
    private boolean isChecked;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UnitFilterUnitModel(String all, String city, int type) {
        this.all = all;
        this.city = city;
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public UnitFilterUnitModel(String all, String city) {
        this.all = all;
        this.city = city;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
