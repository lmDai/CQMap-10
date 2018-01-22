package android.zhixun.uiho.com.gissystem.flux.models;

import java.util.List;

/**
 * Created by simple on 2018/1/22.
 */

public class HandoutFruitModel {

    public long createTime;
    public long cartFruitId;
    public long status;
    public List<FruitAttrList> fruitAttrList;
    public long fruitId;
    public long isDelete;
    public long handoutId;
    public long spareB;
    public long spareA;


    public class FruitAttrList{

        public long fruitCategoryAttrId;
        public String attrNickname;
        public boolean isListShow;
        public String attrValue;
        public int isDelete;
        public long attrLen;
        public int spareB;
        public long fruitCategoryId;
        public int spareA;
        public int isQueryCriteria;
        public String attrType;
        public int createPerson;
        public String attrName;
    }

}
