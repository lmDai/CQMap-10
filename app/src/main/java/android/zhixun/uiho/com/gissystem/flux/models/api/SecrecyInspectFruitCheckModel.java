package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/24.
 */

public class SecrecyInspectFruitCheckModel {


    /**
     * detailsName : 三级点
     * detailsValue : 数量：100；面积：200000m
     * fruitCategoryId : 1
     */

    private String detailsName;
    private String detailsValue;
    private Integer fruitCategoryId;

    public String getDetailsName() {
        return detailsName;
    }

    public void setDetailsName(String detailsName) {
        this.detailsName = detailsName;
    }

    public String getDetailsValue() {
        return detailsValue;
    }

    public void setDetailsValue(String detailsValue) {
        this.detailsValue = detailsValue;
    }

    public Integer getFruitCategoryId() {
        return fruitCategoryId;
    }

    public void setFruitCategoryId(Integer fruitCategoryId) {
        this.fruitCategoryId = fruitCategoryId;
    }
}
