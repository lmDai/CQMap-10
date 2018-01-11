package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by zp on 2016/11/29.
 */

public class HolderDetailModel {
    private String idCard;
    private String phoneNumber;
    private String eMail;
    private String unit;
    private String number;

    public HolderDetailModel(String idCard, String phoneNumber, String eMail, String unit, String number) {
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.unit = unit;
        this.number = number;
    }

    public HolderDetailModel() {
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
