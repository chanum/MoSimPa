package com.mapx.kosten.mosimpa.data.repositories

import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.PatientsDao
import com.mapx.kosten.mosimpa.domain.PatientEntity
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.mappers.PatientEntityToDataMapper
import io.reactivex.Observable

class PatientsRepositoryImpl(
    database: MosimpaDatabase
) : PatientsRepository {

    private val dao: PatientsDao = database.patientsDao()
    private val mapperEntityToDB = PatientEntityToDataMapper()

    override fun getAllPatients() {
        TODO("Not yet implemented")
    }

    override fun saveAllPatients() {
        TODO("Not yet implemented")
    }

    override fun getPatientsById() {
        TODO("Not yet implemented")
    }

    override fun deletePatientById() {
        TODO("Not yet implemented")
    }

    override fun savePatient(patient: PatientEntity): Observable<Long> {
        val item = dao.getPatient(patient.id)
        return Observable.fromCallable {
            if (item == null)
                dao.insertPatient(mapperEntityToDB.mapFrom(patient))
            else
                dao.updatePatient(mapperEntityToDB.mapFrom(patient)).toLong()
        }
    }
}