package com.mapx.kosten.mosimpa.domain.data

import androidx.lifecycle.LiveData
import com.mapx.kosten.mosimpa.domain.entites.*

interface SensorsRepository {
    suspend fun connectMqtt()

    suspend fun subscribeId(patient: PatientEntity)
    fun unSubscribeId(patient: PatientEntity)

    suspend fun subscribeToAll()

    fun getO2Data(patient: PatientEntity): LiveData<SensorO2Entity>
    fun getBloodData(patient: PatientEntity): LiveData<SensorBloodEntity>
    fun getHeartData(patient: PatientEntity): LiveData<SensorHeartEntity>
    fun getTempData(patient: PatientEntity): LiveData<SensorTempEntity>
}