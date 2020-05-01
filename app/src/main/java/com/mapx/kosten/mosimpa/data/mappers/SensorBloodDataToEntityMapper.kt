package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.SensorBloodDB
import com.mapx.kosten.mosimpa.domain.entites.SensorBloodEntity

class SensorBloodDataToEntityMapper {

    fun mapFrom(from: SensorBloodDB): SensorBloodEntity {
        return SensorBloodEntity(
            id = from.id,
            internmentId = from.internment_id,
            time = from.time,
            sys = from.sys,
            dia = from.dia
        )
    }
}