package com.example.mywine.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mywine.model.Wine

@Dao
interface WineDao {

    @Insert
    fun insert(wine: Wine)

    @Query("DELETE FROM wines_table")
    fun deleteAllWines()

    @Query("SELECT * FROM wines_table ")
    fun getAllWines(): LiveData<List<Wine>>

}