package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_HEART_TABLE

@Entity(tableName = SENSOR_HEART_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = InternmentDB::class,
            parentColumns = ["id"],
            childColumns = ["patient_id"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class SensorHeartDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val patient_id: Long = -1,
    val time: Long = -1,
    val heartR: Int = -1,
    val HR_AR: Boolean = false
)