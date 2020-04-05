package com.mapx.kosten.mosimpa.domain.data

import com.mapx.kosten.mosimpa.domain.PatientEntity
import io.reactivex.Observable

interface PatientsRepository {
    fun getAllPatients(): Observable<List<PatientEntity>>
    fun saveAllPatients()
    fun getPatientsById(id: Long): Observable<PatientEntity>
    fun deletePatientById(id: Long)
    fun savePatient(patient: PatientEntity): Observable<Long>
}