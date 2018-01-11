package android.zhixun.uiho.com.gissystem.flux.models.api;

import java.util.List;

/**
 * Created by tanyi on 2017/3/23.
 * 成果类别
 */

public class FruitCategoryModel {
    /**
     * categoryName : 三角点
     * createTime : 1490064816000
     * fruitCategoryAttrsList : []
     * fruitCategoryId : 1
     * isDelete : 0
     */

    private String categoryName;
    private long createTime;
    private int fruitCategoryId;
    private int isDelete;
    private List<FruitCategoryAttrsModel> fruitCategoryAttrsList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getFruitCategoryId() {
        return fruitCategoryId;
    }

    public void setFruitCategoryId(int fruitCategoryId) {
        this.fruitCategoryId = fruitCategoryId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public List<FruitCategoryAttrsModel> getFruitCategoryAttrsList() {
        return fruitCategoryAttrsList;
    }

    public void setFruitCategoryAttrsList(List<FruitCategoryAttrsModel> fruitCategoryAttrsList) {
        this.fruitCategoryAttrsList = fruitCategoryAttrsList;
    }
}
