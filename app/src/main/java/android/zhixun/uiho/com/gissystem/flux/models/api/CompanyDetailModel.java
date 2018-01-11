package android.zhixun.uiho.com.gissystem.flux.models.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.JSON;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanyi on 2017/3/21.
 */

public class CompanyDetailModel implements Parcelable{

    /**
     * areaId : 1
     * areaName : 渝中区
     * companyAddre : 单位联系地址
     * companyEMail : 133@11.com
     * companyId : 1
     * companyName : 单位名字
     * companyStatus : 1
     * companyTelephone : 单位座机
     * companyType : 1
     * corporationId : 1
     * createTime : 1489904166000
     * industryCategoryId : 1
     * industryCategoryName : 行业名1
     * isDelete : 0
     * isLocal : 1
     * organizationCode : 单位代码
     * secrecyPersonPhone : 保密管理人员手
     * zipCode : 500000
     */

    private int areaId;
    private String areaName;
    private String companyAddre;
    private String companyEMail;
    private int companyId;
    private String companyName;
    private int companyStatus;
    private String companyTelephone;
    private int companyType;
    private int corporationId;
    private long createTime;
    private int industryCategoryId;
    private String industryCategoryName;
    private int isDelete;
    private int isLocal;
    private String organizationCode;
    private String secrecyPersonPhone;
    private String zipCode;
    private List<CompanyCertificatesLicenseModel> companyCertificatesLicense;
    private List<CompanyCertificatesQualificationsModel> companyCertificatesQualifications;
    private List<CompanyCertificatesWayModel> companyCertificatesWay;
    private CorporationModel corporation;
    private ArrayList<HoldersModel> holders;
    private Point location;//该单位在地图上的位置
    private Geometry geometry;//几何形状
    private int secrecyIsPass;//是否通过（0不通过1通过
    private int fruitNum;//成果数量

    public CompanyDetailModel() {
    }

    protected CompanyDetailModel(Parcel in) {
        areaId = in.readInt();
        areaName = in.readString();
        companyAddre = in.readString();
        companyEMail = in.readString();
        companyId = in.readInt();
        companyName = in.readString();
        companyStatus = in.readInt();
        companyTelephone = in.readString();
        companyType = in.readInt();
        corporationId = in.readInt();
        createTime = in.readLong();
        industryCategoryId = in.readInt();
        industryCategoryName = in.readString();
        isDelete = in.readInt();
        isLocal = in.readInt();
        organizationCode = in.readString();
        secrecyPersonPhone = in.readString();
        zipCode = in.readString();
        holders = in.createTypedArrayList(HoldersModel.CREATOR);
        secrecyIsPass = in.readInt();
        fruitNum = in.readInt();
    }

    public static final Creator<CompanyDetailModel> CREATOR = new Creator<CompanyDetailModel>() {
        @Override
        public CompanyDetailModel createFromParcel(Parcel in) {
            return new CompanyDetailModel(in);
        }

        @Override
        public CompanyDetailModel[] newArray(int size) {
            return new CompanyDetailModel[size];
        }
    };

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCompanyAddre() {
        return companyAddre;
    }

    public void setCompanyAddre(String companyAddre) {
        this.companyAddre = companyAddre;
    }

    public String getCompanyEMail() {
        return companyEMail;
    }

    public void setCompanyEMail(String companyEMail) {
        this.companyEMail = companyEMail;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(int companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public int getCorporationId() {
        return corporationId;
    }

    public void setCorporationId(int corporationId) {
        this.corporationId = corporationId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIndustryCategoryId() {
        return industryCategoryId;
    }

    public void setIndustryCategoryId(int industryCategoryId) {
        this.industryCategoryId = industryCategoryId;
    }

    public String getIndustryCategoryName() {
        return industryCategoryName;
    }

    public void setIndustryCategoryName(String industryCategoryName) {
        this.industryCategoryName = industryCategoryName;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(int isLocal) {
        this.isLocal = isLocal;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getSecrecyPersonPhone() {
        return secrecyPersonPhone;
    }

    public void setSecrecyPersonPhone(String secrecyPersonPhone) {
        this.secrecyPersonPhone = secrecyPersonPhone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public List<CompanyCertificatesLicenseModel> getCompanyCertificatesLicense() {
        return companyCertificatesLicense;
    }

    public void setCompanyCertificatesLicense(List<CompanyCertificatesLicenseModel> companyCertificatesLicense) {
        this.companyCertificatesLicense = companyCertificatesLicense;
    }

    public List<CompanyCertificatesQualificationsModel> getCompanyCertificatesQualifications() {
        return companyCertificatesQualifications;
    }

    public void setCompanyCertificatesQualifications(List<CompanyCertificatesQualificationsModel> companyCertificatesQualifications) {
        this.companyCertificatesQualifications = companyCertificatesQualifications;
    }

    public List<CompanyCertificatesWayModel> getCompanyCertificatesWay() {
        return companyCertificatesWay;
    }

    public void setCompanyCertificatesWay(List<CompanyCertificatesWayModel> companyCertificatesWay) {
        this.companyCertificatesWay = companyCertificatesWay;
    }

    public CorporationModel getCorporation() {
        return corporation;
    }

    public void setCorporation(CorporationModel corporation) {
        this.corporation = corporation;
    }

    public ArrayList<HoldersModel> getHolders() {
        return holders;
    }

    public void setHolders(ArrayList<HoldersModel> holders) {
        this.holders = holders;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public int getSecrecyIsPass() {
        return secrecyIsPass;
    }

    public void setSecrecyIsPass(int secrecyIsPass) {
        this.secrecyIsPass = secrecyIsPass;
    }

    public int getFruitNum() {
        return fruitNum;
    }

    public void setFruitNum(int fruitNum) {
        this.fruitNum = fruitNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(areaId);
        dest.writeString(areaName);
        dest.writeString(companyAddre);
        dest.writeString(companyEMail);
        dest.writeInt(companyId);
        dest.writeString(companyName);
        dest.writeInt(companyStatus);
        dest.writeString(companyTelephone);
        dest.writeInt(companyType);
        dest.writeInt(corporationId);
        dest.writeLong(createTime);
        dest.writeInt(industryCategoryId);
        dest.writeString(industryCategoryName);
        dest.writeInt(isDelete);
        dest.writeInt(isLocal);
        dest.writeString(organizationCode);
        dest.writeString(secrecyPersonPhone);
        dest.writeString(zipCode);
        dest.writeTypedList(holders);
        dest.writeInt(secrecyIsPass);
        dest.writeInt(fruitNum);
    }
}
