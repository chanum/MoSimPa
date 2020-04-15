package com.mapx.kosten.mosimpa.data.repositories

import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.PatientsDao
import com.mapx.kosten.mosimpa.data.entities.PatientDB
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.data.mappers.PatientDataToEntityMapper
import com.mapx.kosten.mosimpa.data.mappers.PatientEntityToDataMapper
import io.reactivex.Observable

class PatientsRepositoryImpl(
    database: MosimpaDatabase
) : PatientsRepository {

    private val dao: PatientsDao = database.patientsDao()
    private val mapperEntityToDB = PatientEntityToDataMapper()
    private val mapperDBtoEntity = PatientDataToEntityMapper()

    override fun getAllPatients(): Observable<List<PatientEntity>> {
        return Observable.fromCallable {dao.getPatients().map {mapperDBtoEntity.mapFrom(it)}}
    }

    override fun saveAllPatients() {
        TODO("Not yet implemented")
    }

    override fun getPatientsById(id: Long): Observable<PatientEntity> {
        return Observable.fromCallable {
            mapperDBtoEntity.mapFrom(dao.getPatient(id) ?: PatientDB()
            )
        }
    }

    override fun deletePatientById(id: Long) {
        dao.removePatient(id)
    }

    override fun savePatient(patient: PatientEntity): Observable<Long> {
        val devId = patient.deviceId
        val item = dao.getPatientByDeviceId(devId)
        return Observable.fromCallable {
            if (item == null)
                dao.insertPatient(mapperEntityToDB.mapFrom(patient))
            else
                dao.updatePatient(mapperEntityToDB.mapFrom(patient)).toLong()
        }
    }
}