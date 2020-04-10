package com.mapx.kosten.mosimpa.domain.data

import androidx.lifecycle.LiveData
import com.mapx.kosten.mosimpa.domain.entites.SensorBloodEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorHeartEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorO2Entity
import io.reactivex.Observable

interface SensorsRepository {
    fun subscribeId(id: Long): Observable<Boolean>
    fun getSensorById(id: Long): Observable<SensorEntity>
    fun getO2Data(): LiveData<SensorO2Entity>
    fun getBloodData(): LiveData<SensorBloodEntity>
    fun getHeartData(): LiveData<SensorHeartEntity>
}