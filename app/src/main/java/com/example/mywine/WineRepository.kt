package com.example.mywine

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.mywine.model.Wine
import com.example.mywine.model.WineDao

class WineRepository(private val wineDao: WineDao) {

    private val allWines: LiveData<List<Wine>> = wineDao.getAllWines()

    fun insert(wine: Wine) {
        InsertWineAsyncTask(
            wineDao
        ).execute(wine)
    }

    fun deleteAllWines() {
        DeleteAllWinesAsyncTask(
            wineDao
        ).execute()
    }

    fun getAllWines(): LiveData<List<Wine>> {
        return allWines
    }

    private class InsertWineAsyncTask(val wineDao: WineDao) : AsyncTask<Wine, Unit, Unit>() {

        override fun doInBackground(vararg wine: Wine?) {
            wineDao.insert(wine[0]!!)
        }
    }

    private class DeleteAllWinesAsyncTask(val wineDao: WineDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            wineDao.deleteAllWines()
        }
    }
}