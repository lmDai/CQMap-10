package android.zhixun.uiho.com.gissystem.flux.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortTwoModelDao;

/**
 * Created by zp on 2016/12/19.
 */
@Entity (active = true)
public class CGSortTwoModel {
    @Id (autoincrement = true)
    private Long id;

    private String call;//点名

    private String dit;//点号

    private String rank;//等级

    private String HCJZ;//高程基准

    private int CRKey;//关联公司的外键

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1592376628)
    private transient CGSortTwoModelDao myDao;

    @Generated(hash = 239290233)
    public CGSortTwoModel(Long id, String call, String dit, String rank,
            String HCJZ, int CRKey) {
        this.id = id;
        this.call = call;
        this.dit = dit;
        this.rank = rank;
        this.HCJZ = HCJZ;
        this.CRKey = CRKey;
    }

    @Generated(hash = 558418522)
    public CGSortTwoModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCall() {
        return this.call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getDit() {
        return this.dit;
    }

    public void setDit(String dit) {
        this.dit = dit;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getHCJZ() {
        return this.HCJZ;
    }

    public void setHCJZ(String HCJZ) {
        this.HCJZ = HCJZ;
    }

    public int getCRKey() {
        return this.CRKey;
    }

    public void setCRKey(int CRKey) {
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
    @Generated(hash = 1392549228)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCGSortTwoModelDao() : null;
    }

}
