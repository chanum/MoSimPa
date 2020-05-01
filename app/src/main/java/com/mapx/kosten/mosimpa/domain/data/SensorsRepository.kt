package com.mapx.kosten.mosimpa.domain.data

import androidx.lifecycle.LiveData
import com.mapx.kosten.mosimpa.domain.entites.*

interface SensorsRepository {

    suspend fun connectMqtt(mac: String): String

    fun subscribeId(internment: InternmentEntity)
    fun unSubscribeId(internment: InternmentEntity)

    suspend fun subscribeToAll(): String

    fun updateInternments()

    fun getO2Data(): LiveData<SensorO2Entity>
    fun getBloodData(): LiveData<SensorBloodEntity>
    fun getHeartData(): LiveData<SensorHeartEntity>
    fun getTempData(): LiveData<SensorTempEntity>

}