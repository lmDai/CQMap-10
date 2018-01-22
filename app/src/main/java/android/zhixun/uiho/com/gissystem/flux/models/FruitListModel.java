package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by simple on 2018/1/22.
 */

public class FruitListModel {

    public long fruitCategoryId;
    public String fruitAttrList;
    public long fruitId;

    public static class FruitAttrList{
        public long sort;
        public String attrName;
        public String attrValue;
    }
}
