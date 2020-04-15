package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.PatientDB
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity

class PatientEntityToDataMapper {

    fun mapFrom(from: PatientEntity): PatientDB {
        return PatientDB(
            id = from.id,
            deviceId = from.deviceId,
            name = from.name,
            age = from.age,
            status = from.status,
            bed = from.bed,
            sex = from.sex
        )
    }
}