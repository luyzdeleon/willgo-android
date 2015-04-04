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
    private static final String DBNAME = "WilgoDB";
    private static final String DBTABLE = "preference";
    private static final String DBCOLUMNID = "id";
    private static final String DBCOLUMNNAME = "name";
    private static final String DBCOLUMNPLACE = "place";
    private static final String DBCOLUMNNCOOR = "coor";

    private static final int VERSION = 1;
    private static final String TABLE_CREATE = "CREATE TABLE " +
            DBTABLE + "(" + DBCOLUMNID + " INTEGER PRIMARY KEY , " + DBCOLUMNNAME + " TEXT, " +
            DBCOLUMNPLACE + " TEXT, " + DBCOLUMNNCOOR + " TEXT)";


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
    //returns a list of objects preference with all records saved on the data base
     public List<preference> getAll(){
        List<preference> prefList = new ArrayList<preference>();
        String selectAll= "select * from "+DBTABLE;
        SQLiteDatabase db= this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            do{
                preference pref=new preference(cursor.getString(1),cursor.getString(2),cursor.getString(3));
                prefList.add(pref);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return prefList;
    }
    //find a record by his name and return an object with all the info
    public preference find(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        preference pref;
        Cursor cursor;
        String selectOne="select  from "+DBTABLE+" where "+DBCOLUMNNAME+" = "+name;
        String[] columns=new String[]{DBCOLUMNID, DBCOLUMNNAME, DBCOLUMNPLACE, DBCOLUMNNCOOR};
        try{
            cursor= db.query(DBTABLE, columns ,DBCOLUMNNAME+" = ?" ,new String[] { String.valueOf(name) }, null, null, null);
            if(cursor!=null)cursor.moveToFirst();

            pref = new preference(cursor.getString(1).toString(),cursor.getString(2).toString(),cursor.getString(3).toString());
            cursor.close();
        }catch (Exception e){
            pref = new preference(null, null, null);

        }

        return pref;
    }

}
