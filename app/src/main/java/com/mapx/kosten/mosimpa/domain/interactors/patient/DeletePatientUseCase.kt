package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.common.Transformer
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.interactors.UseCase
import io.reactivex.Observable
import java.lang.IllegalArgumentException

class DeletePatientUseCase (
    transformer: Transformer<Boolean>,
    private val patientsRepository: PatientsRepository
) : UseCase<Boolean>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val nodeId = data?.get(PARAM_PATIENT_ID)

        nodeId?.let {
            return Observable.fromCallable {
                val id = it as Long
                patientsRepository.deletePatientById(id)
                return@fromCallable true
            }
        }?: return Observable.error{ IllegalArgumentException("PatientEntity must be provided.") }

    }

    fun delete(id: Long): Observable<Boolean> {
        val data = HashMap<String, Long>()
        data[PARAM_PATIENT_ID] = id
        return observable(data)
    }

    companion object {
        private const val PARAM_PATIENT_ID = "param:id"
    }
}
