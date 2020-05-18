package com.mapx.kosten.mosimpa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mapx.kosten.mosimpa.data.db.Constants.Companion.SERVERS_TABLE

@Entity(tableName = SERVERS_TABLE)
data class ServerDB(
    @PrimaryKey(autoGenerate = true)
    var id: Long = -1,
    var name: String = "",
    var ip: String = "",
    var port: Int = -1
)