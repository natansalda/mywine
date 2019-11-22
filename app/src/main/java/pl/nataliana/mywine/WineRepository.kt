package pl.nataliana.mywine

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import pl.nataliana.mywine.database.WineDatabaseDao
import pl.nataliana.mywine.model.Wine

class WineRepository(private val wineDatabaseDao: WineDatabaseDao) {

    private val allWines: LiveData<List<Wine>> = wineDatabaseDao.getAllWines()

    fun insert(wine: Wine) {
        InsertWineAsyncTask(
            wineDatabaseDao
        ).execute(wine)
    }

    fun deleteAllWines() {
        DeleteAllWinesAsyncTask(
            wineDatabaseDao
        ).execute()
    }

    fun getAllWines(): LiveData<List<Wine>> {
        return allWines
    }

    fun getWineDetails(id: Long) {
        GetWineDetailAsyncTask(
            wineDatabaseDao
            , id
        ).execute()
    }

    private class InsertWineAsyncTask(val wineDatabaseDao: WineDatabaseDao) : AsyncTask<Wine, Unit, Unit>() {

        override fun doInBackground(vararg wine: Wine?) {
            wineDatabaseDao.insert(wine[0]!!)
        }
    }

    private class DeleteAllWinesAsyncTask(val wineDatabaseDao: WineDatabaseDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            wineDatabaseDao.deleteAllWines()
        }
    }

    private class GetWineDetailAsyncTask(val wineDatabaseDao: WineDatabaseDao, val id: Long) :
        AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            wineDatabaseDao.getWineDetails(id)
        }
    }
}