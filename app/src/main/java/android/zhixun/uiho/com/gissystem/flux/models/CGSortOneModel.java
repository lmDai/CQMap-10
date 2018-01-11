package android.zhixun.uiho.com.gissystem.flux.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortOneModelDao;

/**
 * Created by zp on 2016/12/19.
 */
 @Entity (active = true)
public class CGSortOneModel {
    @Id (autoincrement = true)
    private Long id;

    private String XTH;//新图号

    private String SCDate;//生产时间

    private String DDJZ;//大地基准

    private String SJGS;//数据格式

    private long CRKey;//用来存对应公司的id

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2048373801)
    private transient CGSortOneModelDao myDao;

    @Generated(hash = 20121416)
    public CGSortOneModel(Long id, String XTH, String SCDate, String DDJZ,
            String SJGS, long CRKey) {
        this.id = id;
        this.XTH = XTH;
        this.SCDate = SCDate;
        this.DDJZ = DDJZ;
        this.SJGS = SJGS;
        this.CRKey = CRKey;
    }

    @Generated(hash = 2118602921)
    public CGSortOneModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXTH() {
        return this.XTH;
    }

    public void setXTH(String XTH) {
        this.XTH = XTH;
    }

    public String getSCDate() {
        return this.SCDate;
    }

    public void setSCDate(String SCDate) {
        this.SCDate = SCDate;
    }

    public String getDDJZ() {
        return this.DDJZ;
    }

    public void setDDJZ(String DDJZ) {
        this.DDJZ = DDJZ;
    }

    public String getSJGS() {
        return this.SJGS;
    }

    public void setSJGS(String SJGS) {
        this.SJGS = SJGS;
    }

    public long getCRKey() {
        return this.CRKey;
    }

    public void setCRKey(long CRKey) {
        this.CRKey = CRKey;
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
    @Generated(hash = 300167345)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCGSortOneModelDao() : null;
    }

//    private String CGDYMC;//成果单元名称
//
//    private String JTH;//旧图号
//
//    private String BLC;//比例尺
//
//    private String CGLX;//成果类型
//
//    private String BB;//版本
//
//    private String SSXM;//所属项目
//
//    private String MJ;//密级
//
//    private String GCJZ;//高程基准
//
//    private String TY;//投影
//
//    private String FFDW;//分发单位

    
}
