package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_O2_TABLE
import com.mapx.kosten.mosimpa.data.entities.SensorO2DB

@Dao
interface SensorO2Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorO2DB)

    @Query("SELECT * FROM " + SENSOR_O2_TABLE + " ORDER BY id DESC LIMIT 1" )
    fun getData(): LiveData<SensorO2DB>

    @Query("DELETE FROM " + SENSOR_O2_TABLE)
    fun clear()
}