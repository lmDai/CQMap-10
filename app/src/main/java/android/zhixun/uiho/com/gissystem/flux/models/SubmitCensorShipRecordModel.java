package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by zp on 2016/12/6.
 */

public class SubmitCensorShipRecordModel {
    private String company;
    private String date;

    public SubmitCensorShipRecordModel(String company, String date) {
        this.company = company;
        this.date = date;
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
}
