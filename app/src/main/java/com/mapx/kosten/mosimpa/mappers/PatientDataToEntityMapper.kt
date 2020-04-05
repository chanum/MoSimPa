package com.mapx.kosten.mosimpa.mappers

import com.mapx.kosten.mosimpa.data.entities.PatientDB
import com.mapx.kosten.mosimpa.domain.PatientEntity

class PatientDataToEntityMapper {

    fun mapFrom(from: PatientDB): PatientEntity {
        return PatientEntity(
            id = from.id,
            name = from.name,
            age = from.age,
            status = from.status
        )
    }
}