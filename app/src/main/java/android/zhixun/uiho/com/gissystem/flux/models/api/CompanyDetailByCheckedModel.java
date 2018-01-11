package android.zhixun.uiho.com.gissystem.flux.models.api;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by tanyi on 2017/3/24.
 */

public class CompanyDetailByCheckedModel implements Parcelable{

    /**
     * companyId : 1
     * cooperatePersons : 1,3
     * createTime : 1489979415000
     * isAdoptLeader : 1
     * isDelete : 0
     * isLocal : 1
     * isSignCompany : 1
     * isSignLeader : 1
     * leaderReply : 领导批复内容
     * personName : 张三
     * phone : 110
     * rectificationReportUrl : 整改报告url
     * registerStatus : 1
     * secrecyCompanyId : 0
     * secrecyInspectEntryCompanyList : [{"createTime":1489988375000,"isDelete":0,"isPass":1,"secrecyInspectEntryCompanyId":1,"secrecyInspectEntryId":1,"secrecyInspectId":1},{"createTime":1489988375000,"isDelete":0,"isPass":0,"secrecyInspectEntryCompanyId":2,"secrecyInspectEntryId":2,"secrecyInspectId":1}]
     * secrecyInspectEntryList : [{"createTime":1489987277000,"isDelete":0,"secrecyInspectEntryId":1,"sort":1,"title":"是否存在涉密测绘成果失涉密情况","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":2,"sort":2,"title":"被检单位拥有的涉密测绘成果种类","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":3,"sort":3,"title":"保密机构是否健全","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":4,"sort":4,"title":"保密规章制度是否健全","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":5,"sort":5,"title":"是否配备\u201c三铁一器\u201d及监控设备","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":6,"sort":6,"title":"是否建立涉密成果管理台账","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":7,"sort":7,"title":"是否有专人管理涉密成果","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":8,"sort":8,"title":"是否与涉密人员签订保密协议","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":9,"sort":9,"title":"是否存在涉密测绘成果擅自转让情况","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":10,"sort":10,"title":"移动存储介质管理是否符合要求","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":11,"sort":11,"title":"涉密计算机是否连入互联网","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":12,"sort":12,"title":"涉密计算机密码是否按要求设置动态密码","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":13,"sort":13,"title":"涉密计算机端口、串口是否符合保密规定","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":14,"sort":14,"title":"损毁测绘地理信息成果是否按规定程序报批市规划局备案及到指定位置销毁","type":1},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":15,"sort":15,"title":"核心岗位涉密人员持证情况","type":1}]
     * secrecyInspectFruitList : [{"createTime":1490173604000,"detailsName":"三级点","detailsValue":"数量：100；面积：200000m","fruitCategoryId":1,"isDelete":0,"secrecyInspectFruitId":3,"secrecyInspectId":1}]
     * secrecyInspectId : 1
     * secrecyInspectImgList : [{"createTime":1489979847000,"isDelete":0,"secrecyInspectId":1,"secrecyInspectImgId":2,"url":"http://www.baidu.com/img/bd_logo1.png"},{"createTime":1489979844000,"isDelete":0,"secrecyInspectId":1,"secrecyInspectImgId":1,"url":"http://www.baidu.com/img/bd_logo1.png"}]
     * secrecyInspectProposalList : [{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":16,"sort":1,"title":"是否需要进一步健全保密制度","type":2},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":17,"sort":2,"title":"是否需要进一步健全保密机构","type":2},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":18,"sort":3,"title":"是否需要进一步落实保密措施","type":2},{"createTime":1489987316000,"isDelete":0,"secrecyInspectEntryId":19,"sort":4,"title":"核心涉密人员是否需要参加培训","type":2},{"createTime":1490160159000,"isDelete":0,"secrecyInspectEntryId":20,"sort":5,"title":"是否需要进一步全员开展培训","type":2}]
     * secrecyPersonList : [{"name":"检查人员1","userId":3}]
     * secrecyPersons : 3
     * secrecyTime : 1489979536000
     * userId : 2
     */

