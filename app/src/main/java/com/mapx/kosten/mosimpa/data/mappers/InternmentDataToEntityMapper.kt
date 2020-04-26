package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.InternmentDB
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity

class InternmentDataToEntityMapper {

    fun mapFrom(from: InternmentDB): InternmentEntity {
        return InternmentEntity(
            id = from.id,
            deviceId = from.device,
            patient = from.patient,
            alarms = from.alarms,
            location = from.location
        )
    }
}