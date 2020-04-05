package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.PatientEntity
import com.mapx.kosten.mosimpa.domain.common.Transformer
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalArgumentException

class SavePatientUseCase (
    transformer: Transformer<PatientEntity>,
    private val patientsRepository: PatientsRepository
) : UseCase<PatientEntity>(transformer){

    private val compositeDisposable = CompositeDisposable()

    override fun createObservable(data: Map<String, Any>?): Observable<PatientEntity> {
        val patientEntity = data?.get(PARAM_PATIENT_ENTITY)
        patientEntity?.let {
            return Observable.fromCallable {
                lateinit var newPatient: PatientEntity
                val patient = it as PatientEntity
                val disposable = patientsRepository.savePatient(patient)
                    .subscribe(
                        {
                            newPatient = patient.copy()
                            newPatient.id = it
                            compositeDisposable.clear()
                        },{
                            newPatient = patient.copy()
                            newPatient.id = -1
                            compositeDisposable.clear()
                        })
                compositeDisposable.add(disposable)
                return@fromCallable newPatient
            }
        }?: return Observable.error{ IllegalArgumentException("UserEntity must be provided.") }
    }

    fun savePatient(patient: PatientEntity): Observable<PatientEntity> {
        val data = HashMap<String, PatientEntity>()
        data[PARAM_PATIENT_ENTITY] = patient
        return observable(data)
    }

    companion object {
        private const val PARAM_PATIENT_ENTITY = "param:patientEntity"
    }

}