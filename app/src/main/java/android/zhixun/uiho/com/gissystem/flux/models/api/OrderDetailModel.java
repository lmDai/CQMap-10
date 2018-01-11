package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/4/1.
 */

public class OrderDetailModel {

    /**
     * benginTime : 1490064449000
     * certificationLetterCompany : {"addres":"地址","createTime":1491032672000,"eMail":"邮箱","isDelete":0,"personName":"证明函机构名","personType":3,"phone":"证明电话","reportId":1,"reportUserId":3,"unitName":"证明机构名","zipCode":"邮编"}
     * companyName : 重庆607勘察实业总公司
     * confidentialLetterUrl : 保密责任书
     * createTime : 1491028314000
     * encryptionMode : 1
     * encryptionNo : 保密号
     * encryptionTime : 1490064573000
     * encryptionTimeOk : 1490150985000
     * endTime : 1490928457000
     * evidenceUrl : 证明材料
     * handleUser : {"addres":"地址","createTime":1491032672000,"eMail":"邮箱","isDelete":0,"personName":"经办人名字","personType":1,"phone":"经办电话","reportId":1,"reportUserId":1,"unitName":"重庆607勘察实业总公司","zipCode":"邮编"}
     * handleUserId : 1
     * handoutId : 1
     * isDelete : 0
     * letterOfIntroductionUrl : 介绍信
     * prepareStatus : 1
     * purposeUse : 使用目的
     * reportCompanyId : 1
     * reportId : 1
     * reportNo : abcde0011
     * reportPurposeId : 1
     * reportPurposeName : 地产
     * securityUser : {"addres":"地址","createTime":1491032672000,"eMail":"邮箱","isDelete":0,"personName":"保密机构人员名","personType":2,"phone":"保密电话","reportId":1,"reportUserId":2,"unitName":"保密机构名","zipCode":"邮编"}
     * securityUserId : 2
     * sourceUse : 使用来源
     * status : 3
     * useApplicationUrl : 成果使用申请表
     * userCode : 123123
     */

    private long benginTime;
    private CertificationLetterCompanyBean certificationLetterCompany;
    private String companyName;
    private String confidentialLetterUrl;
    private long createTime;
    private int encryptionMode;
    private String encryptionNo;
    private long encryptionTime;
    private long encryptionTimeOk;
    private long endTime;
    private String evidenceUrl;
    private HandleUserBean handleUser;
    private int handleUserId;
    private int handoutId;
    private int isDelete;
    private String letterOfIntroductionUrl;
    private int prepareStatus;
    private String purposeUse;
    private int reportCompanyId;
    private int reportId;
    private String reportNo;
    private int reportPurposeId;
    private String reportPurposeName;
    private SecurityUserBean securityUser;
    private int securityUserId;
    private String sourceUse;
    private int status;
    private String useApplicationUrl;
    private String userCode;

    public long getBenginTime() {
        return benginTime;
    }

    public void setBenginTime(long benginTime) {
        this.benginTime = benginTime;
    }

    public CertificationLetterCompanyBean getCertificationLetterCompany() {
        return certificationLetterCompany;
    }

