package android.zhixun.uiho.com.gissystem.flux.models.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanyi on 2017/3/24.
 */

public class SecrecyInspectEntryCompanyListModel implements Parcelable{
    /**
     * createTime : 1489988375000
     * isDelete : 0
     * isPass : 1
     * secrecyInspectEntryCompanyId : 1
     * secrecyInspectEntryId : 1
     * secrecyInspectId : 1
     */

    private long createTime;
    private int isDelete;
    private int isPass;
    private int secrecyInspectEntryCompanyId;
    private int secrecyInspectEntryId;
    private int secrecyInspectId;

    public SecrecyInspectEntryCompanyListModel() {
    }

    protected SecrecyInspectEntryCompanyListModel(Parcel in) {
        createTime = in.readLong();
        isDelete = in.readInt();
        isPass = in.readInt();
        secrecyInspectEntryCompanyId = in.readInt();
        secrecyInspectEntryId = in.readInt();
        secrecyInspectId = in.readInt();
    }

    public static final Creator<SecrecyInspectEntryCompanyListModel> CREATOR = new Creator<SecrecyInspectEntryCompanyListModel>() {
        @Override
        public SecrecyInspectEntryCompanyListModel createFromParcel(Parcel in) {
            return new SecrecyInspectEntryCompanyListModel(in);
        }

        @Override
        public SecrecyInspectEntryCompanyListModel[] newArray(int size) {
            return new SecrecyInspectEntryCompanyListModel[size];
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

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }

    public int getSecrecyInspectEntryCompanyId() {
        return secrecyInspectEntryCompanyId;
    }

    public void setSecrecyInspectEntryCompanyId(int secrecyInspectEntryCompanyId) {
        this.secrecyInspectEntryCompanyId = secrecyInspectEntryCompanyId;
    }

    public int getSecrecyInspectEntryId() {
        return secrecyInspectEntryId;
    }

    public void setSecrecyInspectEntryId(int secrecyInspectEntryId) {
        this.secrecyInspectEntryId = secrecyInspectEntryId;
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
        dest.writeInt(isDelete);
        dest.writeInt(isPass);
        dest.writeInt(secrecyInspectEntryCompanyId);
        dest.writeInt(secrecyInspectEntryId);
        dest.writeInt(secrecyInspectId);
    }
}
