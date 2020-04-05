package com.mapx.kosten.mosimpa.presentation.fragments.patients

import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent

class PatientsViewModel(
    private val getPatientsUseCase: GetPatientsUseCase
): BaseViewModel() {

    var viewState: MutableLiveData<PatientsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = PatientsViewState()
        this.viewState.value = viewState
    }

    fun loadPatients() {
    }
}
