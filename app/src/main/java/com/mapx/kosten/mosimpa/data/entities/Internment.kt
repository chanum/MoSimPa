package com.mapx.kosten.mosimpa.data.entities

import com.mapx.kosten.mosimpa.domain.entites.AlarmsEntity
import com.mapx.kosten.mosimpa.domain.entites.LocationEntity
import com.mapx.kosten.mosimpa.domain.entites.Patient2Entity

data class Internment(
    var internment_id: Long = -1,
    var alarms: AlarmsEntity,
    var device: String = "",
    var location: LocationEntity,
    var patient: Patient2Entity
)