package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.PATIENTS2_TABLE

@Entity(tableName = PATIENTS2_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = InternmentDB::class,
            parentColumns = ["id"],
            childColumns = ["patient_id"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class PatientDB2(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    var patient_id: Long = -1,
    var data_time_added: Long = -1,
    var name: String = "",
    var surname: String = "",
    var age: Int = -1,
    var gender: String = "",
    var dni: Long = -1,
    var comments: String = ""
)