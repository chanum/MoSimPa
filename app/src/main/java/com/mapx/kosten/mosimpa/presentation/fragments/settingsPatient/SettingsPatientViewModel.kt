package com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.interactors.patient.DeletePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient.SettingsPatientFragment.Companion.SAVE_ERROR
import com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient.SettingsPatientFragment.Companion.SAVE_OK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SettingsPatientViewModel(
    private val savePatientUseCase: SavePatientUseCase,
    private val getPatientUseCase: GetPatientUseCase,
    private val deletePatientUseCase: DeletePatientUseCase
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var viewState: MutableLiveData<SettingsPatientViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = SettingsPatientViewState()
        this.viewState.value = viewState
    }

    fun savePatient(id: String, name: String) {
        val patient = PatientEntity(deviceId = id, name = name)
        // TODO check if deviceID exits
        // save Patient
        viewModelScope.launch {
            val patients = savePatientUseCase.invoke(patient)
            val newViewState = viewState.value?.copy(
                saveStatus = 0,
                close = true,
                isLoading = false)
            viewState.value = newViewState
            errorState.value = null
            Log.i(javaClass.simpleName, "Rcv Ok")
        }
    }

    fun getPatient(id: Long) {
        viewModelScope.launch {
            var newViewState: SettingsPatientViewState? = null
            val patient = getPatientUseCase.invoke(id)

            if (patient.id > 0) {
                newViewState = viewState.value?.copy(
                    saveStatus = SAVE_OK,
                    patient = patient,
                    isLoading = false)
            } else {
                newViewState = viewState.value?.copy(
                    saveStatus = SAVE_ERROR,
                    patient = null,
                    isLoading = false)
            }
            viewState.value = newViewState
            errorState.value = null
            Log.i(javaClass.simpleName, "Rcv Ok $patient")
        }

    }

    fun deletePatient(id: Long) {
        viewModelScope.launch {
            deletePatientUseCase.invoke(id)
            val newViewState = viewState.value?.copy(
                saveStatus = SAVE_OK,
                close = true,
                isLoading = false)
            viewState.value = newViewState
            errorState.value = null
            Log.i(javaClass.simpleName, "Rcv Ok")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}