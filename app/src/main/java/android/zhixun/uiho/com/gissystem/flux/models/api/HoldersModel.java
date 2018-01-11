package android.zhixun.uiho.com.gissystem.flux.models.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanyi on 2017/3/21.
 * 持证人
 */

public class HoldersModel implements Parcelable {

    /**
     * cardBeginTime : 1489911342000
     * cardEndTime : 1496564146000
     * cardSource : 11111
     * companyId : 1
     * createTime : 1489911363000
     * idcard : 11412421
     * idcardUrl1 : 身份证1
     * idcardUrl2 : 身份证1
     * isDelete : 0
     * isHoldCard : 1
     * name : 公司1持证人
     * phone : 123123123
     * status : 1
     * type : 2
     * userId : 2
     * userName : 10086
     */

    private long cardBeginTime;
    private long cardEndTime;
    private int cardSource;
    private int companyId;
    private long createTime;
    private String idcard;
    private String idcardUrl1;
    private String idcardUrl2;
    private int isDelete;
    private int isHoldCard;
    private String name;
    private String phone;
    private int status;
    private int type;
    private int userId;
    private String userName;

    public HoldersModel() {
    }

    protected HoldersModel(Parcel in) {
        cardBeginTime = in.readLong();
        cardEndTime = in.readLong();
        cardSource = in.readInt();
        companyId = in.readInt();
        createTime = in.readLong();
        idcard = in.readString();
        idcardUrl1 = in.readString();
        idcardUrl2 = in.readString();
        isDelete = in.readInt();
        isHoldCard = in.readInt();
        name = in.readString();
        phone = in.readString();
        status = in.readInt();
        type = in.readInt();
        userId = in.readInt();
        userName = in.readString();
    }

    public static final Creator<HoldersModel> CREATOR = new Creator<HoldersModel>() {
        @Override
        public HoldersModel createFromParcel(Parcel in) {
            return new HoldersModel(in);
        }

        @Override
        public HoldersModel[] newArray(int size) {
            return new HoldersModel[size];
        }
    };

    public long getCardBeginTime() {
        return cardBeginTime;
    }

    public void setCardBeginTime(long cardBeginTime) {
        this.cardBeginTime = cardBeginTime;
    }

    public long getCardEndTime() {
        return cardEndTime;
    }

    public void setCardEndTime(long cardEndTime) {
        this.cardEndTime = cardEndTime;
    }

    public int getCardSource() {
        return cardSource;
    }

    public void setCardSource(int cardSource) {
        this.cardSource = cardSource;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardUrl1() {
        return idcardUrl1;
    }

    public void setIdcardUrl1(String idcardUrl1) {
        this.idcardUrl1 = idcardUrl1;
    }

    public String getIdcardUrl2() {
        return idcardUrl2;
    }

    public void setIdcardUrl2(String idcardUrl2) {
        this.idcardUrl2 = idcardUrl2;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsHoldCard() {
        return isHoldCard;
    }

    public void setIsHoldCard(int isHoldCard) {
        this.isHoldCard = isHoldCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(cardBeginTime);
        dest.writeLong(cardEndTime);
        dest.writeInt(cardSource);
        dest.writeInt(companyId);
        dest.writeLong(createTime);
        dest.writeString(idcard);
        dest.writeString(idcardUrl1);
        dest.writeString(idcardUrl2);
        dest.writeInt(isDelete);
        dest.writeInt(isHoldCard);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeInt(status);
        dest.writeInt(type);
        dest.writeInt(userId);
        dest.writeString(userName);
    }
}
