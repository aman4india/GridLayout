package com.aman.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aman.sqlite.models.UserEntity;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_db";
    private static final String TABLE_NAME = "user";
    private static final int VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " name TEXT NOT NULL, " +
                        " phone TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
    }

    /**
     * Saves an item into sqlite database
     *
     * @param names
     * @param phone
     */
    public void save(String names, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", names);
        values.put("phone", phone);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Fetches all unsynced records from the database
     *
     * @return data
     */
    public ArrayList<UserEntity> fetch() {
        ArrayList<UserEntity> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String names = cursor.getString(1);
                String phone = cursor.getString(2);
                UserEntity p = new UserEntity();
                p.setName(names);
                p.setPhone(phone);
                p.setId(id);
                data.add(p);
            } while (cursor.moveToNext());
        }
        return data;
    }

    /**
     * Counts All  Records in sqlite
     *
     * @return
     */
    public int count() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }

    public int update(int id,String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        return db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }

    public Integer deleteUser(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "phone = ? ", new String[]{(phone)});
    }
}