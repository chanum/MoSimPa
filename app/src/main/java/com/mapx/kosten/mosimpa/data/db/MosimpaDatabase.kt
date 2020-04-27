package com.mapx.kosten.mosimpa.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mapx.kosten.mosimpa.data.db.dao.*
import com.mapx.kosten.mosimpa.data.entities.*

@Database(entities = arrayOf(
    InternmentDB::class,
    SensorO2DB::class,
    SensorHeartDB::class,
    SensorBloodDB::class,
    SensorTempDB::class),
    version = 1)
abstract class MosimpaDatabase: RoomDatabase() {
    abstract fun internmentDao(): InternmentsDao
    abstract fun sensorO2Dao(): SensorO2Dao
    abstract fun sensorHeartDao(): SensorHeartDao
    abstract fun sensorBloodDao(): SensorBloodDao
    abstract fun sensorTempDao(): SensorTempDao
}