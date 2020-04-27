package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.INTERNMENTS_TABLE
import com.mapx.kosten.mosimpa.domain.entites.AlarmsEntity
import com.mapx.kosten.mosimpa.domain.entites.LocationEntity
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity

@Entity(tableName = INTERNMENTS_TABLE)
data class InternmentDB(
    @PrimaryKey
    var id: Long = -1,
    var device: String = "",
    @Embedded
    var patient: PatientEntity,
    @Embedded
    var location: LocationEntity,
    @Embedded
    var alarms: AlarmsEntity
)