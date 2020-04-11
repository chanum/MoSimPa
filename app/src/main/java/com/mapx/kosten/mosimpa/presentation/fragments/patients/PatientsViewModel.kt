package com.mapx.kosten.mosimpa.presentation.fragments.patients

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent

class PatientsViewModel(
    private val getPatientsUseCase: GetPatientsUseCase,
    private val connectClientMqttUseCase: ConnectClientMqttUseCase
): BaseViewModel() {

    var viewState: MutableLiveData<PatientsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = PatientsViewState()
        this.viewState.value = viewState
    }

    fun connect() {
        connectClientMqttUseCase.invoke()
    }

    fun loadPatients() {
        addDisposable(getPatientsUseCase.observable()
            .subscribe({ nodes ->
                val newViewState = viewState.value?.copy(
                    isEmpty = nodes.isEmpty(),
                    isLoading = false,
                    patients = nodes)
                viewState.value = newViewState
                errorState.value = null
                Log.i(javaClass.simpleName, "Rcv Ok")
            } , {
                viewState.value = viewState.value?.copy(isLoading = false, isEmpty = false)
                errorState.value = it
                Log.i(javaClass.simpleName, "Rcv Error")
            })
        )
    }
}
