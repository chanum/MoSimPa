package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.SensorDB
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity

class SensorDataToEntityMapper {

    fun mapFrom(from: SensorDB): SensorEntity {
        return SensorEntity(
            id = from.id,
            name = from.name,
            value = from.value
        )
    }
}