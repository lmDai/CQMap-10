package android.zhixun.uiho.com.gissystem.flux.models;

import java.util.List;

/**
 * Created by simple on 2017/12/25.
 */

public class GethandoutConditionByFCModel {

    public List<FruitCateGoryAttrVal> fruitCateGoryAttrVal;
    public int spareB;
    public long fruitCategoryAttrId;
    public String attrType;
    public String attrName;

    public static class FruitCateGoryAttrVal {

        public FruitCateGoryAttrVal() {
        }

        public FruitCateGoryAttrVal(String attrValue) {
            this.attrValue = attrValue;
        }

        public int type;
        public String attrValue;
        public boolean selected;
    }
}
