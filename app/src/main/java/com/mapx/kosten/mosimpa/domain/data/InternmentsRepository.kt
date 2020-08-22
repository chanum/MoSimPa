package com.mapx.kosten.mosimpa.domain.data

import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity
import kotlinx.coroutines.flow.Flow

interface InternmentsRepository {
    fun getAll(): Flow<List<InternmentEntity>>
    suspend fun getDeviceIdById(id: Long): String
}