package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainBottomModel {
    private String company;
    private String distance;
    private String address;
    private String position;
    private String detail;

    public MainBottomModel(String company, String distance, String address) {
        this.company = company;
        this.distance = distance;
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
