package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import com.mapx.kosten.mosimpa.data.preferences.BrokerPreferenceImpl
import com.mapx.kosten.mosimpa.domain.data.SettingsRepository
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

class SettingsRepositoryImpl(
    private val context: Context
) : SettingsRepository {

    override fun setCurrentServer(server: ServerEntity) {
        BrokerPreferenceImpl(context).setBrokerId(server.id)
        BrokerPreferenceImpl(context).setBrokerName(server.name)
        BrokerPreferenceImpl(context).setBrokerIP(server.ip)
    }

    override fun getCurrentServer(): ServerEntity {
        return ServerEntity (
            BrokerPreferenceImpl(context).getBrokerId(),
            BrokerPreferenceImpl(context).getBrokerName(),
            BrokerPreferenceImpl(context).getBrokerIP()
        )

    }
}