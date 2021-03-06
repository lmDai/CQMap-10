package android.zhixun.uiho.com.gissystem.greendao_gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import android.zhixun.uiho.com.gissystem.flux.models.CGSortOneModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CGSORT_ONE_MODEL".
*/
public class CGSortOneModelDao extends AbstractDao<CGSortOneModel, Long> {

    public static final String TABLENAME = "CGSORT_ONE_MODEL";

    /**
     * Properties of entity CGSortOneModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property XTH = new Property(1, String.class, "XTH", false, "XTH");
        public final static Property SCDate = new Property(2, String.class, "SCDate", false, "SCDATE");
        public final static Property DDJZ = new Property(3, String.class, "DDJZ", false, "DDJZ");
        public final static Property SJGS = new Property(4, String.class, "SJGS", false, "SJGS");
        public final static Property CRKey = new Property(5, long.class, "CRKey", false, "CRKEY");
    }

    private DaoSession daoSession;


    public CGSortOneModelDao(DaoConfig config) {
        super(config);
    }
    
    public CGSortOneModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CGSORT_ONE_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"XTH\" TEXT," + // 1: XTH
                "\"SCDATE\" TEXT," + // 2: SCDate
                "\"DDJZ\" TEXT," + // 3: DDJZ
                "\"SJGS\" TEXT," + // 4: SJGS
                "\"CRKEY\" INTEGER NOT NULL );"); // 5: CRKey
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CGSORT_ONE_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CGSortOneModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String XTH = entity.getXTH();
        if (XTH != null) {
            stmt.bindString(2, XTH);
        }
 
        String SCDate = entity.getSCDate();
        if (SCDate != null) {
            stmt.bindString(3, SCDate);
        }
 
        String DDJZ = entity.getDDJZ();
        if (DDJZ != null) {
            stmt.bindString(4, DDJZ);
        }
 
        String SJGS = entity.getSJGS();
        if (SJGS != null) {
            stmt.bindString(5, SJGS);
        }
        stmt.bindLong(6, entity.getCRKey());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CGSortOneModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String XTH = entity.getXTH();
        if (XTH != null) {
            stmt.bindString(2, XTH);
        }
 
        String SCDate = entity.getSCDate();
        if (SCDate != null) {
            stmt.bindString(3, SCDate);
        }
 
        String DDJZ = entity.getDDJZ();
        if (DDJZ != null) {
            stmt.bindString(4, DDJZ);
        }
 
        String SJGS = entity.getSJGS();
        if (SJGS != null) {
            stmt.bindString(5, SJGS);
        }
        stmt.bindLong(6, entity.getCRKey());
    }

    @Override
    protected final void attachEntity(CGSortOneModel entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CGSortOneModel readEntity(Cursor cursor, int offset) {
        CGSortOneModel entity = new CGSortOneModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // XTH
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // SCDate
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // DDJZ
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // SJGS
            cursor.getLong(offset + 5) // CRKey
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CGSortOneModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setXTH(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSCDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDDJZ(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSJGS(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCRKey(cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CGSortOneModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CGSortOneModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CGSortOneModel entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
