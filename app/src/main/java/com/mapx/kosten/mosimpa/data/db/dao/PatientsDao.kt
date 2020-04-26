package com.mapx.kosten.mosimpa.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.PATIENTS_TABLE
import com.mapx.kosten.mosimpa.data.entities.PatientDB

@Dao
interface PatientsDao {
    @Query("SELECT * FROM " + PATIENTS_TABLE)
    suspend fun getPatients(): List<PatientDB>

    @Query("SELECT * FROM " + PATIENTS_TABLE + " WHERE id=:id")
    suspend fun getPatient(id: Long): PatientDB?

    @Query("SELECT * FROM " + PATIENTS_TABLE + " WHERE deviceId == :deviceId LIMIT 1")
    suspend fun getPatientByDeviceId(deviceId: String): PatientDB?

    @Query("SELECT * FROM " + PATIENTS_TABLE + " WHERE id=:id LIMIT 1")
    suspend fun getDeviceIdByPatientId(id: Long): PatientDB?

    @Insert
    suspend fun insertPatient(patient: PatientDB): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePatient(patient: PatientDB): Int

    @Query("DELETE FROM " + PATIENTS_TABLE + " WHERE id=:id")
    suspend fun removePatient(id: Long)

    @Query("SELECT * FROM " + PATIENTS_TABLE)
    fun observePatients(): LiveData<List<PatientDB>>
}