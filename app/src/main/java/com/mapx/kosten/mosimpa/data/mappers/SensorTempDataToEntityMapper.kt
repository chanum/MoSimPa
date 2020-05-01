package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.SensorTempDB
import com.mapx.kosten.mosimpa.domain.entites.SensorTempEntity

class SensorTempDataToEntityMapper {

    fun mapFrom(from: SensorTempDB): SensorTempEntity {
        return SensorTempEntity(
            id = from.id,
            internmentId = from.internment_id,
            time = from.time,
            temp = from.temp
        )
    }
}