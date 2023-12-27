package com.example.wpg.ItemSave;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();

    // 单例模式获取数据库实例
    private static ItemDatabase instance;

    public static synchronized ItemDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, "app_database")
                    .build();
        }
        return instance;
    }

}
