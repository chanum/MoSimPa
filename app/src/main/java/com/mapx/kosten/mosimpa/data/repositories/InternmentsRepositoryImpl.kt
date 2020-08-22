package com.mapx.kosten.mosimpa.data.repositories

import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.InternmentsDao
import com.mapx.kosten.mosimpa.data.mappers.InternmentDataToEntityMapper
import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InternmentsRepositoryImpl(
    database: MosimpaDatabase
) : InternmentsRepository {

    private val dao: InternmentsDao = database.internmentDao()
    private val mapperDBtoEntity = InternmentDataToEntityMapper()

    override fun getAll(): Flow<List<InternmentEntity>> {
        return dao.getAll().map {
            it.map { mapperDBtoEntity.mapFrom(it) }
        }
    }

    override suspend fun getDeviceIdById(id: Long) =
        dao.getDeviceIdById(id)?.device ?: ""

}