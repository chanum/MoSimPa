package com.mapx.kosten.mosimpa.domain.entites

data class InternmentEntity (
    var id: Long = -1,
    var deviceId: String = "",
    var patient: Patient2Entity = Patient2Entity(),
    var alarms: AlarmsEntity = AlarmsEntity(),
    var location: LocationEntity = LocationEntity()
)