package com.example.valutes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ValuteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ValuteDatabase: RoomDatabase() {
    abstract val valuteDatabaseDao: ValuteDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ValuteDatabase? =
            null

        fun getInstance(context: Context): ValuteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ValuteDatabase::class.java,
                        "favorite_articles_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}