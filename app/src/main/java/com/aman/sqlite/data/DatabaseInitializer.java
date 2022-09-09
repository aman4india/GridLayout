package com.aman.sqlite.data;


import android.content.Context;

import com.aman.sqlite.data.db.UserDatabase;
import com.aman.sqlite.models.UserEntity;

import java.util.List;

public class DatabaseInitializer {

    public static long insertUser(Context context, UserEntity userEntity) {
        return UserDatabase.getInstance(context).save(userEntity);
    }

    public static List<UserEntity> getAllUser(Context context) {
        return UserDatabase.getInstance(context).fetch();
    }

    public static long updateUser(Context context, UserEntity userEntity) {
        return UserDatabase.getInstance(context).update(userEntity);
    }

    public static long deleteUser(Context context, String phone) {
        return UserDatabase.getInstance(context).deleteUser(phone);
    }
}