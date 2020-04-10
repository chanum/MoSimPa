package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensorSpo2Tbl")
data class SensorSpo2DB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val deviceId: Long = -1,
    val time: Long = -1,
    val spo2: Float = -1F,
    val r: Float = -1F
)