package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/22.
 */

public class AreaModel {

    /**
     * areaId : 1
     * areaName : 渝中区
     * createTime : 1489742285000
     * isDelete : 0
     */

    private int areaId;
    private String areaName;
    private long createTime;
    private int isDelete;
    private boolean isChecked;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
