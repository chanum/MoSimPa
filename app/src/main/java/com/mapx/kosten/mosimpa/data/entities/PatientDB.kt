package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.PATIENTS_TABLE

@Entity(tableName = PATIENTS_TABLE)
data class PatientDB(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var deviceId: String = "",
    var name: String = "",
    var age: Int = -1 ,
    var status: Int = -1,
    val bed: String = "",
    val sex: String = ""
)