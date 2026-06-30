package com.mycooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "MyCooking_231011400324.db";
    private static final int    DB_VERSION = 1;
    private static final String TBL        = "tabel_resep_231011400324";

    public static final String COL_ID       = "id_resep";
    public static final String COL_NIM      = "nim_verifikasi";
    public static final String COL_NAMA     = "nama_menu";
    public static final String COL_KATEGORI = "kategori_menu";
    public static final String COL_BAHAN    = "bahan_resep";
    public static final String COL_HARGA    = "estimasi_harga";
    public static final String COL_FOTO     = "foto_uri_path";
    public static final String COL_RATING   = "skor_rating";

    private static final String CREATE_TABLE =
        "CREATE TABLE " + TBL + " (" +
        COL_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_NIM      + " TEXT DEFAULT '231011400324', " +
        COL_NAMA     + " TEXT NOT NULL, " +
        COL_KATEGORI + " TEXT NOT NULL, " +
        COL_BAHAN    + " TEXT NOT NULL, " +
        COL_HARGA    + " INTEGER NOT NULL, " +
        COL_FOTO     + " TEXT NOT NULL, " +
        COL_RATING   + " REAL NOT NULL" +
        ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL);
        onCreate(db);
    }

    public long insertData(String nama, String kategori, String bahan,
                           int harga, String fotoUri, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  cv = new ContentValues();
        cv.put(COL_NAMA,     nama);
        cv.put(COL_KATEGORI, kategori);
        cv.put(COL_BAHAN,    bahan);
        cv.put(COL_HARGA,    harga);
        cv.put(COL_FOTO,     fotoUri);
        cv.put(COL_RATING,   rating);
        long result = db.insert(TBL, null, cv);
        db.close();
        return result;
    }

    public List<ResepModel> getAllResep() {
        List<ResepModel> list = new ArrayList<>();
        SQLiteDatabase   db   = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
            "SELECT * FROM " + TBL + " ORDER BY " + COL_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do { list.add(cursorToModel(cursor)); }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public ResepModel getResepById(int id) {
        SQLiteDatabase db     = this.getReadableDatabase();
        Cursor         cursor = db.rawQuery(
            "SELECT * FROM " + TBL + " WHERE " + COL_ID + "=?",
            new String[]{ String.valueOf(id) });
        ResepModel model = null;
        if (cursor.moveToFirst()) model = cursorToModel(cursor);
        cursor.close();
        db.close();
        return model;
    }

    public int updateData(int id, String nama, String kategori, String bahan,
                          int harga, String fotoUri, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  cv = new ContentValues();
        cv.put(COL_NAMA,     nama);
        cv.put(COL_KATEGORI, kategori);
        cv.put(COL_BAHAN,    bahan);
        cv.put(COL_HARGA,    harga);
        cv.put(COL_FOTO,     fotoUri);
        cv.put(COL_RATING,   rating);
        int rows = db.update(TBL, cv, COL_ID + "=?", new String[]{ String.valueOf(id) });
        db.close();
        return rows;
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL, COL_ID + "=?", new String[]{ String.valueOf(id) });
        db.close();
    }

    private ResepModel cursorToModel(Cursor c) {
        return new ResepModel(
            c.getInt   (c.getColumnIndexOrThrow(COL_ID)),
            c.getString(c.getColumnIndexOrThrow(COL_NAMA)),
            c.getString(c.getColumnIndexOrThrow(COL_KATEGORI)),
            c.getString(c.getColumnIndexOrThrow(COL_BAHAN)),
            c.getInt   (c.getColumnIndexOrThrow(COL_HARGA)),
            c.getString(c.getColumnIndexOrThrow(COL_FOTO)),
            c.getFloat (c.getColumnIndexOrThrow(COL_RATING))
        );
    }
}
