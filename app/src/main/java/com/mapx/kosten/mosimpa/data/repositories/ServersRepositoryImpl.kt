package com.mapx.kosten.mosimpa.data.repositories

import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.ServersDao
import com.mapx.kosten.mosimpa.data.mappers.ServerDataToEntityMapper
import com.mapx.kosten.mosimpa.data.mappers.ServerEntityToDataMapper
import com.mapx.kosten.mosimpa.domain.data.ServersRepository
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServersRepositoryImpl(
    database: MosimpaDatabase
) : ServersRepository {

    private val dao: ServersDao = database.serverDao()
    private val mapperEntityToData = ServerEntityToDataMapper()
    private val mapperDataToEntity = ServerDataToEntityMapper()

    override fun getAll(): Flow<List<ServerEntity>> {
        return dao.getAll().map {
            it.map { mapperDataToEntity.mapFrom(it) }
        }
    }

    override suspend fun saveServer(server: ServerEntity): Long {
        val srv = dao.getServerByName(server.name)
        if (srv != null) {
            dao.deleteById(srv.id)
        }
        return dao.insert(mapperEntityToData.mapFrom(server))
    }

    override suspend fun deleteServer(server: ServerEntity) {
        return dao.deleteById(mapperEntityToData.mapFrom(server).id)
    }

}