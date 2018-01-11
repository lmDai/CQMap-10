package android.zhixun.uiho.com.gissystem.flux.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by parcool on 2016/12/5.
 * 重庆区(州)县
 */
@Entity
public class CQPrefectureModel {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String code;
    private double latitude;//纬度
    private double longitude;//经度
    @Transient
    private boolean isChecked;
    @Generated(hash = 2129570218)
    public CQPrefectureModel(Long id, @NotNull String name, @NotNull String code,
            double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Generated(hash = 1674589136)
    public CQPrefectureModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
