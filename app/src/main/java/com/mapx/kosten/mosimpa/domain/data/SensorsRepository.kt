package com.mapx.kosten.mosimpa.domain.data

import androidx.lifecycle.LiveData
import com.mapx.kosten.mosimpa.domain.entites.*

interface SensorsRepository {

    suspend fun connectMqtt()

    fun subscribeId(internment: InternmentEntity)
    fun unSubscribeId(internment: InternmentEntity)

    suspend fun subscribeToAll()

    fun observeDevices(): LiveData<String>

    fun getO2Data(internment: InternmentEntity): LiveData<SensorO2Entity>
    fun getBloodData(internment: InternmentEntity): LiveData<SensorBloodEntity>
    fun getHeartData(internment: InternmentEntity): LiveData<SensorHeartEntity>
    fun getTempData(internment: InternmentEntity): LiveData<SensorTempEntity>

}