package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainBottomResultFFaModel {
    private String company;
    private String date;
    private String code;

    public MainBottomResultFFaModel(String company, String date, String code) {
        this.company = company;
        this.date = date;
        this.code = code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
