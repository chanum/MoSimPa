package com.mapx.kosten.mosimpa.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mapx.kosten.mosimpa.data.db.dao.PatientsDao
import com.mapx.kosten.mosimpa.data.db.dao.SensorSpo2Dao
import com.mapx.kosten.mosimpa.data.db.dao.SensorsDao
import com.mapx.kosten.mosimpa.data.entities.PatientDB
import com.mapx.kosten.mosimpa.data.entities.SensorDB
import com.mapx.kosten.mosimpa.data.entities.SensorSpo2DB

@Database(entities = arrayOf(
    PatientDB::class,
    SensorDB::class,
    SensorSpo2DB::class),
    version = 1)
abstract class MosimpaDatabase: RoomDatabase() {
    abstract fun patientsDao(): PatientsDao
    abstract fun sensorsDao(): SensorsDao
    abstract fun sensorSpo2Dao(): SensorSpo2Dao
}