package com.mapx.kosten.mosimpa.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity
import com.mapx.kosten.mosimpa.domain.interactors.server.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val saveServerUseCase: SaveServerUseCase,
    private val getServersUseCase: GetServersUseCase,
    private val deleteServerUseCase: DeleteServerUseCase,
    private val getBrokerConfigUseCase: GetBrokerConfigUseCase,
    private val setBrokerConfigUseCase: SetBrokerConfigUseCase
): ViewModel() {

    var servers: LiveData<List<ServerEntity>> = getServersUseCase.invoke()

    fun getCurrentServer(): ServerEntity {
        return getBrokerConfigUseCase.invoke()
    }

    fun saveCurrentServer(server: ServerEntity) {
        setBrokerConfigUseCase.invoke(server)
    }

    fun saveServer(name: String, ip: String): Boolean {
        val server = ServerEntity(0, name, ip)
        // TODO: save in DB, replace if exists?
        viewModelScope.launch {
            saveServerUseCase.invoke(server)
            // TODO: if save ok in DB then save in preferences
            setBrokerConfigUseCase.invoke(server)
        }
        // TODO
        return true
    }

    fun deleteServer(server: ServerEntity) {
        viewModelScope.launch {
            deleteServerUseCase.invoke(server)
        }
    }

}