package com.mapx.kosten.mosimpa.domain.entites

data class PatientEntity (
    var id: Long = -1,
    val name: String = "---------------",
    val status: Int = -1,
    val age: Int = -1,
    val bed: String = "-------",
    val sex: String = "----"
)