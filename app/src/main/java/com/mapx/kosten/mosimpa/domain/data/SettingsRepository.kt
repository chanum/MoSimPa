package com.mapx.kosten.mosimpa.domain.data


interface SettingsRepository {
    suspend fun setBrokerIp(ip: String)
    fun getBrokerIp(): String
}