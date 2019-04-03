package com.example.techwheelsservicecentre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databasename="tech_wheels.db";
    public static final String tablename="tech_wheels_table";
    public static final String COL1="ID";
    public static final String COL2="vehicle_model";
    public static final String COL3="reg_no";
    public static final String COL4="date";
    public static final String COL5="time";

    public static final String tablename2="cars";
    public static final String TCOL1="ID";
    public static final String TCOL2="vehicle_model";
    public static final String TCOL3="reg_no";


    public DatabaseHelper(Context context) {
        super(context, databasename,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Createtable="CREATE TABLE "+ tablename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " vehicle_model TEXT,reg_no TEXT,date TEXT,time TEXT)";
        db.execSQL(Createtable);

        String Createtable2="CREATE TABLE "+tablename2+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "vehicle_model TEXT,reg_no TEXT)";
        db.execSQL(Createtable2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tablename);
        //onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS "+tablename2);
        onCreate(db);
    }

    public Boolean AddData(String vehicle_model,String reg_no,String date,String time)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,vehicle_model);
        contentValues.put(COL3,reg_no);
        contentValues.put(COL4,date);
        contentValues.put(COL5,time);

        long result= db.insert(tablename,null,contentValues);

        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor showData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+tablename, null);
        return cursor;

    }

    public Boolean getCardata(String vehicle_model,String reg_no)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TCOL2,vehicle_model);
        contentValues.put(TCOL3,reg_no);

        long result= db.insert(tablename2,null,contentValues);

        if(result==-1)
            return false;
        else
            return true;

    }
    public Cursor showCarData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+tablename2, null);
        return cursor;

    }
}
