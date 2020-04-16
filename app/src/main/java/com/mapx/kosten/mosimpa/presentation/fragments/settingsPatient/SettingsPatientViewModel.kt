package com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.interactors.patient.DeletePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient.SettingsPatientFragment.Companion.SAVE_ERROR
import com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient.SettingsPatientFragment.Companion.SAVE_OK

class SettingsPatientViewModel(
    private val savePatientUseCase: SavePatientUseCase,
    private val getPatientUseCase: GetPatientUseCase,
    private val deletePatientUseCase: DeletePatientUseCase
) : BaseViewModel() {

    var viewState: MutableLiveData<SettingsPatientViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = SettingsPatientViewState()
        this.viewState.value = viewState
    }

    fun savePatient(id: String, name: String) {
        val patient = PatientEntity(
            deviceId = id,
            name = name
        )

        // TODO check if deviceID exits

        // save Patient
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

    fun getPatient(id: Long) {
        addDisposable(getPatientUseCase.getPatient(id)
            .subscribe({it ->
                var newViewState: SettingsPatientViewState? = null
                if (it.id > 0) {
                    newViewState = viewState.value?.copy(
                        saveStatus = SAVE_OK,
                        patient = it,
                        isLoading = false)
                } else {
                    newViewState = viewState.value?.copy(
                        saveStatus = SAVE_ERROR,
                        patient = null,
                        isLoading = false)
                }
                viewState.value = newViewState
                errorState.value = null
                Log.i(javaClass.simpleName, "Rcv Ok $it")

            }, {
                viewState.value = SettingsPatientViewState(false, SAVE_ERROR, false, null)
                errorState.value = it
                Log.i(javaClass.simpleName, "Rcv Error")
            })
        )
    }

    fun deletePatient(id: Long) {
        addDisposable(deletePatientUseCase.delete(id)
            .subscribe({ status ->
                val newViewState = viewState.value?.copy(
                    saveStatus = SAVE_OK,
                    close = true,
                    isLoading = false)
                viewState.value = newViewState
                errorState.value = null
                Log.i(javaClass.simpleName, "Rcv Ok")
            }, {
                viewState.value = viewState.value?.copy(saveStatus = SAVE_ERROR, isLoading = false)
                errorState.value = it
                Log.i(javaClass.simpleName, "Rcv Error")
            })
        )
    }

}