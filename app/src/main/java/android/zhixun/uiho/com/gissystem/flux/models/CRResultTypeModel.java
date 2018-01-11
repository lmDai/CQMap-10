package android.zhixun.uiho.com.gissystem.flux.models;

import android.zhixun.uiho.com.gissystem.greendao_gen.CRResultTypeModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zp on 2016/12/8.
 */
@Entity (active = true)
public class CRResultTypeModel {
    @Id (autoincrement = true)
    private Long id;

    private long CRKey;//保密检查登记表的ID（外键）
    private String RTypeContent;//成果类型内容
    private int index;//Config.RESULT_TYPE的索引

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 46194559)
    private transient CRResultTypeModelDao myDao;
    @Generated(hash = 1864322671)
    public CRResultTypeModel(Long id, long CRKey, String RTypeContent, int index) {
        this.id = id;
        this.CRKey = CRKey;
        this.RTypeContent = RTypeContent;
        this.index = index;
    }
    @Generated(hash = 171855511)
    public CRResultTypeModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getCRKey() {
        return this.CRKey;
    }
    public void setCRKey(long CRKey) {
        this.CRKey = CRKey;
    }
    public String getRTypeContent() {
        return this.RTypeContent;
    }
    public void setRTypeContent(String RTypeContent) {
        this.RTypeContent = RTypeContent;
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
    @Generated(hash = 863097833)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCRResultTypeModelDao() : null;
    }
    public int getIndex() {
        return this.index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

}
