package com.mapx.kosten.mosimpa.presentation.fragments.patients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PatientsViewModel: ViewModel() {

    var viewState: MutableLiveData<PatientsViewState> = MutableLiveData()
    // var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = PatientsViewState()
        this.viewState.value = viewState
    }

    fun loadPatients() {
    }
}
