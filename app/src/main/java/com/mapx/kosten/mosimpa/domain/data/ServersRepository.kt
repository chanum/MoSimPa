package com.mapx.kosten.mosimpa.domain.data

import com.mapx.kosten.mosimpa.domain.entites.ServerEntity
import kotlinx.coroutines.flow.Flow

interface ServersRepository {
    fun getAll(): Flow<List<ServerEntity>>
    suspend fun saveServer(server: ServerEntity): Long
    suspend fun deleteServer(server: ServerEntity)
}