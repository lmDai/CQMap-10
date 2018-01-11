package android.zhixun.uiho.com.gissystem.flux.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by parcool on 2016/12/13.
 */

@Entity
public class UnitModel implements Serializable {

    private static final long serialVersionUID = -105485190467652997L;

    @Id(autoincrement = true)
    private Long id;
    @Unique
    private int arcGisId;//地图服务器那边的ID
    //    @Convert(converter = PointConverter.class, columnType = String.class)
//    private Point location;//该单位在地图上的位置
//    @Convert(converter = GeometryConverter.class, columnType = String.class)
//    private Geometry geometry;//几何形状
    private String address;//地址
    private Long holderId;//持证人表的外键id
    private Long checkResultId;//保密检查结果表的外键id
    private String name;//公司名称
    private String QYDM;//企业代码（根据彪哥的表来的，都是拼音的下同）
    private String HYLB_CODE;//行业类别_代码
    private String HYLB_NAME;//行业类别_名称
    private String ZCD_CODE;//注册地_代码
    private String ZCD_NAME;//注册地_名称
    private String YZBM;//邮政编码
    private String LXR;//联系人
    private String ZJDH;//座机电话
    private String LXSJ;//联系手机

    /***
     * 如下3个暂时不用，直接在界面上写死UI上的图片即可
     */
    private Long ruleId;//测绘成果管理办法表的外键的id
    private Long certificateId;//法人证书表的外键的id
    private Long qualificationId;//相关资质表的外键的id
    @Generated(hash = 258609523)
    public UnitModel(Long id, int arcGisId, String address, Long holderId, Long checkResultId,
            String name, String QYDM, String HYLB_CODE, String HYLB_NAME, String ZCD_CODE,
            String ZCD_NAME, String YZBM, String LXR, String ZJDH, String LXSJ, Long ruleId,
            Long certificateId, Long qualificationId) {
        this.id = id;
        this.arcGisId = arcGisId;
        this.address = address;
        this.holderId = holderId;
        this.checkResultId = checkResultId;
        this.name = name;
        this.QYDM = QYDM;
        this.HYLB_CODE = HYLB_CODE;
        this.HYLB_NAME = HYLB_NAME;
        this.ZCD_CODE = ZCD_CODE;
        this.ZCD_NAME = ZCD_NAME;
        this.YZBM = YZBM;
        this.LXR = LXR;
        this.ZJDH = ZJDH;
        this.LXSJ = LXSJ;
        this.ruleId = ruleId;
        this.certificateId = certificateId;
        this.qualificationId = qualificationId;
    }
    @Generated(hash = 436854100)
    public UnitModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getArcGisId() {
        return this.arcGisId;
    }
    public void setArcGisId(int arcGisId) {
        this.arcGisId = arcGisId;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Long getHolderId() {
        return this.holderId;
    }
    public void setHolderId(Long holderId) {
        this.holderId = holderId;
    }
    public Long getCheckResultId() {
        return this.checkResultId;
    }
    public void setCheckResultId(Long checkResultId) {
        this.checkResultId = checkResultId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getQYDM() {
        return this.QYDM;
    }
    public void setQYDM(String QYDM) {
        this.QYDM = QYDM;
    }
    public String getHYLB_CODE() {
        return this.HYLB_CODE;
    }
    public void setHYLB_CODE(String HYLB_CODE) {
        this.HYLB_CODE = HYLB_CODE;
    }
    public String getHYLB_NAME() {
        return this.HYLB_NAME;
    }
    public void setHYLB_NAME(String HYLB_NAME) {
        this.HYLB_NAME = HYLB_NAME;
    }
    public String getZCD_CODE() {
        return this.ZCD_CODE;
    }
    public void setZCD_CODE(String ZCD_CODE) {
        this.ZCD_CODE = ZCD_CODE;
    }
    public String getZCD_NAME() {
        return this.ZCD_NAME;
    }
    public void setZCD_NAME(String ZCD_NAME) {
        this.ZCD_NAME = ZCD_NAME;
    }
    public String getYZBM() {
        return this.YZBM;
    }
    public void setYZBM(String YZBM) {
        this.YZBM = YZBM;
    }
    public String getLXR() {
        return this.LXR;
    }
    public void setLXR(String LXR) {
        this.LXR = LXR;
    }
    public String getZJDH() {
        return this.ZJDH;
    }
    public void setZJDH(String ZJDH) {
        this.ZJDH = ZJDH;
    }
    public String getLXSJ() {
        return this.LXSJ;
    }
    public void setLXSJ(String LXSJ) {
        this.LXSJ = LXSJ;
    }
    public Long getRuleId() {
        return this.ruleId;
    }
    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }
    public Long getCertificateId() {
        return this.certificateId;
    }
    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }
    public Long getQualificationId() {
        return this.qualificationId;
    }
    public void setQualificationId(Long qualificationId) {
        this.qualificationId = qualificationId;
    }

    


//
//    public static class PointConverter implements PropertyConverter<Point, String> {
//        @Override
//        public Point convertToEntityProperty(String databaseValue) {
//            if (databaseValue == null) {
//                return null;
//            }
//            String[] pointArray = databaseValue.split(",");
//            if (pointArray.length != 2) {
//                return null;
//            }
//            return new Point(Double.parseDouble(pointArray[0]), Double.parseDouble(pointArray[1]));
//        }
//
//        @Override
//        public String convertToDatabaseValue(Point entityProperty) {
//            if (entityProperty != null) {
//                return entityProperty.getX() + "," + entityProperty.getY();
//            }
//            return null;
//        }
//    }

//    public static class GeometryConverter implements PropertyConverter<Geometry, String> {
//
//
//        @Override
//        public Geometry convertToEntityProperty(String databaseValue) {
//            return Geometry.fromJson(databaseValue);
//        }
//
//        @Override
//        public String convertToDatabaseValue(Geometry entityProperty) {
//            return entityProperty.toJson();
//        }
//    }
}
