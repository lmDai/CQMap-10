package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/21.
 * 单位法人
 */

public class CorporationModel {

    /**
     * companyId : 1
     * createTime : 1489910598000
     * idcard : 500000021651302
     * idcardUrl1 : http: //www.baidu.com
     * isDelete : 0
     * name : 公司法人
     * status : 1
     * type : 1
     * userId : 1
     * userName : 13667696651
     */

    private int companyId;
    private long createTime;
    private String idcard;
    private String idcardUrl1;
    private int isDelete;
    private String name;
    private int status;
    private int type;
    private int userId;
    private String userName;

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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardUrl1() {
        return idcardUrl1;
    }

    public void setIdcardUrl1(String idcardUrl1) {
        this.idcardUrl1 = idcardUrl1;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
