package com.mapx.kosten.mosimpa.data.mappers
import com.mapx.kosten.mosimpa.data.entities.ServerDB
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

class ServerDataToEntityMapper {

    fun mapFrom(from: ServerDB): ServerEntity {
        return ServerEntity(
            id = from.id,
            name = from.name,
            ip = from.ip,
            port = from.port
        )
    }
}