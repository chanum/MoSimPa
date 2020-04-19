package com.mapx.kosten.mosimpa.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.PatientsDao
import com.mapx.kosten.mosimpa.data.entities.PatientDB
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.data.mappers.PatientDataToEntityMapper
import com.mapx.kosten.mosimpa.data.mappers.PatientEntityToDataMapper

class PatientsRepositoryImpl(
    database: MosimpaDatabase
) : PatientsRepository {

    private val dao: PatientsDao = database.patientsDao()
    private val mapperEntityToDB = PatientEntityToDataMapper()
    private val mapperDBtoEntity = PatientDataToEntityMapper()

    /*
    Note: Room uses its own dispatcher to run queries on a background thread.
    Your code should not use withContext(Dispatchers.IO) to call suspending room queries.
     It will complicate the code and make your queries run slower.
        withContext(Dispatchers.IO){
            return@withContext dao.getDeviceIdByPatientId(id)?.deviceId ?: ""
    */
    override suspend fun getAllPatients(): List<PatientEntity> =
        dao.getPatients().map {mapperDBtoEntity.mapFrom(it)}

    override suspend fun getPatientsById(id: Long) =
        mapperDBtoEntity.mapFrom(dao.getPatient(id) ?: PatientDB())

    override suspend fun deletePatientById(id: Long) = dao.removePatient(id)

    override suspend fun savePatient(patient: PatientEntity): Long {
        val devId = patient.deviceId
        val item = dao.getPatientByDeviceId(devId)
        return if (item == null)
                dao.insertPatient(mapperEntityToDB.mapFrom(patient))
            else
            INVALID_PATIENT_DB_ID
    }

    override suspend fun updateNameByDeviceId(patient: PatientEntity): Long {
        val item = dao.getPatientByDeviceId(patient.deviceId)
        if (item != null) {
            val patient = item.copy(name = patient.name)
            return dao.updatePatient(patient).toLong()
        }
        return INVALID_PATIENT_DB_ID
    }

    override suspend fun getDeviceIdByPatientId(id: Long) =
        dao.getDeviceIdByPatientId(id)?.deviceId ?: ""

    override fun observePatients(): LiveData<List<PatientEntity>> {
        return Transformations.map(dao.observePatients()) {
            it.map { mapperDBtoEntity.mapFrom(it) }
        }
    }

    companion object {
        const val INVALID_PATIENT_DB_ID = -1L
    }
}