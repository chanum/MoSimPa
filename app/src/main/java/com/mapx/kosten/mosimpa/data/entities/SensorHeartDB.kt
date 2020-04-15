package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Contants.Companion.SENSOR_HEART_TABLE
import com.mapx.kosten.mosimpa.data.db.Contants.Companion.SENSOR_O2_TABLE

@Entity(tableName = SENSOR_HEART_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = PatientDB::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class SensorHeartDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val patientId: Long = -1,
    val time: Long = -1,
    val heartR: Int = -1,
    val HR_AR: Boolean = false
)