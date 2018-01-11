package android.zhixun.uiho.com.gissystem.flux.models.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanyi on 2017/3/24.
 */

public class SecrecyInspectImgListModel implements Parcelable{
    /**
     * createTime : 1489979847000
     * isDelete : 0
     * secrecyInspectId : 1
     * secrecyInspectImgId : 2
     * url : http://www.baidu.com/img/bd_logo1.png
     */

    private long createTime;
    private int isDelete;
    private int secrecyInspectId;
    private int secrecyInspectImgId;
    private String url;

    public SecrecyInspectImgListModel() {
    }

    protected SecrecyInspectImgListModel(Parcel in) {
        createTime = in.readLong();
        isDelete = in.readInt();
        secrecyInspectId = in.readInt();
        secrecyInspectImgId = in.readInt();
        url = in.readString();
    }

    public static final Creator<SecrecyInspectImgListModel> CREATOR = new Creator<SecrecyInspectImgListModel>() {
        @Override
        public SecrecyInspectImgListModel createFromParcel(Parcel in) {
            return new SecrecyInspectImgListModel(in);
        }

        @Override
        public SecrecyInspectImgListModel[] newArray(int size) {
            return new SecrecyInspectImgListModel[size];
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

    public int getSecrecyInspectId() {
        return secrecyInspectId;
    }

    public void setSecrecyInspectId(int secrecyInspectId) {
        this.secrecyInspectId = secrecyInspectId;
    }

    public int getSecrecyInspectImgId() {
        return secrecyInspectImgId;
    }

    public void setSecrecyInspectImgId(int secrecyInspectImgId) {
        this.secrecyInspectImgId = secrecyInspectImgId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(createTime);
        dest.writeInt(isDelete);
        dest.writeInt(secrecyInspectId);
        dest.writeInt(secrecyInspectImgId);
        dest.writeString(url);
    }
}