    private int companyId;
    private String companyName;
    private String cooperatePersons;
    private long createTime;
    private int isAdoptLeader;
    private String secrecyPersonsInterim;
    private int isDelete;
    private int isLocal;
    private int isSignCompany;
    private int isSignLeader;
    private String leaderReply;
    private String personName;
    private String phone;
    private String rectificationReportUrl;
    private int registerStatus;
    private int secrecyCompanyId;
    private int secrecyInspectId;
    private String secrecyPersons;
    private long secrecyTime;
    private int userId;
    private List<SecrecyInspectEntryCompanyListModel> secrecyInspectEntryCompanyList;
    private List<SecrecyInspectEntryListModel> secrecyInspectEntryList;
    private List<SecrecyInspectFruitListModel> secrecyInspectFruitList;
    private List<SecrecyInspectImgListModel> secrecyInspectImgList;
    private List<SecrecyInspectProposalListModel> secrecyInspectProposalList;
    private List<SecrecyPersonListModel> secrecyPersonList;
    private String rectificationSuggestions;

    public CompanyDetailByCheckedModel() {
    }


    protected CompanyDetailByCheckedModel(Parcel in) {
        companyId = in.readInt();
        companyName = in.readString();
        cooperatePersons = in.readString();
        createTime = in.readLong();
        isAdoptLeader = in.readInt();
        secrecyPersonsInterim = in.readString();
        isDelete = in.readInt();
        isLocal = in.readInt();
        isSignCompany = in.readInt();
        isSignLeader = in.readInt();
        leaderReply = in.readString();
        personName = in.readString();
        phone = in.readString();
        rectificationReportUrl = in.readString();
        registerStatus = in.readInt();
        secrecyCompanyId = in.readInt();
        secrecyInspectId = in.readInt();
        secrecyPersons = in.readString();
        secrecyTime = in.readLong();
        userId = in.readInt();
        secrecyInspectEntryCompanyList = in.createTypedArrayList(SecrecyInspectEntryCompanyListModel.CREATOR);
        secrecyInspectEntryList = in.createTypedArrayList(SecrecyInspectEntryListModel.CREATOR);
        secrecyInspectFruitList = in.createTypedArrayList(SecrecyInspectFruitListModel.CREATOR);
        secrecyInspectImgList = in.createTypedArrayList(SecrecyInspectImgListModel.CREATOR);
        secrecyInspectProposalList = in.createTypedArrayList(SecrecyInspectProposalListModel.CREATOR);
        secrecyPersonList = in.createTypedArrayList(SecrecyPersonListModel.CREATOR);
        rectificationSuggestions = in.readString();
    }

    public static final Creator<CompanyDetailByCheckedModel> CREATOR = new Creator<CompanyDetailByCheckedModel>() {
        @Override
        public CompanyDetailByCheckedModel createFromParcel(Parcel in) {
            return new CompanyDetailByCheckedModel(in);
        }

        @Override
        public CompanyDetailByCheckedModel[] newArray(int size) {
            return new CompanyDetailByCheckedModel[size];
        }
    };

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCooperatePersons() {
        return cooperatePersons;
    }

