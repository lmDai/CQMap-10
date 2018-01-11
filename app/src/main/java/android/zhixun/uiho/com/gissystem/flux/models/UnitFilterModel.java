package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by zp on 2016/12/2.
 */

public class UnitFilterModel {
    private String city;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public UnitFilterModel(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
