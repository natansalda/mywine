package com.example.mywine.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wines_table")
data class Wine(val name: String, val color: String, val year: Int, val rate: Double) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
