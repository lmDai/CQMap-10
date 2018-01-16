package android.zhixun.uiho.com.gissystem.flux.body;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simple on 2017/12/26.
 */

public class ReportHandoutListBody {

    public long fruitCategoryId = -1;//成果类型id
    public String fruitIds;//成果id（多个用逗号拼接，如1,2,3,4
    public long mapNum = -1;//图幅号（模糊查询）
    public List<AttrValueList> attrValueList = new ArrayList<>();//属性值的集合

    public static class AttrValueList {

        public AttrValueList(long fruitCategoryAttrId, String attrValue) {
            this.fruitCategoryAttrId = fruitCategoryAttrId;
            this.attrValue = attrValue;
        }

        public long fruitCategoryAttrId;//成果分类属性id
        public String attrValue;//成果属性值（string时传）
        //attrValueBegin
        //attrValueEnd 成果属性值（date时传，时间戳字符串）
    }

    public void addFruIds(String fruitId){

    }

    public void setFruitCategoryId(long fruitCategoryId) {
        this.fruitCategoryId = fruitCategoryId;
    }
}
