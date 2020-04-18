package com.mapx.kosten.mosimpa.domain.data

import androidx.lifecycle.LiveData
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity

interface PatientsRepository {
    suspend fun getAllPatients(): List<PatientEntity>
    suspend fun getPatientsById(id: Long): PatientEntity
    suspend fun deletePatientById(id: Long)
    suspend fun savePatient(patient: PatientEntity): Long
    suspend fun updateNameByDeviceId(patient: PatientEntity): Long
    suspend fun getDeviceIdByPatientId(id: Long): String

    fun observePatients(): LiveData<List<PatientEntity>>
}