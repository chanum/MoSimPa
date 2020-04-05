package com.mapx.kosten.mosimpa.domain.data

import com.mapx.kosten.mosimpa.domain.PatientEntity
import io.reactivex.Observable

interface PatientsRepository {
    fun getAllPatients()
    fun saveAllPatients()
    fun getPatientsById()
    fun deletePatientById()
    fun savePatient(patient: PatientEntity): Observable<Long>
}