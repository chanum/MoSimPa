package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Contants.Companion.PATIENTS_TABLE

@Entity(tableName = PATIENTS_TABLE)
data class PatientDB(
    @PrimaryKey
    var id: Long = -1,
    var name: String = "",
    var age: Int = -1 ,
    var status: Int = -1,
    val bed: String = "",
    val sex: String = ""
)