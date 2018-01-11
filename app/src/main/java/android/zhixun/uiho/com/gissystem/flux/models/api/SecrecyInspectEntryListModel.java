package android.zhixun.uiho.com.gissystem.flux.models.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanyi on 2017/3/24.
 */

public class SecrecyInspectEntryListModel implements Parcelable{
    /**
     * createTime : 1489987277000
     * isDelete : 0
     * secrecyInspectEntryId : 1
     * sort : 1
     * title : 是否存在涉密测绘成果失涉密情况
     * type : 1
     */

    private long createTime;
    private int isDelete;
    private int secrecyInspectEntryId;
    private int sort;
    private String title;
    private int type;

    public SecrecyInspectEntryListModel() {
    }

    protected SecrecyInspectEntryListModel(Parcel in) {
        createTime = in.readLong();
        isDelete = in.readInt();
        secrecyInspectEntryId = in.readInt();
        sort = in.readInt();
        title = in.readString();
        type = in.readInt();
    }

    public static final Creator<SecrecyInspectEntryListModel> CREATOR = new Creator<SecrecyInspectEntryListModel>() {
        @Override
        public SecrecyInspectEntryListModel createFromParcel(Parcel in) {
            return new SecrecyInspectEntryListModel(in);
        }

        @Override
        public SecrecyInspectEntryListModel[] newArray(int size) {
            return new SecrecyInspectEntryListModel[size];
        }
    };

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

    public int getSecrecyInspectEntryId() {
        return secrecyInspectEntryId;
    }

    public void setSecrecyInspectEntryId(int secrecyInspectEntryId) {
        this.secrecyInspectEntryId = secrecyInspectEntryId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(createTime);
        dest.writeInt(isDelete);
        dest.writeInt(secrecyInspectEntryId);
        dest.writeInt(sort);
        dest.writeString(title);
        dest.writeInt(type);
    }
}
