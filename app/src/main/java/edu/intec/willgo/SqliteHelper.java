package edu.intec.willgo;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by LUIS on 4/4/2015.
 */
public class SqliteHelper  extends SQLiteOpenHelper {
    private static final String DBNAME = "WilgoDB2";
    private static final String DBTABLE = "preference";
    private static final String DBCOLUMNID = "id";
    private static final String DBCOLUMNNAME = "name";
    private static final String DBCOLUMNPLACE = "place";
    private static final String DBCOLUMNNCOOR = "coor";
    private static final String KEY_NAME = "name";
    private static final int VERSION = 1;
    private static final String TABLE_CREATE = "CREATE TABLE " +
            DBTABLE + "(" + DBCOLUMNID + " INTEGER PRIMARY KEY , " + DBCOLUMNNAME + " TEXT, " + DBCOLUMNPLACE + " TEXT, " + DBCOLUMNNCOOR + " TEXT)";


    public SqliteHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

      @Override
      public void onUpgrade(SQLiteDatabase db, int i, int i2) {

      }
    public boolean insert(preference pref) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DBCOLUMNNAME, pref.name);
            values.put(DBCOLUMNPLACE, pref.place);
            values.put(DBCOLUMNNCOOR, pref.coor);
            db.insert(DBTABLE, null, values);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
