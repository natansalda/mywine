package com.example.mywine.model

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Wine::class], version = 1)
abstract class WineDatabase : RoomDatabase() {

    abstract fun wineDao(): WineDao

    private var instance: WineDatabase? = null

    companion object {
        private var instance: WineDatabase? = null

        fun getInstance(context: Context): WineDatabase {
            if (instance == null) {
                synchronized(WineDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WineDatabase::class.java, "wines_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }

    }

    class PopulateDbAsyncTask(db: WineDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val wineDao = db?.wineDao()

        override fun doInBackground(vararg p0: Unit?) {
            wineDao?.insert(Wine("Title 1", "red", 1987, 3.5))
            wineDao?.insert(Wine("Title 2", "white", 1999, 4.5))
            wineDao?.insert(Wine("Title 3", "pink", 2019, 2.5))
        }
    }
}