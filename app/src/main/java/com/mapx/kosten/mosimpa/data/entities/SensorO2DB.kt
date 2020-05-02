package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_O2_TABLE

@Entity(tableName = SENSOR_O2_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = InternmentDB::class,
            parentColumns = ["id"],
            childColumns = ["internment_id"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class SensorO2DB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val internment_id: Long = -1,
    val time: Long = -1,
    val spo2: Float = -1F,
    val r: Float = -1F
)