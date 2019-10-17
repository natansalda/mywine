package com.example.mywine

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.mywine.model.Wine
import com.example.mywine.model.WineDao
import com.example.mywine.model.WineDatabase

class WineRepository(application: Application) {

    private var wineDao: WineDao

    private var allWines: LiveData<List<Wine>>

    init {
        val database: WineDatabase = WineDatabase.getInstance(
            application.applicationContext
        )
        wineDao = database.wineDao()
        allWines = wineDao.getAllWines()
    }

    fun insert(wine: Wine) {
        val insertWineAsyncTask = InsertWineAsyncTask(wineDao).execute(wine)
    }

    fun deleteAllWines() {
        val deleteAllWinesAsyncTask = DeleteAllWinesAsyncTask(
            wineDao
        ).execute()
    }

    fun getAllWines(): LiveData<List<Wine>> {
        return allWines
    }

    private class InsertWineAsyncTask(wineDao: WineDao) : AsyncTask<Wine, Unit, Unit>() {
        val wineDao = wineDao

        override fun doInBackground(vararg p0: Wine?) {
            wineDao.insert(p0[0]!!)
        }
    }


    private class DeleteAllWinesAsyncTask(val wineDao: WineDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            wineDao.deleteAllWines()
        }
    }

}