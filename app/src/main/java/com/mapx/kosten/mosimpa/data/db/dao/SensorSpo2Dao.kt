package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.entities.SensorSpo2DB

@Dao
interface SensorSpo2Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorSpo2DB)

    @Query("SELECT * FROM sensorSpo2Tbl  ORDER BY id DESC LIMIT 1" )
    fun getData(): LiveData<SensorSpo2DB>

    @Query("DELETE FROM sensorSpo2Tbl")
    fun clear()
}