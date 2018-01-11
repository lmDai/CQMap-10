package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/23.
 */

public class FruitCategoryAttrsModel {

    /**
     * attrName : 路线名称
     * attrValue : 多功坝
     * fruitAttrId : 1
     * fruitCategoryAttrId : 1
     * isListShow : 1
     */

    private String attrName;
    private String attrValue;
    private int fruitAttrId;
    private int fruitCategoryAttrId;
    private int isListShow;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public int getFruitAttrId() {
        return fruitAttrId;
    }

    public void setFruitAttrId(int fruitAttrId) {
        this.fruitAttrId = fruitAttrId;
    }

    public int getFruitCategoryAttrId() {
        return fruitCategoryAttrId;
    }

    public void setFruitCategoryAttrId(int fruitCategoryAttrId) {
        this.fruitCategoryAttrId = fruitCategoryAttrId;
    }

    public int getIsListShow() {
        return isListShow;
    }

    public void setIsListShow(int isListShow) {
        this.isListShow = isListShow;
    }
}
