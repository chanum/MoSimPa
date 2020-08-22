package com.mapx.kosten.mosimpa.domain.data

import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

interface SettingsRepository {
    fun setCurrentServer(server: ServerEntity)
    fun getCurrentServer(): ServerEntity
}