package com.example.mywine.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mywine.model.Wine

@Database(entities = [Wine::class], version = 4, exportSchema = false)
abstract class WineDatabase : RoomDatabase() {

    abstract fun wineDao(): WineDao

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
        }
    }
}