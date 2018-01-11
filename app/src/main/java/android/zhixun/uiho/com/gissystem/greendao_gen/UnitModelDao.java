package android.zhixun.uiho.com.gissystem.greendao_gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import android.zhixun.uiho.com.gissystem.flux.models.UnitModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "UNIT_MODEL".
*/
public class UnitModelDao extends AbstractDao<UnitModel, Long> {

    public static final String TABLENAME = "UNIT_MODEL";

    /**
     * Properties of entity UnitModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ArcGisId = new Property(1, int.class, "arcGisId", false, "ARC_GIS_ID");
        public final static Property Address = new Property(2, String.class, "address", false, "ADDRESS");
        public final static Property HolderId = new Property(3, Long.class, "holderId", false, "HOLDER_ID");
        public final static Property CheckResultId = new Property(4, Long.class, "checkResultId", false, "CHECK_RESULT_ID");
        public final static Property Name = new Property(5, String.class, "name", false, "NAME");
        public final static Property QYDM = new Property(6, String.class, "QYDM", false, "QYDM");
        public final static Property HYLB_CODE = new Property(7, String.class, "HYLB_CODE", false, "HYLB__CODE");
        public final static Property HYLB_NAME = new Property(8, String.class, "HYLB_NAME", false, "HYLB__NAME");
        public final static Property ZCD_CODE = new Property(9, String.class, "ZCD_CODE", false, "ZCD__CODE");
        public final static Property ZCD_NAME = new Property(10, String.class, "ZCD_NAME", false, "ZCD__NAME");
        public final static Property YZBM = new Property(11, String.class, "YZBM", false, "YZBM");
        public final static Property LXR = new Property(12, String.class, "LXR", false, "LXR");
        public final static Property ZJDH = new Property(13, String.class, "ZJDH", false, "ZJDH");
        public final static Property LXSJ = new Property(14, String.class, "LXSJ", false, "LXSJ");
        public final static Property RuleId = new Property(15, Long.class, "ruleId", false, "RULE_ID");
        public final static Property CertificateId = new Property(16, Long.class, "certificateId", false, "CERTIFICATE_ID");
        public final static Property QualificationId = new Property(17, Long.class, "qualificationId", false, "QUALIFICATION_ID");
    }


    public UnitModelDao(DaoConfig config) {
        super(config);
    }
    
    public UnitModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"UNIT_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ARC_GIS_ID\" INTEGER NOT NULL UNIQUE ," + // 1: arcGisId
                "\"ADDRESS\" TEXT," + // 2: address
                "\"HOLDER_ID\" INTEGER," + // 3: holderId
                "\"CHECK_RESULT_ID\" INTEGER," + // 4: checkResultId
                "\"NAME\" TEXT," + // 5: name
                "\"QYDM\" TEXT," + // 6: QYDM
                "\"HYLB__CODE\" TEXT," + // 7: HYLB_CODE
                "\"HYLB__NAME\" TEXT," + // 8: HYLB_NAME
                "\"ZCD__CODE\" TEXT," + // 9: ZCD_CODE
                "\"ZCD__NAME\" TEXT," + // 10: ZCD_NAME
                "\"YZBM\" TEXT," + // 11: YZBM
                "\"LXR\" TEXT," + // 12: LXR
                "\"ZJDH\" TEXT," + // 13: ZJDH
                "\"LXSJ\" TEXT," + // 14: LXSJ
                "\"RULE_ID\" INTEGER," + // 15: ruleId
                "\"CERTIFICATE_ID\" INTEGER," + // 16: certificateId
                "\"QUALIFICATION_ID\" INTEGER);"); // 17: qualificationId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"UNIT_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UnitModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getArcGisId());
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(3, address);
        }
 
        Long holderId = entity.getHolderId();
        if (holderId != null) {
            stmt.bindLong(4, holderId);
        }
 
        Long checkResultId = entity.getCheckResultId();
        if (checkResultId != null) {
            stmt.bindLong(5, checkResultId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
 
        String QYDM = entity.getQYDM();
        if (QYDM != null) {
            stmt.bindString(7, QYDM);
        }
 
        String HYLB_CODE = entity.getHYLB_CODE();
        if (HYLB_CODE != null) {
            stmt.bindString(8, HYLB_CODE);
        }
 
        String HYLB_NAME = entity.getHYLB_NAME();
        if (HYLB_NAME != null) {
            stmt.bindString(9, HYLB_NAME);
        }
 
        String ZCD_CODE = entity.getZCD_CODE();
        if (ZCD_CODE != null) {
            stmt.bindString(10, ZCD_CODE);
        }
 
        String ZCD_NAME = entity.getZCD_NAME();
        if (ZCD_NAME != null) {
            stmt.bindString(11, ZCD_NAME);
        }
 
        String YZBM = entity.getYZBM();
        if (YZBM != null) {
            stmt.bindString(12, YZBM);
        }
 
        String LXR = entity.getLXR();
        if (LXR != null) {
            stmt.bindString(13, LXR);
        }
 
        String ZJDH = entity.getZJDH();
        if (ZJDH != null) {
            stmt.bindString(14, ZJDH);
        }
 
        String LXSJ = entity.getLXSJ();
        if (LXSJ != null) {
            stmt.bindString(15, LXSJ);
        }
 
        Long ruleId = entity.getRuleId();
        if (ruleId != null) {
            stmt.bindLong(16, ruleId);
        }
 
        Long certificateId = entity.getCertificateId();
        if (certificateId != null) {
            stmt.bindLong(17, certificateId);
        }
 
        Long qualificationId = entity.getQualificationId();
        if (qualificationId != null) {
            stmt.bindLong(18, qualificationId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UnitModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getArcGisId());
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(3, address);
        }
 
        Long holderId = entity.getHolderId();
        if (holderId != null) {
            stmt.bindLong(4, holderId);
        }
 
        Long checkResultId = entity.getCheckResultId();
        if (checkResultId != null) {
            stmt.bindLong(5, checkResultId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
 
        String QYDM = entity.getQYDM();
        if (QYDM != null) {
            stmt.bindString(7, QYDM);
        }
 
        String HYLB_CODE = entity.getHYLB_CODE();
        if (HYLB_CODE != null) {
            stmt.bindString(8, HYLB_CODE);
        }
 
        String HYLB_NAME = entity.getHYLB_NAME();
        if (HYLB_NAME != null) {
            stmt.bindString(9, HYLB_NAME);
        }
 
        String ZCD_CODE = entity.getZCD_CODE();
        if (ZCD_CODE != null) {
            stmt.bindString(10, ZCD_CODE);
        }
 
        String ZCD_NAME = entity.getZCD_NAME();
        if (ZCD_NAME != null) {
            stmt.bindString(11, ZCD_NAME);
        }
 
        String YZBM = entity.getYZBM();
        if (YZBM != null) {
            stmt.bindString(12, YZBM);
        }
 
        String LXR = entity.getLXR();
        if (LXR != null) {
            stmt.bindString(13, LXR);
        }
 
        String ZJDH = entity.getZJDH();
        if (ZJDH != null) {
            stmt.bindString(14, ZJDH);
        }
 
        String LXSJ = entity.getLXSJ();
        if (LXSJ != null) {
            stmt.bindString(15, LXSJ);
        }
 
        Long ruleId = entity.getRuleId();
        if (ruleId != null) {
            stmt.bindLong(16, ruleId);
        }
 
        Long certificateId = entity.getCertificateId();
        if (certificateId != null) {
            stmt.bindLong(17, certificateId);
        }
 
        Long qualificationId = entity.getQualificationId();
        if (qualificationId != null) {
            stmt.bindLong(18, qualificationId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UnitModel readEntity(Cursor cursor, int offset) {
        UnitModel entity = new UnitModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // arcGisId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // address
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // holderId
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // checkResultId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // QYDM
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // HYLB_CODE
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // HYLB_NAME
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // ZCD_CODE
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // ZCD_NAME
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // YZBM
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // LXR
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // ZJDH
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // LXSJ
            cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15), // ruleId
            cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16), // certificateId
            cursor.isNull(offset + 17) ? null : cursor.getLong(offset + 17) // qualificationId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UnitModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setArcGisId(cursor.getInt(offset + 1));
        entity.setAddress(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHolderId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setCheckResultId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setQYDM(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setHYLB_CODE(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setHYLB_NAME(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setZCD_CODE(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setZCD_NAME(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setYZBM(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setLXR(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setZJDH(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setLXSJ(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setRuleId(cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15));
        entity.setCertificateId(cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16));
        entity.setQualificationId(cursor.isNull(offset + 17) ? null : cursor.getLong(offset + 17));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UnitModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UnitModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UnitModel entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
