package com.mapx.kosten.mosimpa.presentation.viewmodels

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

    private val mapperEntityToView = InternmentEntityToViewMapper()
    var internments: LiveData<List<InternmentView>>
    var sensorO2Value: LiveData<SensorO2Entity?>
    var sensorBloodValue: LiveData<SensorBloodEntity?>
    var sensorHeartValue: LiveData<SensorHeartEntity?>
    var sensorTempValue: LiveData<SensorTempEntity?>
    val snackBar = MutableLiveData<String?>()

    init {
        internments = Transformations.map(getInternmentsUseCase.invoke().asLiveData()) {
                    list -> list.map { mapperEntityToView.mapFrom(it) }
            }
        sensorO2Value = getO2DataUseCase.invoke().asLiveData()
        sensorBloodValue = getBloodDataUseCase.invoke().asLiveData()
        sensorHeartValue = getHeartDataUseCase.invoke().asLiveData()
        sensorTempValue = getTempDataUseCase.invoke().asLiveData()
    }

    fun connectAndSubscribeToAll(mac: String) {
        viewModelScope.launch {
            // TODO rename: connect Mqtt And Subcribe /monitor and /reads/#
            val status = connectClientMqttUseCase.invoke(mac)
            if (status.equals(MQTT_CONNECTION_OK)) {
                // publish in datakeeper/query cmd internments
                updateInternmentsUseCase.invoke()
            } else {
                // TODO error connection reconnect? or send a toast?
                snackBar.value = ""
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscribeIdUseCase.invoke(InternmentEntity())
    }

}
