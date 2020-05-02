package com.mapx.kosten.mosimpa.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.interactors.settings.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.SetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent

class SettingsViewModel (
    private val getBrokerConfigUseCase: GetBrokerConfigUseCase,
    private val setBrokerConfigUseCase: SetBrokerConfigUseCase
): ViewModel() {

    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    fun getBrokerIp(): String {
        return getBrokerConfigUseCase.invoke()
    }

    fun setBrokerIp(ip: String) {
        setBrokerConfigUseCase.invoke(ip)
    }
}
