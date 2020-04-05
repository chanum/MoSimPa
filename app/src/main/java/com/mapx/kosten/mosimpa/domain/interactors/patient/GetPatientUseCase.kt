package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.common.Transformer
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalArgumentException

class GetPatientUseCase (
    transformer: Transformer<PatientEntity>,
    private val patientsRepository: PatientsRepository
) : UseCase<PatientEntity>(transformer){

    private val compositeDisposable = CompositeDisposable()

    override fun createObservable(data: Map<String, Any>?): Observable<PatientEntity> {
        val patientId = data?.get(PARAM_PATIENT_ID)

        patientId?.let {
            return Observable.fromCallable {
                lateinit var newPatient: PatientEntity
                val id = it as Long
                val disposable = patientsRepository.getPatientsById(id)
                    .subscribe(
                        {
                            newPatient = it.copy()
                            compositeDisposable.clear()
                        },{
                            newPatient.id = id
                            compositeDisposable.clear()
                        })
                compositeDisposable.add(disposable)
                return@fromCallable newPatient
            }
        }?: return Observable.error{ IllegalArgumentException("PatientEntity must be provided.") }
    }

    fun getPatient(id: Long): Observable<PatientEntity> {
        val data = HashMap<String, Long>()
        data[PARAM_PATIENT_ID] = id
        return observable(data)
    }

    companion object {
        private const val PARAM_PATIENT_ID = "param:id"
    }
}
