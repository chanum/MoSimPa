package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.entities.SensorDB

@Dao
interface SensorsDao {
    @Query("select * from sensorsTbl")
    fun getSensorData(): List<SensorDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorDB)

    @Query("SELECT * FROM sensorsTbl  ORDER BY id DESC LIMIT 1" )
    fun getSpo2Data(): LiveData<SensorDB>

    @Query("DELETE FROM sensorsTbl")
    fun clear()
}