package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.SensorSpo2DB
import com.mapx.kosten.mosimpa.domain.entites.SensorSpo2Entity

class SensorSpo2DataToEntityMapper {

    fun mapFrom(from: SensorSpo2DB): SensorSpo2Entity {
        return SensorSpo2Entity(
            id = from.id,
            deviceId = from.deviceId,
            time = from.time,
            spo2 = from.spo2,
            r = from.r
        )
    }
}