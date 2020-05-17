package com.mapx.kosten.mosimpa.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.ServersDao
import com.mapx.kosten.mosimpa.data.mappers.ServerDataToEntityMapper
import com.mapx.kosten.mosimpa.data.mappers.ServerEntityToDataMapper
import com.mapx.kosten.mosimpa.domain.data.ServersRepository
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

class ServersRepositoryImpl(
    database: MosimpaDatabase
) : ServersRepository {

    private val dao: ServersDao = database.serverDao()
    private val mapperEntityToData = ServerEntityToDataMapper()
    private val mapperDataToEntity = ServerDataToEntityMapper()

    override fun getAll(): LiveData<List<ServerEntity>> {
        return Transformations.map(dao.getAll()) {
            it.map { mapperDataToEntity.mapFrom(it) }
        }
    }

    override suspend fun saveServer(server: ServerEntity): Long {
        return dao.insert(mapperEntityToData.mapFrom(server))
    }

    override suspend fun deleteServer(server: ServerEntity) {
        return dao.deleteById(mapperEntityToData.mapFrom(server).id)
    }

}