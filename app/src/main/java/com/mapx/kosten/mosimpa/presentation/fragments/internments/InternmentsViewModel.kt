package com.mapx.kosten.mosimpa.presentation.fragments.internments

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke(currentInternment)
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke(currentInternment)
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke(currentInternment)
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke(currentInternment)

    fun connectAndSubscribeToAll(mac: String) {
        viewModelScope.launch {
            val status = connectClientMqttUseCase.invoke(mac)
            if (status.equals(MQTT_CONNECTION_OK)) {
                refreshInternments()
            } else {
                // TODO error connection
            }
        }
    }

    fun refreshInternments() {
        updateInternmentsUseCase.invoke()
    }

    fun subscribePatient(internment: InternmentView) {
        viewModelScope.launch {
            val internmentEntity = InternmentEntity(id = internment.id, deviceId = internment.deviceId)
            subscribeIdUseCase.invoke(internmentEntity)
            currentInternment = internmentEntity
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentInternment = InternmentEntity()
        subscribeIdUseCase.invoke(currentInternment)
    }

}