    public void setCertificationLetterCompany(CertificationLetterCompanyBean certificationLetterCompany) {
        this.certificationLetterCompany = certificationLetterCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getConfidentialLetterUrl() {
        return confidentialLetterUrl;
    }

    public void setConfidentialLetterUrl(String confidentialLetterUrl) {
        this.confidentialLetterUrl = confidentialLetterUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getEncryptionMode() {
        return encryptionMode;
    }

    public void setEncryptionMode(int encryptionMode) {
        this.encryptionMode = encryptionMode;
    }

    public String getEncryptionNo() {
        return encryptionNo;
    }

    public void setEncryptionNo(String encryptionNo) {
        this.encryptionNo = encryptionNo;
    }

    public long getEncryptionTime() {
        return encryptionTime;
    }

    public void setEncryptionTime(long encryptionTime) {
        this.encryptionTime = encryptionTime;
    }

    public long getEncryptionTimeOk() {
        return encryptionTimeOk;
    }

    public void setEncryptionTimeOk(long encryptionTimeOk) {
        this.encryptionTimeOk = encryptionTimeOk;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getEvidenceUrl() {
        return evidenceUrl;
    }

    public void setEvidenceUrl(String evidenceUrl) {
        this.evidenceUrl = evidenceUrl;
    }

    public HandleUserBean getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(HandleUserBean handleUser) {
        this.handleUser = handleUser;
    }

    public int getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(int handleUserId) {
        this.handleUserId = handleUserId;
    }

    public int getHandoutId() {
        return handoutId;
    }

    public void setHandoutId(int handoutId) {
        this.handoutId = handoutId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getLetterOfIntroductionUrl() {
        return letterOfIntroductionUrl;
    }

    public void setLetterOfIntroductionUrl(String letterOfIntroductionUrl) {
        this.letterOfIntroductionUrl = letterOfIntroductionUrl;
    }

    public int getPrepareStatus() {
        return prepareStatus;
    }

    public void setPrepareStatus(int prepareStatus) {
        this.prepareStatus = prepareStatus;
    }

    public String getPurposeUse() {
        return purposeUse;
    }

    public void setPurposeUse(String purposeUse) {
        this.purposeUse = purposeUse;
    }

    public int getReportCompanyId() {
        return reportCompanyId;
    }

    public void setReportCompanyId(int reportCompanyId) {
        this.reportCompanyId = reportCompanyId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public int getReportPurposeId() {
        return reportPurposeId;
    }

    public void setReportPurposeId(int reportPurposeId) {
        this.reportPurposeId = reportPurposeId;
    }

    public String getReportPurposeName() {
        return reportPurposeName;
    }

    public void setReportPurposeName(String reportPurposeName) {
        this.reportPurposeName = reportPurposeName;
    }

    public SecurityUserBean getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserBean securityUser) {
        this.securityUser = securityUser;
    }

    public int getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(int securityUserId) {
        this.securityUserId = securityUserId;
    }

    public String getSourceUse() {
        return sourceUse;
    }

    public void setSourceUse(String sourceUse) {
        this.sourceUse = sourceUse;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUseApplicationUrl() {
        return useApplicationUrl;
    }

    public void setUseApplicationUrl(String useApplicationUrl) {
        this.useApplicationUrl = useApplicationUrl;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public static class CertificationLetterCompanyBean {
        /**
         * addres : 地址
         * createTime : 1491032672000
         * eMail : 邮箱
         * isDelete : 0
         * personName : 证明函机构名
         * personType : 3
         * phone : 证明电话
         * reportId : 1
         * reportUserId : 3
         * unitName : 证明机构名
         * zipCode : 邮编
         */

        private String addres;
        private long createTime;
        private String eMail;
        private int isDelete;
        private String personName;
        private int personType;
        private String phone;
        private int reportId;
        private int reportUserId;
        private String unitName;
        private String zipCode;

        public String getAddres() {
            return addres;
        }

        public void setAddres(String addres) {
            this.addres = addres;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEMail() {
            return eMail;
        }

        public void setEMail(String eMail) {
            this.eMail = eMail;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public int getPersonType() {
            return personType;
        }

        public void setPersonType(int personType) {
            this.personType = personType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getReportId() {
            return reportId;
        }

        public void setReportId(int reportId) {
            this.reportId = reportId;
        }

        public int getReportUserId() {
            return reportUserId;
        }

        public void setReportUserId(int reportUserId) {
            this.reportUserId = reportUserId;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }

    public static class HandleUserBean {
        /**
         * addres : 地址
         * createTime : 1491032672000
         * eMail : 邮箱
         * isDelete : 0
         * personName : 经办人名字
         * personType : 1
         * phone : 经办电话
         * reportId : 1
         * reportUserId : 1
         * unitName : 重庆607勘察实业总公司
         * zipCode : 邮编
         */

        private String addres;
        private long createTime;
        private String eMail;
        private int isDelete;
        private String personName;
        private int personType;
        private String phone;
        private int reportId;
        private int reportUserId;
        private String unitName;
        private String zipCode;

        public String getAddres() {
            return addres;
        }

        public void setAddres(String addres) {
            this.addres = addres;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEMail() {
            return eMail;
        }

        public void setEMail(String eMail) {
            this.eMail = eMail;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public int getPersonType() {
            return personType;
        }

        public void setPersonType(int personType) {
            this.personType = personType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getReportId() {
            return reportId;
        }

        public void setReportId(int reportId) {
            this.reportId = reportId;
        }

        public int getReportUserId() {
            return reportUserId;
        }

        public void setReportUserId(int reportUserId) {
            this.reportUserId = reportUserId;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }

    public static class SecurityUserBean {
        /**
         * addres : 地址
         * createTime : 1491032672000
         * eMail : 邮箱
         * isDelete : 0
         * personName : 保密机构人员名
         * personType : 2
         * phone : 保密电话
         * reportId : 1
         * reportUserId : 2
         * unitName : 保密机构名
         * zipCode : 邮编
         */

        private String addres;
        private long createTime;
        private String eMail;
        private int isDelete;
        private String personName;
        private int personType;
        private String phone;
        private int reportId;
        private int reportUserId;
        private String unitName;
        private String zipCode;

        public String getAddres() {
            return addres;
        }

        public void setAddres(String addres) {
            this.addres = addres;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEMail() {
            return eMail;
        }

        public void setEMail(String eMail) {
            this.eMail = eMail;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public int getPersonType() {
            return personType;
        }

        public void setPersonType(int personType) {
            this.personType = personType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getReportId() {
            return reportId;
        }

        public void setReportId(int reportId) {
            this.reportId = reportId;
        }

        public int getReportUserId() {
            return reportUserId;
        }

        public void setReportUserId(int reportUserId) {
            this.reportUserId = reportUserId;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
