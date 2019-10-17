package com.example.mywine.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WineDao {

    @Insert
    fun insert(note: Wine)

    @Query("DELETE FROM wines_table")
    fun deleteAllWines()

    @Query("SELECT * FROM wines_table ")
    fun getAllWines(): LiveData<List<Wine>>

}