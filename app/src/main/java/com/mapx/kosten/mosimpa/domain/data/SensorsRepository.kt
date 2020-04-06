package com.mapx.kosten.mosimpa.domain.data


import io.reactivex.Observable

interface SensorsRepository {
    fun subscribeId(id: Long): Observable<Boolean>
    fun getSensorById(id: Long): Observable<Boolean>
}