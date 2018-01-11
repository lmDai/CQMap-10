package android.zhixun.uiho.com.gissystem.flux.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by parcool on 2016/12/6.
 * 单位/行业类别
 */
@Entity
public class IndustryCategoryModel {
    @Id (autoincrement = true)
    private Long id;
    @NotNull
    private String code;
    @NotNull
    private String name;
    @Transient
    private boolean isChecked;
    @Generated(hash = 2054013716)
    public IndustryCategoryModel(Long id, @NotNull String code,
            @NotNull String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    @Generated(hash = 395444940)
    public IndustryCategoryModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
