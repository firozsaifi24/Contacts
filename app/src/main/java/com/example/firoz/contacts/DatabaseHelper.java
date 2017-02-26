package com.example.firoz.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Firoz on 26-12-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="MyContacts.db";
    private static final int VERSION=1;

    private static final String TABLE_NAME="contacts";
    private static final String ID="id";
    private static final String NAME="name";
    private static final String PHONE="phone";
    private static final String EMAIL="email";

    SQLiteDatabase myDB;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                            NAME+" TEXT NOT NULL, "+
                            PHONE+" INTEGER NOT NULL, "+
                            EMAIL+" TEXT)";

        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXISTS "+TABLE_NAME;
        onCreate(db);
    }
    public void openDB()
    {
        myDB=getWritableDatabase();
    }
    public void closeDB()
    {
        if(myDB!=null && myDB.isOpen())
        {
            myDB.close();
        }
    }
    public long insert(String name,String phone,String email)
    {
        ContentValues cv=new ContentValues();
        long result;
            cv.put(NAME,name);
            cv.put(PHONE,phone);
            cv.put(EMAIL,email);
            result=myDB.insert(TABLE_NAME,null,cv);

            return result;
    }
    public long update(int id,String name,String phone,String email) {
        ContentValues cv = new ContentValues();
        long result;
        cv.put(NAME, name);
        cv.put(PHONE, phone);
        cv.put(EMAIL, email);

        String where = ID + " = " + id;
        result = myDB.update(TABLE_NAME, cv, where, null);
        return result;
    }
    public long delete(int id) {
        long result;
        String where = ID + " = " + id;
        result = myDB.delete(TABLE_NAME,where,null);
        return result;
    }

    public Cursor getAllData()
    {
            String query="SELECT * FROM "+TABLE_NAME;
            return myDB.rawQuery(query,null);

    }

}
