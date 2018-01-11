package android.zhixun.uiho.com.gissystem.flux.models.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanyi on 2017/3/24.
 */

public class SecrecyPersonListModel implements Parcelable{
    /**
     * name : 检查人员1
     * userId : 3
     */

    private String name;
    private int userId;

    protected SecrecyPersonListModel(Parcel in) {
        name = in.readString();
        userId = in.readInt();
    }

    public SecrecyPersonListModel() {
    }

    public static final Creator<SecrecyPersonListModel> CREATOR = new Creator<SecrecyPersonListModel>() {
        @Override
        public SecrecyPersonListModel createFromParcel(Parcel in) {
            return new SecrecyPersonListModel(in);
        }

        @Override
        public SecrecyPersonListModel[] newArray(int size) {
            return new SecrecyPersonListModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(userId);
    }
}
