package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_BLOOD_TABLE
import com.mapx.kosten.mosimpa.data.entities.SensorBloodDB

@Dao
interface SensorBloodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorBloodDB)

    @Query("SELECT * FROM " + SENSOR_BLOOD_TABLE + " ORDER BY id DESC LIMIT 1" )
    fun getData(): LiveData<SensorBloodDB>

    @Query("DELETE FROM " + SENSOR_BLOOD_TABLE)
    fun clear()
}