package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/22.
 */

public class IndustryCategoryModel {

    /**
     * createTime : 1489902718000
     * industryCategoryId : 1
     * industryCategoryName : 行业名1
     * isDelete : 0
     * parentId : 0
     */

    private long createTime;
    private int industryCategoryId;
    private String industryCategoryName;
    private int isDelete;
    private int parentId;
    private boolean isChecked;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIndustryCategoryId() {
        return industryCategoryId;
    }

    public void setIndustryCategoryId(int industryCategoryId) {
        this.industryCategoryId = industryCategoryId;
    }

    public String getIndustryCategoryName() {
        return industryCategoryName;
    }

    public void setIndustryCategoryName(String industryCategoryName) {
        this.industryCategoryName = industryCategoryName;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
