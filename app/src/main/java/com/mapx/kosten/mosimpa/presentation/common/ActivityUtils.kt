package com.mapx.kosten.mosimpa.presentation.common

import android.app.Activity
import android.content.Intent
import com.mapx.kosten.mosimpa.R

object ActivityUtils {
    fun startActivityWithCrossFade(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun startActivityFromBottomToTop(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    fun startActivityFromLefToRight(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun startActivityFromRightToLeft(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}