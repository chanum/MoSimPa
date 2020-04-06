package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.common.Transformer
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.interactors.UseCase
import io.reactivex.Observable

class GetSensorDataUseCase(
    transformer: Transformer<Boolean>,
    private val sensorsRepository: SensorsRepository
) : UseCase<Boolean>(transformer){

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        return sensorsRepository.getSensorById(1)
    }

}