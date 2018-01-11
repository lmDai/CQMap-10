package android.zhixun.uiho.com.gissystem.flux.models;

import android.widget.TextView;

/**
 * Created by zp on 2016/11/30.
 */

public class UnitDetailModel {
    private String tvNumber;//持证人数量
    private String tvState;//保密检查结果——通过
    private String tvEnterpriseCodeContent;//企业代码
    private String tvBusinessCategoryContent;//行业类别
    private String tvDomicileontent;//注册地
    private String tvContactsContacts;//联系人
    private String tvTelContacts;//座机电话
    private String tvPhoneContent;//联系电话
    private String tvCompany;//公司
    private String image;

    public UnitDetailModel(String image) {
        this.image = image;
    }

    public String getTvNumber() {
        return tvNumber;
    }

    public void setTvNumber(String tvNumber) {
        this.tvNumber = tvNumber;
    }

    public String getTvState() {
        return tvState;
    }

    public void setTvState(String tvState) {
        this.tvState = tvState;
    }

    public String getTvEnterpriseCodeContent() {
        return tvEnterpriseCodeContent;
    }

    public void setTvEnterpriseCodeContent(String tvEnterpriseCodeContent) {
        this.tvEnterpriseCodeContent = tvEnterpriseCodeContent;
    }

    public String getTvBusinessCategoryContent() {
        return tvBusinessCategoryContent;
    }

    public void setTvBusinessCategoryContent(String tvBusinessCategoryContent) {
        this.tvBusinessCategoryContent = tvBusinessCategoryContent;
    }

    public String getTvDomicileontent() {
        return tvDomicileontent;
    }

    public void setTvDomicileontent(String tvDomicileontent) {
        this.tvDomicileontent = tvDomicileontent;
    }

    public String getTvContactsContacts() {
        return tvContactsContacts;
    }

    public void setTvContactsContacts(String tvContactsContacts) {
        this.tvContactsContacts = tvContactsContacts;
    }

    public String getTvTelContacts() {
        return tvTelContacts;
    }

    public void setTvTelContacts(String tvTelContacts) {
        this.tvTelContacts = tvTelContacts;
    }

    public String getTvPhoneContent() {
        return tvPhoneContent;
    }

    public void setTvPhoneContent(String tvPhoneContent) {
        this.tvPhoneContent = tvPhoneContent;
    }

    public String getTvCompany() {
        return tvCompany;
    }

    public void setTvCompany(String tvCompany) {
        this.tvCompany = tvCompany;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
