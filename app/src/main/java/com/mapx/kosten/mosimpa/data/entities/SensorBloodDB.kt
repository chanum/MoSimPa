package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_BLOOD_TABLE

@Entity(tableName = SENSOR_BLOOD_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = PatientDB::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class SensorBloodDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val patientId: Long = -1,
    val time: Long = -1,
    val sys: Int = -1,
    val dia: Int = -1
)