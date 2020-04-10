package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Contants.Companion.SENSOR_O2_TABLE

@Entity(tableName = SENSOR_O2_TABLE)
data class SensorO2DB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val deviceId: Long = -1,
    val time: Long = -1,
    val spo2: Float = -1F,
    val r: Float = -1F
)