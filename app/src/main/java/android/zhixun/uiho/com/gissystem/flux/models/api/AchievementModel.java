package android.zhixun.uiho.com.gissystem.flux.models.api;

import java.util.List;

/**
 * Created by tanyi on 2017/3/23.
 */

public class AchievementModel {

    /**
     * companyId : 1
     * createTime : 1490064629000
     * handoutFruitList : [{"cartFruitId":2,"createTime":1490169261000,"fruit":{"createPerson":3,"createTime":1490169328000,"fruitCategory":{"categoryName":"三角点","createTime":1490064816000,"fruitCategoryAttrsList":[],"fruitCategoryId":1,"isDelete":0},"fruitCategoryId":1,"fruitId":2,"isDelete":0,"status":1},"fruitId":2,"handoutId":1,"isDelete":0},{"cartFruitId":1,"createTime":1490077801000,"fruit":{"createPerson":3,"createTime":1490065475000,"fruitCategory":{"categoryName":"三角点","createTime":1490064816000,"fruitCategoryAttrsList":[{"attrName":"路线名称","attrValue":"多功坝","fruitAttrId":1,"fruitCategoryAttrId":1,"isListShow":1},{"attrName":"路线编号","attrValue":"大多14","fruitAttrId":2,"fruitCategoryAttrId":2,"isListShow":1},{"attrName":"新图号","attrValue":"H48E009004","fruitAttrId":3,"fruitCategoryAttrId":5,"isListShow":1},{"attrName":"旧图号","attrValue":"H-48-050-B","fruitAttrId":4,"fruitCategoryAttrId":6,"isListShow":1}],"fruitCategoryId":1,"isDelete":0},"fruitCategoryId":1,"fruitId":1,"isDelete":0,"status":1},"fruitId":1,"handoutId":1,"isDelete":0}]
     * handoutId : 1
     * isDelete : 0
     * reportId : 1
     * status : 2
     */

    private int companyId;
    private long createTime;
    private int handoutId;
    private int isDelete;
    private int reportId;
    private int status;
    private List<HandoutFruitListModel> handoutFruitList;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<HandoutFruitListModel> getHandoutFruitList() {
        return handoutFruitList;
    }

    public void setHandoutFruitList(List<HandoutFruitListModel> handoutFruitList) {
        this.handoutFruitList = handoutFruitList;
    }

}
