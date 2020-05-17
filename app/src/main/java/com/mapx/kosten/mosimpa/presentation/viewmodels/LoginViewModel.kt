package com.mapx.kosten.mosimpa.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity
import com.mapx.kosten.mosimpa.domain.interactors.settings.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.SetBrokerConfigUseCase

class LoginViewModel(
    private val getBrokerConfigUseCase: GetBrokerConfigUseCase,
    private val setBrokerConfigUseCase: SetBrokerConfigUseCase
): ViewModel() {


    fun getCurrentServer(): ServerEntity {
        return getBrokerConfigUseCase.invoke()
    }

    fun saveServer(server: ServerEntity) {
        setBrokerConfigUseCase.invoke(server)
    }

}