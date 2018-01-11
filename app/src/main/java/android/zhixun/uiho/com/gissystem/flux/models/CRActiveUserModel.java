package android.zhixun.uiho.com.gissystem.flux.models;

import android.zhixun.uiho.com.gissystem.greendao_gen.CRActiveUserModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by zp on 2016/12/12.
 */
@Entity (active = true)
public class CRActiveUserModel {
    @Id (autoincrement = true)
    private Long id;//存当前用户的资料信息

    private long cdKey;//存用户表的ID
    private Long userId;//
    private int userUnitId;//用户的所属单位id
    @Unique
    private String Nickname;//存用户表的名字

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1016403938)
    private transient CRActiveUserModelDao myDao;

    @Generated(hash = 782562150)
    public CRActiveUserModel(Long id, long cdKey, Long userId, int userUnitId,
            String Nickname) {
        this.id = id;
        this.cdKey = cdKey;
        this.userId = userId;
        this.userUnitId = userUnitId;
        this.Nickname = Nickname;
    }

    @Generated(hash = 375313950)
    public CRActiveUserModel() {
    }

    public long getCdKey() {
        return this.cdKey;
    }

    public void setCdKey(long cdKey) {
        this.cdKey = cdKey;
    }

    public String getNickname() {
        return this.Nickname;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
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
    @Generated(hash = 906319720)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCRActiveUserModelDao() : null;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getUserUnitId() {
        return this.userUnitId;
    }

    public void setUserUnitId(int userUnitId) {
        this.userUnitId = userUnitId;
    }


    
    
}
