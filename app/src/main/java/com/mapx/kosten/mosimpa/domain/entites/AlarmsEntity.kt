package com.mapx.kosten.mosimpa.domain.entites

data class AlarmsEntity (
    val spo2_lt: Float = 0F,
    val hr_lt: Float = 0F,
    val hr_gt: Float = 0F,
    val bt_gt: Float = 0F,
    val bp_sys_lt: Float = 0F,
    val bp_sys_gt: Float = 0F
)