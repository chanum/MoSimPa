package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patientsTbl")
data class PatientDB(
    @PrimaryKey
    var id: Long = -1,
    var name: String = "",
    var age: Int = -1 ,
    var status: Int = -1
)