package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.SensorDB
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity

class SensorEntityToDataMapper {

    fun mapFrom(from: SensorEntity): SensorDB {
        return SensorDB(
            id = from.id,
            name = from.name,
            value = from.value
        )
    }
}