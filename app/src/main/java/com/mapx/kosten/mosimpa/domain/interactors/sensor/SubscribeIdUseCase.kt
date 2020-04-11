package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.common.Transformer
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.interactors.UseCase
import io.reactivex.Observable

class SubscribeIdUseCase(
    transformer: Transformer<Boolean>,
    private val sensorsRepository: SensorsRepository
) : UseCase<Boolean>(transformer){

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val id = data?.get(PARAM_PATIENT_ID)

        id?.let {
            return Observable.fromCallable {
                val id = it as Long
                sensorsRepository.subscribeId(id)
                return@fromCallable true
            }
        }?: return Observable.error{ IllegalArgumentException("Id must be provided.") }

    }

    fun subscribe(id: Long): Observable<Boolean> {
        val data = HashMap<String, Long>()
        data[PARAM_PATIENT_ID] = id
        return observable(data)
    }

    companion object {
        private const val PARAM_PATIENT_ID = "param:id"
    }
}