package com.mapx.kosten.mosimpa.domain.data

import androidx.lifecycle.LiveData
import com.mapx.kosten.mosimpa.domain.entites.*
import io.reactivex.Observable

interface SensorsRepository {
    fun connectMqtt()

    suspend fun subscribeId(id: Long)
    fun unSubscribeId(id: Long)

    fun getO2Data(id: Long): LiveData<SensorO2Entity>
    fun getBloodData(id: Long): LiveData<SensorBloodEntity>
    fun getHeartData(id: Long): LiveData<SensorHeartEntity>
    fun getTempData(id: Long): LiveData<SensorTempEntity>
}