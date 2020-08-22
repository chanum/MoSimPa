package com.mapx.kosten.mosimpa.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SENSOR_BLOOD_TABLE
import com.mapx.kosten.mosimpa.data.entities.SensorBloodDB
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorBloodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensor: SensorBloodDB)

    @Query("SELECT * FROM " + SENSOR_BLOOD_TABLE + " ORDER BY id DESC LIMIT 1" )
    fun getData(): Flow<SensorBloodDB?>

    @Query("DELETE FROM " + SENSOR_BLOOD_TABLE)
    fun clear()

    @Query("SELECT * FROM " + SENSOR_BLOOD_TABLE + " WHERE internment_id=:id LIMIT 1" )
    fun getDataByInternmentId(id: Long): Flow<SensorBloodDB?>
}