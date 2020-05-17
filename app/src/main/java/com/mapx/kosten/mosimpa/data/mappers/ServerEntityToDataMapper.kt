package com.mapx.kosten.mosimpa.data.mappers

import com.mapx.kosten.mosimpa.data.entities.ServerDB
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

class ServerEntityToDataMapper {

    fun mapFrom(from: ServerEntity): ServerDB {
        return ServerDB(
            id = from.id,
            name = from.name,
            ip = from.ip,
            port = from.port
        )
    }
}