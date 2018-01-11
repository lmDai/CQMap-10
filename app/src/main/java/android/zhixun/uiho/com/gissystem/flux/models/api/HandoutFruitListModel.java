package android.zhixun.uiho.com.gissystem.flux.models.api;

import java.util.List;

/**
 * Created by tanyi on 2017/3/23.
 * 分发成果列表
 */

public class HandoutFruitListModel {
    /**
     * cartFruitId : 2
     * createTime : 1490169261000
     * fruit : {"createPerson":3,"createTime":1490169328000,"fruitCategory":{"categoryName":"三角点","createTime":1490064816000,"fruitCategoryAttrsList":[],"fruitCategoryId":1,"isDelete":0},"fruitCategoryId":1,"fruitId":2,"isDelete":0,"status":1}
     * fruitId : 2
     * handoutId : 1
     * isDelete : 0
     */

    private int cartFruitId;
    private long createTime;
    private FruitModel fruit;
    private int fruitId;
    private int handoutId;
    private int isDelete;

    public int getCartFruitId() {
        return cartFruitId;
    }

    public void setCartFruitId(int cartFruitId) {
        this.cartFruitId = cartFruitId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public FruitModel getFruit() {
        return fruit;
    }

    public void setFruit(FruitModel fruit) {
        this.fruit = fruit;
    }

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
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

}
