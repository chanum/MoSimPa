package com.mapx.kosten.mosimpa.presentation.fragments.internments

import androidx.lifecycle.*
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.MQTT_CONNECTION_OK
import com.mapx.kosten.mosimpa.domain.entites.*
import com.mapx.kosten.mosimpa.domain.interactors.internments.GetInternmentsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.entities.InternmentView
import com.mapx.kosten.mosimpa.presentation.mappers.InternmentEntityToViewMapper
import kotlinx.coroutines.launch

class InternmentsViewModel(
    private val connectClientMqttUseCase: ConnectClientMqttUseCase,
    private val getInternmentsUseCase: GetInternmentsUseCase,
    private val updateInternmentsUseCase: UpdateInternmentsUseCase,
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase
): ViewModel() {

    private var currentInternment = InternmentEntity()

    private val mapperEntityToView = InternmentEntityToViewMapper()
    var internments: LiveData<List<InternmentView>> =
        Transformations.map( getInternmentsUseCase.invoke()) {
            list -> list.map { mapperEntityToView.mapFrom(it) }
        }
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke()
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke()
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke()
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke()

    fun connectAndSubscribeToAll(mac: String) {
        viewModelScope.launch {
            // TODO rename: connect Mqtt And Subcribe /monitor and /reads/#
            val status = connectClientMqttUseCase.invoke(mac)
            if (status.equals(MQTT_CONNECTION_OK)) {
                // publish in datakeeper/query cmd internments
                refreshInternments()
            } else {
                // TODO error connection reconnect? or send a toast?
            }
        }
    }

    fun refreshInternments() {
        updateInternmentsUseCase.invoke()
    }

    override fun onCleared() {
        super.onCleared()
        currentInternment = InternmentEntity()
        subscribeIdUseCase.invoke(currentInternment)
    }

}
