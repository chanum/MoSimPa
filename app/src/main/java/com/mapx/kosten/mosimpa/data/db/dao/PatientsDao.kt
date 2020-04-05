package com.mapx.kosten.mosimpa.data.db.dao

import androidx.room.*
import com.mapx.kosten.mosimpa.data.entities.PatientDB

@Dao
interface PatientsDao {
    @Query("SELECT * FROM patientsTbl")
    fun getPatients(): List<PatientDB>

    @Query("SELECT * FROM patientsTbl WHERE id=:id")
    fun getPatient(id: Long): PatientDB?

    @Insert
    fun insertPatient(patient: PatientDB): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePatient(patient: PatientDB): Int

    @Query("DELETE FROM patientsTbl")
    fun clear()

    @Query("DELETE FROM patientsTbl WHERE id=:id")
    fun removePatient(id: Long)
}