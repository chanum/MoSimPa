package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.SensorO2DB
import com.mapx.kosten.mosimpa.domain.entites.SensorO2Entity

class SensorO2DataToEntityMapper {

    fun mapFrom(from: SensorO2DB): SensorO2Entity {
        return SensorO2Entity(
            id = from.id,
            patientId = from.internment_id,
            time = from.time,
            spo2 = from.spo2,
            r = from.r
        )
    }
}