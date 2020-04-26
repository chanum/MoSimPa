package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_HEART_TABLE
import com.mapx.kosten.mosimpa.data.entities.SensorHeartDB

@Dao
interface SensorHeartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorHeartDB)

    @Query("SELECT * FROM " + SENSOR_HEART_TABLE + " ORDER BY id DESC LIMIT 1" )
    fun getData(): LiveData<SensorHeartDB>

    @Query("DELETE FROM " + SENSOR_HEART_TABLE)
    fun clear()
}