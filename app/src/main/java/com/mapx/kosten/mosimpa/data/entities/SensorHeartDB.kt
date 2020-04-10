package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Contants.Companion.SENSOR_HEART_TABLE
import com.mapx.kosten.mosimpa.data.db.Contants.Companion.SENSOR_O2_TABLE

@Entity(tableName = SENSOR_HEART_TABLE)
data class SensorHeartDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val deviceId: Long = -1,
    val time: Long = -1,
    val heartR: Int = -1,
    val HR_AR: Boolean = false
)