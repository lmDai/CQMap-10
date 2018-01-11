package android.zhixun.uiho.com.gissystem.flux.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 存放某一个登记的参检人员
 * Created by parcool on 2016/12/8.
 */

@Entity
public class CRCheckPersonModel {
    @Id(autoincrement = true)
    private Long id;

    private long CRKey;//保密检查登记表的ID（外键）
    private long userID;//用户的ID
    @Generated(hash = 1918411573)
    public CRCheckPersonModel(Long id, long CRKey, long userID) {
        this.id = id;
        this.CRKey = CRKey;
        this.userID = userID;
    }
    @Generated(hash = 668812963)
    public CRCheckPersonModel() {
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
    public long getUserID() {
        return this.userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }




}
