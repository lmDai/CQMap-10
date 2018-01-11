package android.zhixun.uiho.com.gissystem.flux.models;

import android.zhixun.uiho.com.gissystem.greendao_gen.CRModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zp on 2016/12/7.
 */

@Entity(active = true)
public class CRModel {
    @Id(autoincrement = true)
    private Long id;

    private String check_unit;//检查单位
    //    private List<String> check_people;
    private String checked_unit;//被检单位
    private String match_person;//配合人员
    private String contacts;//联系人
    private String contact_number;//联系电话
    private String check_time;//检查时间
    private String etTempCheckPerson;//临时参检人员
    private String etOtherZGIdea;//其它的整改意见
    private int situationCheck_one;//检查情况一状态
    private int situationCheck_three;//检查情况三状态
    private int situationCheck_four;//检查情况四状态
    private int situationCheck_five;//检查情况五状态
    private int situationCheck_six;//检查情况六状态
    private int situationCheck_seven;//检查情况七状态
    private int situationCheck_eight;//检查情况八状态
    private int situationCheck_nine;//检查情况九状态
    private int situationCheck_ten;//检查情况十状态
    private int situationCheck_elevn;//检查情况十一状态
    private int situationCheck_twelve;//检查情况十二状态
    private int situationCheck_thirteen;//检查情况十三状态
    private int situationCheck_fourteen;//检查情况十四状态
    private int situationCheck_fifteen;//检查情况十五状态
    private String  Otherproblemsthat;//其他问题说明
    private int rectificationOpinions_one;//整改建议一状态
    private int rectificationOpinions_two;//整改建议二状态
    private int rectificationOpinions_three;//整改建议三状态
    private int rectificationOpinions_four;//整改建议四状态
    private int rectificationOpinions_five;//整改建议五状态
    private int leaderIdea_one;//领导意见一状态
    private int leaderIdea_two;//领导意见二状态
    private String leaderIdeacontent;//领导意见内容
    private int leaderIdea_three;//领导意见三状态
    private boolean isLocal;//是否是本地
    private long saveTime;//点保存按钮时保存的时间
    private long savaServerTime;//从本地保存到服务器点击按钮时的时间
    private int unitId;//所属单位的id

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1250623396)
    private transient CRModelDao myDao;
    @Generated(hash = 1395219103)
    public CRModel(Long id, String check_unit, String checked_unit,
            String match_person, String contacts, String contact_number,
            String check_time, String etTempCheckPerson, String etOtherZGIdea,
            int situationCheck_one, int situationCheck_three,
            int situationCheck_four, int situationCheck_five,
            int situationCheck_six, int situationCheck_seven,
            int situationCheck_eight, int situationCheck_nine,
            int situationCheck_ten, int situationCheck_elevn,
            int situationCheck_twelve, int situationCheck_thirteen,
            int situationCheck_fourteen, int situationCheck_fifteen,
            String Otherproblemsthat, int rectificationOpinions_one,
            int rectificationOpinions_two, int rectificationOpinions_three,
            int rectificationOpinions_four, int rectificationOpinions_five,
            int leaderIdea_one, int leaderIdea_two, String leaderIdeacontent,
            int leaderIdea_three, boolean isLocal, long saveTime,
            long savaServerTime, int unitId) {
        this.id = id;
        this.check_unit = check_unit;
        this.checked_unit = checked_unit;
        this.match_person = match_person;
        this.contacts = contacts;
        this.contact_number = contact_number;
        this.check_time = check_time;
        this.etTempCheckPerson = etTempCheckPerson;
        this.etOtherZGIdea = etOtherZGIdea;
        this.situationCheck_one = situationCheck_one;
        this.situationCheck_three = situationCheck_three;
        this.situationCheck_four = situationCheck_four;
        this.situationCheck_five = situationCheck_five;
        this.situationCheck_six = situationCheck_six;
        this.situationCheck_seven = situationCheck_seven;
        this.situationCheck_eight = situationCheck_eight;
        this.situationCheck_nine = situationCheck_nine;
        this.situationCheck_ten = situationCheck_ten;
        this.situationCheck_elevn = situationCheck_elevn;
        this.situationCheck_twelve = situationCheck_twelve;
        this.situationCheck_thirteen = situationCheck_thirteen;
        this.situationCheck_fourteen = situationCheck_fourteen;
        this.situationCheck_fifteen = situationCheck_fifteen;
        this.Otherproblemsthat = Otherproblemsthat;
        this.rectificationOpinions_one = rectificationOpinions_one;
        this.rectificationOpinions_two = rectificationOpinions_two;
        this.rectificationOpinions_three = rectificationOpinions_three;
        this.rectificationOpinions_four = rectificationOpinions_four;
        this.rectificationOpinions_five = rectificationOpinions_five;
        this.leaderIdea_one = leaderIdea_one;
        this.leaderIdea_two = leaderIdea_two;
        this.leaderIdeacontent = leaderIdeacontent;
        this.leaderIdea_three = leaderIdea_three;
        this.isLocal = isLocal;
        this.saveTime = saveTime;
        this.savaServerTime = savaServerTime;
        this.unitId = unitId;
    }
    @Generated(hash = 2007522685)
    public CRModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCheck_unit() {
        return this.check_unit;
    }
    public void setCheck_unit(String check_unit) {
        this.check_unit = check_unit;
    }
    public String getChecked_unit() {
        return this.checked_unit;
    }
    public void setChecked_unit(String checked_unit) {
        this.checked_unit = checked_unit;
    }
    public String getMatch_person() {
        return this.match_person;
    }
    public void setMatch_person(String match_person) {
        this.match_person = match_person;
    }
    public String getContacts() {
        return this.contacts;
    }
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
    public String getContact_number() {
        return this.contact_number;
    }
    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }
    public String getCheck_time() {
        return this.check_time;
    }
    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }
    public String getEtTempCheckPerson() {
        return this.etTempCheckPerson;
    }
    public void setEtTempCheckPerson(String etTempCheckPerson) {
        this.etTempCheckPerson = etTempCheckPerson;
    }
    public String getEtOtherZGIdea() {
        return this.etOtherZGIdea;
    }
    public void setEtOtherZGIdea(String etOtherZGIdea) {
        this.etOtherZGIdea = etOtherZGIdea;
    }
    public int getSituationCheck_one() {
        return this.situationCheck_one;
    }
    public void setSituationCheck_one(int situationCheck_one) {
        this.situationCheck_one = situationCheck_one;
    }
    public int getSituationCheck_three() {
        return this.situationCheck_three;
    }
    public void setSituationCheck_three(int situationCheck_three) {
        this.situationCheck_three = situationCheck_three;
    }
    public int getSituationCheck_four() {
        return this.situationCheck_four;
    }
    public void setSituationCheck_four(int situationCheck_four) {
        this.situationCheck_four = situationCheck_four;
    }
    public int getSituationCheck_five() {
        return this.situationCheck_five;
    }
    public void setSituationCheck_five(int situationCheck_five) {
        this.situationCheck_five = situationCheck_five;
    }
    public int getSituationCheck_six() {
        return this.situationCheck_six;
    }
    public void setSituationCheck_six(int situationCheck_six) {
        this.situationCheck_six = situationCheck_six;
    }
    public int getSituationCheck_seven() {
        return this.situationCheck_seven;
    }
    public void setSituationCheck_seven(int situationCheck_seven) {
        this.situationCheck_seven = situationCheck_seven;
    }
    public int getSituationCheck_eight() {
        return this.situationCheck_eight;
    }
    public void setSituationCheck_eight(int situationCheck_eight) {
        this.situationCheck_eight = situationCheck_eight;
    }
    public int getSituationCheck_nine() {
        return this.situationCheck_nine;
    }
    public void setSituationCheck_nine(int situationCheck_nine) {
        this.situationCheck_nine = situationCheck_nine;
    }
    public int getSituationCheck_ten() {
        return this.situationCheck_ten;
    }
    public void setSituationCheck_ten(int situationCheck_ten) {
        this.situationCheck_ten = situationCheck_ten;
    }
    public int getSituationCheck_elevn() {
        return this.situationCheck_elevn;
    }
    public void setSituationCheck_elevn(int situationCheck_elevn) {
        this.situationCheck_elevn = situationCheck_elevn;
    }
    public int getSituationCheck_twelve() {
        return this.situationCheck_twelve;
    }
    public void setSituationCheck_twelve(int situationCheck_twelve) {
        this.situationCheck_twelve = situationCheck_twelve;
    }
    public int getSituationCheck_thirteen() {
        return this.situationCheck_thirteen;
    }
    public void setSituationCheck_thirteen(int situationCheck_thirteen) {
        this.situationCheck_thirteen = situationCheck_thirteen;
    }
    public int getSituationCheck_fourteen() {
        return this.situationCheck_fourteen;
    }
    public void setSituationCheck_fourteen(int situationCheck_fourteen) {
        this.situationCheck_fourteen = situationCheck_fourteen;
    }
    public int getSituationCheck_fifteen() {
        return this.situationCheck_fifteen;
    }
    public void setSituationCheck_fifteen(int situationCheck_fifteen) {
        this.situationCheck_fifteen = situationCheck_fifteen;
    }
    public String getOtherproblemsthat() {
        return this.Otherproblemsthat;
    }
    public void setOtherproblemsthat(String Otherproblemsthat) {
        this.Otherproblemsthat = Otherproblemsthat;
    }
    public int getRectificationOpinions_one() {
        return this.rectificationOpinions_one;
    }
    public void setRectificationOpinions_one(int rectificationOpinions_one) {
        this.rectificationOpinions_one = rectificationOpinions_one;
    }
    public int getRectificationOpinions_two() {
        return this.rectificationOpinions_two;
    }
    public void setRectificationOpinions_two(int rectificationOpinions_two) {
        this.rectificationOpinions_two = rectificationOpinions_two;
    }
    public int getRectificationOpinions_three() {
        return this.rectificationOpinions_three;
    }
    public void setRectificationOpinions_three(int rectificationOpinions_three) {
        this.rectificationOpinions_three = rectificationOpinions_three;
    }
    public int getRectificationOpinions_four() {
        return this.rectificationOpinions_four;
    }
    public void setRectificationOpinions_four(int rectificationOpinions_four) {
        this.rectificationOpinions_four = rectificationOpinions_four;
    }
    public int getRectificationOpinions_five() {
        return this.rectificationOpinions_five;
    }
    public void setRectificationOpinions_five(int rectificationOpinions_five) {
        this.rectificationOpinions_five = rectificationOpinions_five;
    }
    public int getLeaderIdea_one() {
        return this.leaderIdea_one;
    }
    public void setLeaderIdea_one(int leaderIdea_one) {
        this.leaderIdea_one = leaderIdea_one;
    }
    public int getLeaderIdea_two() {
        return this.leaderIdea_two;
    }
    public void setLeaderIdea_two(int leaderIdea_two) {
        this.leaderIdea_two = leaderIdea_two;
    }
    public String getLeaderIdeacontent() {
        return this.leaderIdeacontent;
    }
    public void setLeaderIdeacontent(String leaderIdeacontent) {
        this.leaderIdeacontent = leaderIdeacontent;
    }
    public int getLeaderIdea_three() {
        return this.leaderIdea_three;
    }
    public void setLeaderIdea_three(int leaderIdea_three) {
        this.leaderIdea_three = leaderIdea_three;
    }
    public boolean getIsLocal() {
        return this.isLocal;
    }
    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }
    public long getSaveTime() {
        return this.saveTime;
    }
    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }
    public long getSavaServerTime() {
        return this.savaServerTime;
    }
    public void setSavaServerTime(long savaServerTime) {
        this.savaServerTime = savaServerTime;
    }
    public int getUnitId() {
        return this.unitId;
    }
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1029737308)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCRModelDao() : null;
    }

    
   
    
   

    
}
