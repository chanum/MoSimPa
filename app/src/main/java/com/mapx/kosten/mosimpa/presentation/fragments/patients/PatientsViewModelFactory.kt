package com.mapx.kosten.mosimpa.presentation.fragments.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PatientsViewModelFactory () : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PatientsViewModel() as T
    }
}