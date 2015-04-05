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
    //insert a object preference on the db
    //return true if the transaction was successful
    public boolean insert(Preference pref) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DBCOLUMNNAME, pref.getName());
            values.put(DBCOLUMNPLACE, pref.getPlace());
            values.put(DBCOLUMNNCOOR, pref.getCoor());
            db.insert(DBTABLE, null, values);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    //returns a list of objects preference with all records saved on the data base
     public List<Preference> getAll(){
        List<Preference> prefList = new ArrayList<Preference>();
        String selectAll= "select * from "+DBTABLE;
        SQLiteDatabase db= this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            do{

                Preference pref=new Preference(Integer.parseInt(cursor.getString(0).toString()),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3));
                prefList.add(pref);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return prefList;
    }

    //Deletes all from preference table
    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DBTABLE, null, null);
    }

    //find a record by his name and return an object with all the info
    public Preference find(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        Preference pref;
        Cursor cursor;
        String selectOne="select  from "+DBTABLE+" where "+DBCOLUMNNAME+" = "+name;
        String[] columns=new String[]{DBCOLUMNID, DBCOLUMNNAME, DBCOLUMNPLACE, DBCOLUMNNCOOR};
        try{
            cursor= db.query(DBTABLE, columns ,DBCOLUMNNAME+" = ?" ,new String[] { String.valueOf(name) }, null, null, null);
            if(cursor!=null)cursor.moveToFirst();

             pref=new Preference(Integer.parseInt(cursor.getString(0).toString()),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3));
            cursor.close();
        }catch (Exception e){
            pref = new Preference(0,null, null, null);

        }

        return pref;
    }
    //return the currently number of rows in the database
    public long countRows(){
        SQLiteDatabase db=this.getWritableDatabase();
        String select="select * from "+DBTABLE;
        Cursor cursor= db.rawQuery(select,null);
        return cursor.getCount();

    }
        //update a row or many of them(if they have the same name)
        // using the column name of the bd
        //returns the number of row affected
     public int update( Preference pref) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBCOLUMNNAME, pref.getName());
        values.put(DBCOLUMNPLACE, pref.getPlace());
        values.put(DBCOLUMNNCOOR, pref.getCoor());

        return db.update(DBTABLE, values, DBCOLUMNNAME + " = ?",new String[] { String.valueOf(pref.getName()) });
    }
    //delete a row using as parameter the column name
    //returns if the row was delete
    public boolean delete(String name) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            db.delete(DBTABLE, DBCOLUMNNAME + " = ?", new String[] { String.valueOf(name) });
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
