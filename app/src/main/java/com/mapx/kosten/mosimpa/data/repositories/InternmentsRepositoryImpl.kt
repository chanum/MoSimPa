package com.mapx.kosten.mosimpa.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.InternmentsDao
import com.mapx.kosten.mosimpa.data.mappers.InternmentDataToEntityMapper
import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity

class InternmentsRepositoryImpl(
    database: MosimpaDatabase
) : InternmentsRepository {

    private val dao: InternmentsDao = database.internmentDao()
    private val mapperDBtoEntity = InternmentDataToEntityMapper()

    override fun getAll(): LiveData<List<InternmentEntity>> {
        return Transformations.map(dao.getAll()) {
            it.map { mapperDBtoEntity.mapFrom(it) }
        }
    }

    override suspend fun getDeviceIdById(id: Long) =
        dao.getDeviceIdById(id)?.device ?: ""

}