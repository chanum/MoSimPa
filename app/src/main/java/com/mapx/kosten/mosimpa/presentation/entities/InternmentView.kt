package com.mapx.kosten.mosimpa.presentation.entities

import com.mapx.kosten.mosimpa.domain.entites.*

data class InternmentView(
    var id: Long = -1,
    var deviceId: String = "",
    var patient: Patient2Entity = Patient2Entity(),
    var alarms: AlarmsEntity = AlarmsEntity(),
    var location: LocationEntity = LocationEntity(),
    var sensorO2: SensorO2Entity = SensorO2Entity(),
    var sensorBlood: SensorBloodEntity = SensorBloodEntity(),
    var sensorHeart: SensorHeartEntity = SensorHeartEntity(),
    var sensorTemp: SensorTempEntity = SensorTempEntity()
)