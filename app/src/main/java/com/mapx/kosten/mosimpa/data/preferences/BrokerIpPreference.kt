package com.mapx.kosten.mosimpa.data.preferences

interface BrokerIpPreference {
    fun getBrokerIP(): String
    fun setBrokerIP(ip: String)
}