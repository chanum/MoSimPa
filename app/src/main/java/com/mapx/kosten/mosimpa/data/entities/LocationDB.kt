package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.LOCATION_TABLE

@Entity(tableName = LOCATION_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = InternmentDB::class,
            parentColumns = ["id"],
            childColumns = ["patient_id"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class LocationDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    var patient_id: Long = -1,
    val type: String = "",
    val description: String = ""
)