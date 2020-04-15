package com.mapx.kosten.mosimpa.domain.common

class Utils {
    companion object {
        fun convertLongToHexString(from: Long): String {
            return String.format("%02X", from)
        }
    }

}