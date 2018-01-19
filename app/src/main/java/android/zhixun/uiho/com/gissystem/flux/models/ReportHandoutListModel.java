package android.zhixun.uiho.com.gissystem.flux.models;

import java.util.List;

/**
 * Created by simple on 2017/12/26.
 */

public class ReportHandoutListModel {

    public long reportCompanyId;
    public List<FruitCategoryList> fruitCategoryList;
    public long handoutId;
    public ReportHandout reportHandout;
    public String reportNo;
    public String purposeUse;
    public String companyName;
    public long reportId;

    public class FruitCategoryList {

        public List<FruitList> fruitList;
        public int isDelete;
        public String categoryName;
        public long fruitCategoryId;
        public int spareA;
        public int createPerson;
        public int mapType;//1的话就是点查询，否则就是面查询

        public class FruitList {

            public FruitHandout fruitHandout;
            public long fruitCategoryId;
            public List<FruitAttrList> fruitAttrList;
            public long fruitId;

            public class FruitHandout {

                public long cartFruitId;
                public int status;
                public long fruitId;
                public int isDelete;
                public long handoutId;
                public long spareB;
                public long spareA;

            }

            public class FruitAttrList {

                public long attrId;
                public long spareB;
                public long spareA;
                public long isQueryCriteria;
                public String attrType;
                public boolean isListShow;
                public String attrName;
                public String attrValue;

            }
        }


    }

    public class ReportHandout {
        public long createTime;
        public int status;
    }
}
