package com.example.proektdva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "test.db";
    public static final String TABLE_NAME = "kompanii_table";

    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "address";
    public static final String COL_4 = "phone";
    public static final String COL_5 = "website";
    public static final String COL_6 = "email";
    public static final String COL_7 = "latitude";
    public static final String COL_8 = "longitude";
    public static final String COL_9 = "kategorija";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"( id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, address VARCHAR, phone VARCHAR," +
                " website VARCHAR, email VARCHAR, latitude VARCHAR, longitude VARCHAR, kategorija VARCHAR )" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    ////////////// Insert DATA
    public boolean insertData(String name, String address, String phone, String website, String email, String latitude, String longitude, String kategorija){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, address);
        contentValues.put(COL_4, phone);
        contentValues.put(COL_5, website);
        contentValues.put(COL_6, email);
        contentValues.put(COL_7, latitude);
        contentValues.put(COL_8, longitude);
        contentValues.put(COL_9, kategorija);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    //////////////// Services getDATA
    public ArrayList<Item> getDataServices(){
        ArrayList<Item> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from "+TABLE_NAME +" where "+ COL_9 +" like '%Services%'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            String  ime = cursor.getString(1);
            String adresa = cursor.getString(2);
            String telefon = cursor.getString(3);
            String websajt = cursor.getString(4);
            Item item = new Item(ime, adresa, telefon, websajt);
            arrayList.add(item);
        }
        return arrayList;
    }
    //////////////// Fun getDATA
    public ArrayList<Item> getDataFun(){
        ArrayList<Item> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT columnName FROM yourTable WHERE CONTAINS (columnName, ‘yourSubstring’);
        String query = "select * from "+TABLE_NAME +" where "+ COL_9 +" like '%Fun%'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            String  ime = cursor.getString(1);
            String adresa = cursor.getString(2);
            String telefon = cursor.getString(3);
            String websajt = cursor.getString(4);
            Item item = new Item(ime, adresa, telefon, websajt);
            arrayList.add(item);
        }
        return arrayList;
    }

    //////////////// Industry getDATA
    public ArrayList<Item> getDataIndustry(){
        ArrayList<Item> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT columnName FROM yourTable WHERE CONTAINS (columnName, ‘yourSubstring’);
        String query = "select * from "+TABLE_NAME +" where "+ COL_9 +" like '%Industry%'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            String  ime = cursor.getString(1);
            String adresa = cursor.getString(2);
            String telefon = cursor.getString(3);
            String websajt = cursor.getString(4);
            Item item = new Item(ime, adresa, telefon, websajt);
            arrayList.add(item);
        }
        return arrayList;
    }

    //////////////// Education getDATA
    public ArrayList<Item> getDataEducation(){
        ArrayList<Item> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT columnName FROM yourTable WHERE CONTAINS (columnName, ‘yourSubstring’);
        String query = "select * from "+TABLE_NAME +" where "+ COL_9 +" like '%Education%'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            String  ime = cursor.getString(1);
            String adresa = cursor.getString(2);
            String telefon = cursor.getString(3);
            String websajt = cursor.getString(4);
            Item item = new Item(ime, adresa, telefon, websajt);
            arrayList.add(item);
        }
        return arrayList;
    }
}
