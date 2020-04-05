package com.mapx.kosten.mosimpa.presentation.fragments.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {

    var viewState: MutableLiveData<SettingsViewState> = MutableLiveData()
    // var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = SettingsViewState()
        this.viewState.value = viewState
    }

    fun loadPatients() {
    }
}
