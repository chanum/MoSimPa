package com.mapx.kosten.mosimpa.data.entities

import androidx.room.PrimaryKey

data class DeviceDB(
    @PrimaryKey
    val id: Long = -1,
    val mac: String = "",
    val date_time_added: Long = -1,
    val date_time_last_seen: Long = 1,
    val battmv: Int = -1,
    val timems: Long = -1,
    val msgid: Long = -1,
    val status: String = ""
)