package com.mapx.kosten.mosimpa.domain.data


interface SettingsRepository {
    fun setBrokerIp(ip: String)
    fun getBrokerIp(): String
}