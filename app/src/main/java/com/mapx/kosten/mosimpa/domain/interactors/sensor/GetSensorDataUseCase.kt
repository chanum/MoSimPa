package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.common.Transformer
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.domain.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class GetSensorDataUseCase(
    transformer: Transformer<SensorEntity>,
    private val sensorsRepository: SensorsRepository
) : UseCase<SensorEntity>(transformer){

    private val compositeDisposable = CompositeDisposable()

    override fun createObservable(data: Map<String, Any>?): Observable<SensorEntity> {
        val id = data?.get(PARAM_PATIENT_ID)

        id?.let {
            return Observable.fromCallable {
                lateinit var sensorData: SensorEntity
                val id = it as Long
                val disposable = sensorsRepository.getSensorById(id)
                    .subscribe( {
                        sensorData = it.copy()
                        compositeDisposable.clear()
                    },{
                        sensorData.id = -1
                        compositeDisposable.clear()
                    })
                compositeDisposable.add(disposable)
                return@fromCallable sensorData
            }
        }?: return Observable.error{ IllegalArgumentException("Id must be provided.") }

    }

    fun getDataById(id: Long): Observable<SensorEntity> {
        val data = HashMap<String, Long>()
        data[PARAM_PATIENT_ID] = id
        return observable(data)
    }

    companion object {
        private const val PARAM_PATIENT_ID = "param:id"
    }


}