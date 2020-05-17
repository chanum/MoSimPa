package com.mapx.kosten.mosimpa.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity
import com.mapx.kosten.mosimpa.domain.interactors.server.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.server.SetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent

class SettingsViewModel (
    private val getBrokerConfigUseCase: GetBrokerConfigUseCase,
    private val setBrokerConfigUseCase: SetBrokerConfigUseCase
): ViewModel() {

    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    fun getCurrentServerInfo(): ServerEntity {
        return getBrokerConfigUseCase.invoke()
    }

    fun saveServer(server: ServerEntity) {
        setBrokerConfigUseCase.invoke(server)
    }
}
