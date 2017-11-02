package com.nesnata.pemiloe.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nikko Eka Saputra on 23/09/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_travel";

    //tabel user
    public static final String TABLE_USER = "tb_user";
    public static final String COL_USER = "username";
    public static final String COL_PASS = "password";
    public static final String COL_LEVEL = "level";

    //tabel kandidat
    public static final String TABLE_KANDIDAT = "tb_kandidat";
    public static final String COL_NO = "no_urut";
    public static final String COL_NAMA = "nama";
    public static final String COL_VISI = "visi";
    public static final String COL_MISI = "misi";

    //tabel suara
    public static final String TABLE_SUARA = "tb_suara";
    public static final String COL_PILIHAN = "pilihan";
    public static final String COL_PEMILIH = "pemilih";

    //tabel akses
    public static final String TABLE_AKSES = "tb_akses";
    public static final String COL_AKSES = "status";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON");

        //create database
        db.execSQL("create table " + TABLE_USER + " (" + COL_USER + " INT PRIMARY KEY, " + COL_PASS + " TEXT, " + COL_LEVEL + " TEXT)");
        db.execSQL("create table " + TABLE_KANDIDAT + " (" + COL_NO + " INT PRIMARY KEY, " + COL_NAMA + " TEXT, " + COL_VISI + " TEXT, " + COL_MISI + " TEXT)");
        db.execSQL("create table " + TABLE_SUARA + " (" + COL_PEMILIH + " INT PRIMARY KEY, " + COL_PILIHAN + " TEXT)");
        db.execSQL("create table " + TABLE_AKSES + " (" + COL_AKSES + " TEXT)");

        //insert database
        db.execSQL("insert into " + TABLE_USER + " values ('1501','1501','user');");
        db.execSQL("insert into " + TABLE_USER + " values ('1502','1502','user');");
        db.execSQL("insert into " + TABLE_USER + " values ('1601','1601','user');");
        db.execSQL("insert into " + TABLE_USER + " values ('admin','admin','admin');");

        db.execSQL("insert into " + TABLE_KANDIDAT + " values ('1','Nikko Eka Saputra','Solid, Aktif, Kreatif, Transformatif, dan Islami','Menjadikan Santri yg Solid\nMenjadikan Santri yg Aktif\nMenjadikan Santri yg kreatif');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }


    public boolean Login(String username, String password) throws SQLException {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USER + "=? AND " + COL_PASS + "=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

}