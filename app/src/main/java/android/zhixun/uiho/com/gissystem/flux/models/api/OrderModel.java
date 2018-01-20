package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/23.
 * 成果分发列表（简单的那个，不含具体的成果分发数据）
 */

public class OrderModel {

    /**
     * companyId : 1
     * companyName : 重庆607勘察实业总公司
     * createTime : 1490064629000
     * handoutId : 1
     * isDelete : 0
     * reportId : 1
     * reportNo : abcde0011
     * status : 2
     */

    public int companyId;
    public String companyName;
    public long createTime;
    public int handoutId;
    public int isDelete;
    public int reportId;
    public String reportNo;
    public int status;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
