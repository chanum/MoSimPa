package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.db.Contants.Companion.SENSOR_TEMP_TABLE
import com.mapx.kosten.mosimpa.data.entities.SensorTempDB

@Dao
interface SensorTempDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorTempDB)

    @Query("SELECT * FROM " + SENSOR_TEMP_TABLE + " ORDER BY id DESC LIMIT 1" )
    fun getData(): LiveData<SensorTempDB>

    @Query("DELETE FROM " + SENSOR_TEMP_TABLE)
    fun clear()
}