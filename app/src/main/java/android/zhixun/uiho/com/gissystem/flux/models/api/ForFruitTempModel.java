package android.zhixun.uiho.com.gissystem.flux.models.api;

import java.util.List;

/**
 * Created by parcool on 2017/3/31.
 */

public class ForFruitTempModel{
    private int fruitCategoryId;//分类ID
    private String fruitCategoryName;//分类名称
    private List<List<FruitCategoryAttrsModel>> listFruitCategoryAttrsModel;//该分类的详细属性ListAttrModel

    public int getFruitCategoryId() {
        return fruitCategoryId;
    }

    public void setFruitCategoryId(int fruitCategoryId) {
        this.fruitCategoryId = fruitCategoryId;
    }

    public String getFruitCategoryName() {
        return fruitCategoryName;
    }

    public void setFruitCategoryName(String fruitCategoryName) {
        this.fruitCategoryName = fruitCategoryName;
    }

    public List<List<FruitCategoryAttrsModel>> getListFruitCategoryAttrsModel() {
        return listFruitCategoryAttrsModel;
    }

    public void setListFruitCategoryAttrsModel(List<List<FruitCategoryAttrsModel>> listFruitCategoryAttrsModel) {
        this.listFruitCategoryAttrsModel = listFruitCategoryAttrsModel;
    }
}
