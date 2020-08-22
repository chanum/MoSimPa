package com.mapx.kosten.mosimpa.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_HEART_TABLE
import com.mapx.kosten.mosimpa.data.entities.SensorHeartDB
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorHeartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorHeartDB)

    @Query("SELECT * FROM " + SENSOR_HEART_TABLE + " ORDER BY id DESC LIMIT 1" )
    fun getData(): Flow<SensorHeartDB?>

    @Query("DELETE FROM " + SENSOR_HEART_TABLE)
    fun clear()

    @Query("SELECT * FROM " + SENSOR_HEART_TABLE + " WHERE internment_id=:id LIMIT 1" )
    fun getDataByInternmentId(id: Long): Flow<SensorHeartDB?>
}