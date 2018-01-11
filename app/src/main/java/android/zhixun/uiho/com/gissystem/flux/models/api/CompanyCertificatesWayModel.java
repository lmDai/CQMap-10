package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/21.
 * 管理办法Model
 */

public class CompanyCertificatesWayModel {

    /**
     * certificatesId : 1
     * certificatesName : 管理办法
     * certificatesType : 1
     * certificatesUrl : url
     * companyId : 1
     * createTime : 1489913949000
     * isDelete : 0
     * suffix : pdf
     */

    private int certificatesId;
    private String certificatesName;
    private int certificatesType;
    private String certificatesUrl;
    private int companyId;
    private long createTime;
    private int isDelete;
    private String suffix;

    public int getCertificatesId() {
        return certificatesId;
    }

    public void setCertificatesId(int certificatesId) {
        this.certificatesId = certificatesId;
    }

    public String getCertificatesName() {
        return certificatesName;
    }

    public void setCertificatesName(String certificatesName) {
        this.certificatesName = certificatesName;
    }

    public int getCertificatesType() {
        return certificatesType;
    }

    public void setCertificatesType(int certificatesType) {
        this.certificatesType = certificatesType;
    }

    public String getCertificatesUrl() {
        return certificatesUrl;
    }

    public void setCertificatesUrl(String certificatesUrl) {
        this.certificatesUrl = certificatesUrl;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
