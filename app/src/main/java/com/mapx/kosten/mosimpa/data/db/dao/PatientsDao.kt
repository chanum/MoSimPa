package com.mapx.kosten.mosimpa.data.db.dao

import androidx.room.*
import com.mapx.kosten.mosimpa.data.db.Contants.Companion.PATIENTS_TABLE
import com.mapx.kosten.mosimpa.data.entities.PatientDB

@Dao
interface PatientsDao {
    @Query("SELECT * FROM " + PATIENTS_TABLE)
    fun getPatients(): List<PatientDB>

    @Query("SELECT * FROM " + PATIENTS_TABLE + " WHERE id=:id")
    fun getPatient(id: Long): PatientDB?

    @Query("SELECT * FROM " + PATIENTS_TABLE + " WHERE deviceId == :deviceId LIMIT 1")
    fun getPatientByDeviceId(deviceId: String): PatientDB?

    @Insert
    fun insertPatient(patient: PatientDB): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePatient(patient: PatientDB): Int

    @Query("DELETE FROM " + PATIENTS_TABLE)
    fun clear()

    @Query("DELETE FROM " + PATIENTS_TABLE + " WHERE id=:id")
    fun removePatient(id: Long)
}