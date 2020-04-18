package com.mapx.kosten.mosimpa.presentation.fragments.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.ObservePatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.SetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent

class SettingsViewModel (
    private val getPatientsUseCase: GetPatientsUseCase,
    private val getBrokerConfigUseCase: GetBrokerConfigUseCase,
    private val setBrokerConfigUseCase: SetBrokerConfigUseCase,
    private val observePatientsUseCase: ObservePatientsUseCase
): ViewModel() {

    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    // TODO see FLow
    var patients: LiveData<List<PatientEntity>> = observePatientsUseCase.invoke()

    fun getBrokerIp(): String {
        return getBrokerConfigUseCase.invoke()
    }

    fun setBrokerIp(ip: String) {
        setBrokerConfigUseCase.invoke(ip)
    }
}
