package com.mapx.kosten.mosimpa.presentation.fragments.settings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.SetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SettingsViewModel (
    private val getPatientsUseCase: GetPatientsUseCase,
    private val getBrokerConfigUseCase: GetBrokerConfigUseCase,
    private val setBrokerConfigUseCase: SetBrokerConfigUseCase
): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var viewState: MutableLiveData<SettingsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = SettingsViewState()
        this.viewState.value = viewState
    }

    fun loadPatients() {
        viewModelScope.launch {
            val patients = getPatientsUseCase.invoke()
            val newViewState = viewState.value?.copy(
                isEmpty = patients.isEmpty(),
                isLoading = false,
                patients = patients)
            viewState.value = newViewState
            errorState.value = null
            Log.i(javaClass.simpleName, "Rcv Ok")
        }
    }

    fun getBrokerIp(): String {
        return getBrokerConfigUseCase.invoke()
    }

    fun setBrokerIp(ip: String) {
        setBrokerConfigUseCase.invoke(ip)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
