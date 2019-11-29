package pl.nataliana.mywine.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.nataliana.mywine.model.Wine

@Dao
interface WineDatabaseDao {

    // insert new wine
    @Insert
    fun insert(wine: Wine)

    // update particular wine
    @Update
    fun update(wine: Wine)

    // delete all wines in database
    @Query("DELETE FROM wines_table")
    fun deleteAllWines()

    // delete particular wine
    @Query("DELETE FROM wines_table WHERE id = :key")
    fun deleteThisWine(key: Long)

    // access all wines in database
    @Query("SELECT * FROM wines_table ORDER BY id DESC")
    fun getAllWines(): LiveData<List<Wine>>

    // access all wines in database by rating descending
    @Query("SELECT * FROM wines_table ORDER BY rate DESC")
    fun getAllWinesByRatingBest(): LiveData<List<Wine>>

    // access all wines in database by rating ascending
    @Query("SELECT * FROM wines_table ORDER BY rate ASC")
    fun getAllWinesByRatingWorse(): LiveData<List<Wine>>

    // get particular wine in the database
    @Query("SELECT * from wines_table WHERE id = :key")
    fun getWineDetails(key: Long): Wine?
}