package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.common.Transformer
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.interactors.UseCase
import io.reactivex.Observable

class GetPatientsUseCase (
    transformer: Transformer<List<PatientEntity>>,
    private val patientsRepository: PatientsRepository
) : UseCase<List<PatientEntity>>(transformer){

    override fun createObservable(data: Map<String, Any>?): Observable<List<PatientEntity>> {
        return patientsRepository.getAllPatients()
    }
}
