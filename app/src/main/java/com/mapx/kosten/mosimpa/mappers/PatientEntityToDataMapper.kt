package com.mapx.kosten.mosimpa.mappers

import com.mapx.kosten.mosimpa.data.entities.PatientDB
import com.mapx.kosten.mosimpa.domain.PatientEntity

class PatientEntityToDataMapper {

    fun mapFrom(from: PatientEntity): PatientDB {
        return PatientDB(
            id = from.id,
            name = from.name,
            age = from.age,
            status = from.status
        )
    }
}