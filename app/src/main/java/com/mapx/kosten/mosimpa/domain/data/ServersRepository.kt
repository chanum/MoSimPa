package com.mapx.kosten.mosimpa.domain.data

import androidx.lifecycle.LiveData
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

interface ServersRepository {
    fun getAll(): LiveData<List<ServerEntity>>
    suspend fun saveServer(server: ServerEntity): Long
    suspend fun deleteServer(server: ServerEntity)
}