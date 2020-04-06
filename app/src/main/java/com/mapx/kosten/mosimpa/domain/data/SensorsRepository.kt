package com.mapx.kosten.mosimpa.domain.data

import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import io.reactivex.Observable

interface SensorsRepository {
    fun subscribeId(id: Long): Observable<Boolean>
    fun getSensorById(id: Long): Observable<Boolean>
}