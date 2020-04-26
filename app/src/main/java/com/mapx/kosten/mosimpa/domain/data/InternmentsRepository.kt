package com.mapx.kosten.mosimpa.domain.data

import androidx.lifecycle.LiveData
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity

interface InternmentsRepository {
    fun getAll(): LiveData<List<InternmentEntity>>
    suspend fun getDeviceIdById(id: Long): String
}