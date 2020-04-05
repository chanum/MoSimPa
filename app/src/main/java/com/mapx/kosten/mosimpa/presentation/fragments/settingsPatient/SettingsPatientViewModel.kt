package com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.domain.PatientEntity
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent

class SettingsPatientViewModel(
    private val savePatientUseCase: SavePatientUseCase
) : BaseViewModel() {

    var viewState: MutableLiveData<SettingsPatientViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = SettingsPatientViewState()
        this.viewState.value = viewState
    }

    fun savePatientClicked() {}

    fun savePatient(id: Long, name: String) {
        val patient = PatientEntity(id=id, name = name)
        addDisposable(savePatientUseCase.savePatient(patient)
            .subscribe({ status ->
                val newViewState = viewState.value?.copy(
                    saveStatus = 0,
                    close = true,
                    isLoading = false)
                viewState.value = newViewState
                errorState.value = null
                Log.i(javaClass.simpleName, "Save Patient Ok")
            }, {
                viewState.value = viewState.value?.copy(saveStatus = -1, isLoading = false)
                errorState.value = it
                Log.i(javaClass.simpleName, "Error Save Patient")
            })
        )
    }

    fun getPatient() {

    }

    fun deletePatient() {

    }

}