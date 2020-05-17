package com.mapx.kosten.mosimpa.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.server.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.server.GetServersUseCase
import com.mapx.kosten.mosimpa.domain.interactors.server.SaveServerUseCase
import com.mapx.kosten.mosimpa.domain.interactors.server.SetBrokerConfigUseCase

class LoginViewModelFactory(
    private val saveServerUseCase: SaveServerUseCase,
    private val getServersUseCase: GetServersUseCase,
    private val getBrokerConfigUseCase: GetBrokerConfigUseCase,
    private val setBrokerConfigUseCase: SetBrokerConfigUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(
            saveServerUseCase,
            getServersUseCase,
            getBrokerConfigUseCase,
            setBrokerConfigUseCase
        ) as T
    }
}