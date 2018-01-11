package android.zhixun.uiho.com.gissystem.flux.models.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanyi on 2017/3/24.
 */

public class SecrecyInspectFruitListModel implements Parcelable{
    /**
     * createTime : 1490173604000
     * detailsName : 三级点
     * detailsValue : 数量：100；面积：200000m
     * fruitCategoryId : 1
     * isDelete : 0
     * secrecyInspectFruitId : 3
     * secrecyInspectId : 1
     */

    private long createTime;
    private String detailsName;
    private String detailsValue;
    private int fruitCategoryId;
    private int isDelete;
    private int secrecyInspectFruitId;
    private int secrecyInspectId;

    public SecrecyInspectFruitListModel() {
    }

    protected SecrecyInspectFruitListModel(Parcel in) {
        createTime = in.readLong();
        detailsName = in.readString();
        detailsValue = in.readString();
        fruitCategoryId = in.readInt();
        isDelete = in.readInt();
        secrecyInspectFruitId = in.readInt();
        secrecyInspectId = in.readInt();
    }

    public static final Creator<SecrecyInspectFruitListModel> CREATOR = new Creator<SecrecyInspectFruitListModel>() {
        @Override
        public SecrecyInspectFruitListModel createFromParcel(Parcel in) {
            return new SecrecyInspectFruitListModel(in);
        }

        @Override
        public SecrecyInspectFruitListModel[] newArray(int size) {
            return new SecrecyInspectFruitListModel[size];
        }
    };

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDetailsName() {
        return detailsName;
    }

    public void setDetailsName(String detailsName) {
        this.detailsName = detailsName;
    }

    public String getDetailsValue() {
        return detailsValue;
    }

    public void setDetailsValue(String detailsValue) {
        this.detailsValue = detailsValue;
    }

    public int getFruitCategoryId() {
        return fruitCategoryId;
    }

    public void setFruitCategoryId(int fruitCategoryId) {
        this.fruitCategoryId = fruitCategoryId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getSecrecyInspectFruitId() {
        return secrecyInspectFruitId;
    }

    public void setSecrecyInspectFruitId(int secrecyInspectFruitId) {
        this.secrecyInspectFruitId = secrecyInspectFruitId;
    }

    public int getSecrecyInspectId() {
        return secrecyInspectId;
    }

    public void setSecrecyInspectId(int secrecyInspectId) {
        this.secrecyInspectId = secrecyInspectId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(createTime);
        dest.writeString(detailsName);
        dest.writeString(detailsValue);
        dest.writeInt(fruitCategoryId);
        dest.writeInt(isDelete);
        dest.writeInt(secrecyInspectFruitId);
        dest.writeInt(secrecyInspectId);
    }
}
