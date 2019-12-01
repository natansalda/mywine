package pl.nataliana.mywine.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wines_table")
data class Wine(
    val name: String,
    val color: String,
    val year: Int?,
    val rate: Int?,
    val price: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
