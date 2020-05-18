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
        var server = ServerEntity(0, name, ip)
        viewModelScope.launch {
            val id = saveServerUseCase.invoke(server)
            if (id > 0) {
                server.id = id
                setBrokerConfigUseCase.invoke(server)
            }
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