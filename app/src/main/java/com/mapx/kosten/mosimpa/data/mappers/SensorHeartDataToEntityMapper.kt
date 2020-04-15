package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.SensorHeartDB
import com.mapx.kosten.mosimpa.domain.entites.SensorHeartEntity

class SensorHeartDataToEntityMapper {

    fun mapFrom(from: SensorHeartDB): SensorHeartEntity {
        return SensorHeartEntity(
            id = from.id,
            patientId = from.patientId,
            time = from.time,
            heartR = from.heartR,
            HR_AR = from.HR_AR
        )
    }
}