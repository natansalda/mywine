package com.example.mywine.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mywine.model.Wine

@Database(entities = [Wine::class], version = 4, exportSchema = false)
abstract class WineDatabase : RoomDatabase() {

    abstract val wineDatabaseDao: WineDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: WineDatabase? = null

        fun getInstance(context: Context): WineDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WineDatabase::class.java,
                        "wines_database"
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