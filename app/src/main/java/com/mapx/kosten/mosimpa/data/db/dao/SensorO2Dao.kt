package com.mapx.kosten.mosimpa.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_O2_TABLE
import com.mapx.kosten.mosimpa.data.entities.SensorO2DB
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorO2Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorO2DB)

    @Query("SELECT * FROM " + SENSOR_O2_TABLE + " ORDER BY id DESC LIMIT 1" )
    fun getData(): Flow<SensorO2DB?>

    @Query("DELETE FROM " + SENSOR_O2_TABLE)
    fun clear()

    @Query("SELECT * FROM " + SENSOR_O2_TABLE + " WHERE internment_id=:id LIMIT 1" )
    fun getDataByInternmentId(id: Long): Flow<SensorO2DB?>
}