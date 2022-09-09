package com.aman.sqlite.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aman.sqlite.models.UserEntity;

import java.util.ArrayList;

public class UserDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_db";
    private static final String TABLE_NAME = "user_table";
    private static final int VERSION = 1;

    private static UserDatabase userDatabase = null;

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static UserDatabase getInstance(Context context) {
        if (userDatabase == null) {
            userDatabase = new UserDatabase(context);
        }
        return userDatabase;
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
     * @param userEntity
     */
    public long save(UserEntity userEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", userEntity.getName());
        values.put("phone", userEntity.getPhone());
        return db.insert(TABLE_NAME, null, values);
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
     * Update All Records in sqlite
     * @param userEntity
     * @return
     */
    public long update(UserEntity userEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", userEntity.getName());
        contentValues.put("phone", userEntity.getPhone());
        return db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(userEntity.getId())});
    }

    /**
     * delete Records in sqlite
     * @param phone
     * @return
     */
    public long deleteUser(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "phone = ? ", new String[]{(phone)});
    }
}