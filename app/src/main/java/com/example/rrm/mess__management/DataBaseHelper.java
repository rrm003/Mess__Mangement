package com.example.rrm.mess__management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DataBase_Name = "Mess.db";
    private static final String Table_Name = "Customer";
    private static final String col1 = "ID";
    private static final String col2 = "Name";
    private static final String col3 = "Address";
    private static final String col4 = "Phone";
    private static final String col5 = "LunchPlates";
    private static final String col6 = "DinnerPlates";
    private static final String col7 = "Amount_Paid";
    private static final String col8 = "Date";
    private static final String col9 = "Amount_Pending";
    private static final String col10 = "LastDay";

    //constructor
    DataBaseHelper(Context context) {
        super(context, DataBase_Name, null, 1);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ Table_Name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Address TEXT,Phone LONG,LunchPlates INTEGER,DinnerPlates INTEGER,Amount_Paid DOUBLE,Date TEXT,Amount_Pending DOUBLE,LastDay INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ Table_Name);
        onCreate(db);   //create
    }

    boolean intoDB(String name, long phone, String address, double amtpaid, int lunchPlates, int dinnerPlates, String date,int yr_day){
        double ap = Pricepg.getTwoPrice();
        if(lunchPlates+dinnerPlates == 30)
            ap= Pricepg.getOnePrice()-amtpaid;
        else if(lunchPlates+dinnerPlates == 60)
            ap = Pricepg.getTwoPrice()-amtpaid;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2,name);
        contentValues.put(col3,address);
        contentValues.put(col4,phone);
        contentValues.put(col5,lunchPlates);
        contentValues.put(col6,dinnerPlates);
        contentValues.put(col7,amtpaid);
        contentValues.put(col8,date);
        contentValues.put(col9,ap);
        contentValues.put(col10,yr_day);

        long flag = db.insert(Table_Name,null,contentValues);
        return flag != -1;
    }

    public Cursor getDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+Table_Name,null);
    }




    boolean updateDB(String name, long phone, String address, int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2,name);
        contentValues.put(col3,address);
        contentValues.put(col4,phone);
        int rows;
        String ids = Integer.valueOf(id).toString();
        rows=db.update(Table_Name,contentValues,"ID = ?",new String[] {ids});

        return rows>0;
    }

    boolean custDeposit(String id, double amtpaid, double amtpending){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col7,amtpaid);
        contentValues.put(col9,amtpending);
        int rows;
        rows=db.update(Table_Name,contentValues,"ID = ?",new String[] {id});
        return  rows >0;
    }

    boolean deleteDb(String id){
        int rows;
        SQLiteDatabase db =this.getWritableDatabase();
        rows=db.delete(Table_Name,"ID = ?",new String []{id});
        return rows>0;

    }


    boolean updateAttendance(int id,int plate,int t) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(t==1){
            contentValues.put(col5,plate);
            String ids = Integer.toString(id);
            int rows=db.update(Table_Name,contentValues,"ID = ?",new String[] {ids});
            return rows>0;
        }

        else if(t==2){

            contentValues.put(col6,plate);
            String ids = Integer.toString(id);
            int rows=db.update(Table_Name,contentValues,"ID = ?",new String[] {ids});
            return rows>0;
        }


        return false;
    }


}