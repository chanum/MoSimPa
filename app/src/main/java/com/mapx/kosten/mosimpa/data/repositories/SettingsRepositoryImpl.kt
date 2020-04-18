package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import com.mapx.kosten.mosimpa.data.preferences.BrokerIpPreferenceImpl
import com.mapx.kosten.mosimpa.domain.data.SettingsRepository

class SettingsRepositoryImpl(
    private val context: Context
) : SettingsRepository {

    override fun setBrokerIp(ip: String) {
        BrokerIpPreferenceImpl(context).setBrokerIP(ip)
    }

    override fun getBrokerIp(): String {
        return BrokerIpPreferenceImpl(context).getBrokerIP()
    }
}