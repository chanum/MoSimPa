package com.mapx.kosten.mosimpa.presentation.mappers

import com.mapx.kosten.mosimpa.domain.entites.*
import com.mapx.kosten.mosimpa.presentation.entities.InternmentView

class InternmentEntityToViewMapper {

    fun mapFrom(from: InternmentEntity): InternmentView {
        return InternmentView(
            id = from.id,
            deviceId = from.deviceId,
            patient = from.patient,
            alarms = from.alarms,
            location = from.location,
            sensorO2 = SensorO2Entity(),
            sensorBlood = SensorBloodEntity(),
            sensorHeart = SensorHeartEntity(),
            sensorTemp = SensorTempEntity()
        )
    }

}