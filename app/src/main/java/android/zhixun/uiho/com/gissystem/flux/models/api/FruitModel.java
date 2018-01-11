package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/23.
 * 成果
 */

public class FruitModel {
    /**
     * createPerson : 3
     * createTime : 1490169328000
     * fruitCategory : {"categoryName":"三角点","createTime":1490064816000,"fruitCategoryAttrsList":[],"fruitCategoryId":1,"isDelete":0}
     * fruitCategoryId : 1
     * fruitId : 2
     * isDelete : 0
     * status : 1
     */

    private int createPerson;
    private long createTime;
    private FruitCategoryModel fruitCategory;
    private int fruitCategoryId;
    private int fruitId;
    private int isDelete;
    private int status;

    public int getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(int createPerson) {
        this.createPerson = createPerson;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public FruitCategoryModel getFruitCategory() {
        return fruitCategory;
    }

    public void setFruitCategory(FruitCategoryModel fruitCategory) {
        this.fruitCategory = fruitCategory;
    }

    public int getFruitCategoryId() {
        return fruitCategoryId;
    }

    public void setFruitCategoryId(int fruitCategoryId) {
        this.fruitCategoryId = fruitCategoryId;
    }

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
