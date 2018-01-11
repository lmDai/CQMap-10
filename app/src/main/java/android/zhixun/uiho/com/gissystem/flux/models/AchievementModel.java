package android.zhixun.uiho.com.gissystem.flux.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zp on 2016/12/21.
 */

@Entity
public class AchievementModel {
    @Id(autoincrement = true)
    private Long id;
    private Long unitKey;//表示这条成果是属于哪个公司的？
//    @Convert(converter = ListLongConverter.class, columnType = String.class)
//    private List<Long> sortOneModelIdList;//分类1的所有关联ID
//    @Convert(converter = ListLongConverter.class, columnType = String.class)
//    private List<Long> sortTwoModelIdList;//分类2的所有关联ID
    private Long time;//分发时间
    private String bjCode;//报件编号





    @Generated(hash = 1810882455)
    public AchievementModel(Long id, Long unitKey, Long time, String bjCode) {
        this.id = id;
        this.unitKey = unitKey;
        this.time = time;
        this.bjCode = bjCode;
    }
    @Generated(hash = 1281560100)
    public AchievementModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUnitKey() {
        return this.unitKey;
    }
    public void setUnitKey(Long unitKey) {
        this.unitKey = unitKey;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public String getBjCode() {
        return this.bjCode;
    }
    public void setBjCode(String bjCode) {
        this.bjCode = bjCode;
    }

    
}