    public void setCooperatePersons(String cooperatePersons) {
        this.cooperatePersons = cooperatePersons;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIsAdoptLeader() {
        return isAdoptLeader;
    }

    public void setIsAdoptLeader(int isAdoptLeader) {
        this.isAdoptLeader = isAdoptLeader;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(int isLocal) {
        this.isLocal = isLocal;
    }

    public int getIsSignCompany() {
        return isSignCompany;
    }

    public void setIsSignCompany(int isSignCompany) {
        this.isSignCompany = isSignCompany;
    }

    public int getIsSignLeader() {
        return isSignLeader;
    }

    public void setIsSignLeader(int isSignLeader) {
        this.isSignLeader = isSignLeader;
    }

    public String getLeaderReply() {
        return leaderReply;
    }

    public void setLeaderReply(String leaderReply) {
        this.leaderReply = leaderReply;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRectificationReportUrl() {
        return rectificationReportUrl;
    }

    public void setRectificationReportUrl(String rectificationReportUrl) {
        this.rectificationReportUrl = rectificationReportUrl;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public int getSecrecyCompanyId() {
        return secrecyCompanyId;
    }

    public void setSecrecyCompanyId(int secrecyCompanyId) {
        this.secrecyCompanyId = secrecyCompanyId;
    }

    public int getSecrecyInspectId() {
        return secrecyInspectId;
    }

    public void setSecrecyInspectId(int secrecyInspectId) {
        this.secrecyInspectId = secrecyInspectId;
    }

    public String getSecrecyPersons() {
        return secrecyPersons;
    }

    public void setSecrecyPersons(String secrecyPersons) {
        this.secrecyPersons = secrecyPersons;
    }

    public long getSecrecyTime() {
        return secrecyTime;
    }

    public void setSecrecyTime(long secrecyTime) {
        this.secrecyTime = secrecyTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<SecrecyInspectEntryCompanyListModel> getSecrecyInspectEntryCompanyList() {
        return secrecyInspectEntryCompanyList;
    }

    public void setSecrecyInspectEntryCompanyList(List<SecrecyInspectEntryCompanyListModel> secrecyInspectEntryCompanyList) {
        this.secrecyInspectEntryCompanyList = secrecyInspectEntryCompanyList;
    }

    public List<SecrecyInspectEntryListModel> getSecrecyInspectEntryList() {
        return secrecyInspectEntryList;
    }

    public void setSecrecyInspectEntryList(List<SecrecyInspectEntryListModel> secrecyInspectEntryList) {
        this.secrecyInspectEntryList = secrecyInspectEntryList;
    }

    public List<SecrecyInspectFruitListModel> getSecrecyInspectFruitList() {
        return secrecyInspectFruitList;
    }

    public void setSecrecyInspectFruitList(List<SecrecyInspectFruitListModel> secrecyInspectFruitList) {
        this.secrecyInspectFruitList = secrecyInspectFruitList;
    }

    public List<SecrecyInspectImgListModel> getSecrecyInspectImgList() {
        return secrecyInspectImgList;
    }

    public void setSecrecyInspectImgList(List<SecrecyInspectImgListModel> secrecyInspectImgList) {
        this.secrecyInspectImgList = secrecyInspectImgList;
    }

    public List<SecrecyInspectProposalListModel> getSecrecyInspectProposalList() {
        return secrecyInspectProposalList;
    }

    public void setSecrecyInspectProposalList(List<SecrecyInspectProposalListModel> secrecyInspectProposalList) {
        this.secrecyInspectProposalList = secrecyInspectProposalList;
    }

    public List<SecrecyPersonListModel> getSecrecyPersonList() {
        return secrecyPersonList;
    }

    public void setSecrecyPersonList(List<SecrecyPersonListModel> secrecyPersonList) {
        this.secrecyPersonList = secrecyPersonList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSecrecyPersonsInterim() {
        return secrecyPersonsInterim;
    }

    public void setSecrecyPersonsInterim(String secrecyPersonsInterim) {
        this.secrecyPersonsInterim = secrecyPersonsInterim;
    }

    public String getRectificationSuggestions() {
        return rectificationSuggestions;
    }

    public void setRectificationSuggestions(String rectificationSuggestions) {
        this.rectificationSuggestions = rectificationSuggestions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(companyId);
        dest.writeString(companyName);
        dest.writeString(cooperatePersons);
        dest.writeLong(createTime);
        dest.writeInt(isAdoptLeader);
        dest.writeString(secrecyPersonsInterim);
        dest.writeInt(isDelete);
        dest.writeInt(isLocal);
        dest.writeInt(isSignCompany);
        dest.writeInt(isSignLeader);
        dest.writeString(leaderReply);
        dest.writeString(personName);
        dest.writeString(phone);
        dest.writeString(rectificationReportUrl);
        dest.writeInt(registerStatus);
        dest.writeInt(secrecyCompanyId);
        dest.writeInt(secrecyInspectId);
        dest.writeString(secrecyPersons);
        dest.writeLong(secrecyTime);
        dest.writeInt(userId);
        dest.writeTypedList(secrecyInspectEntryCompanyList);
        dest.writeTypedList(secrecyInspectEntryList);
        dest.writeTypedList(secrecyInspectFruitList);
        dest.writeTypedList(secrecyInspectImgList);
        dest.writeTypedList(secrecyInspectProposalList);
        dest.writeTypedList(secrecyPersonList);
        dest.writeString(rectificationSuggestions);
    }
}
