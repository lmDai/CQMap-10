package android.zhixun.uiho.com.gissystem.flux.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRSubmitServerModelDao;

/**
 * Created by zp on 2016/12/12.
 */
@Entity (active = true)
public class CRSubmitServerModel {
    @Id (autoincrement = true)
    private Long id;

    private long cdKey;//存的提交到了服务器的CRModel的 ID。

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1496656637)
    private transient CRSubmitServerModelDao myDao;

    @Generated(hash = 2099063487)
    public CRSubmitServerModel(Long id, long cdKey) {
        this.id = id;
        this.cdKey = cdKey;
    }

    @Generated(hash = 919680234)
    public CRSubmitServerModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCdKey() {
        return this.cdKey;
    }

    public void setCdKey(long cdKey) {
        this.cdKey = cdKey;
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
    @Generated(hash = 9967049)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCRSubmitServerModelDao() : null;
    }
    
}
