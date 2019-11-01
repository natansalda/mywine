package com.example.mywine.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mywine.model.Wine

@Dao
interface WineDatabaseDao {

    // insert new wine
    @Insert
    fun insert(wine: Wine)

    // update particular wine
    @Update
    fun update(wine: Wine)

    // delete particular wine
    @Delete
    fun delete(wine: Wine)

    // delete all wines in database
    @Query("DELETE FROM wines_table")
    fun deleteAllWines()

    // access all wines in database
    @Query("SELECT * FROM wines_table ORDER BY id DESC")
    fun getAllWines(): LiveData<List<Wine>>

    // get particular wine in the database
    @Query("SELECT * from wines_table WHERE id = :key")
    fun getWineDetails(key: Long): Wine?
}