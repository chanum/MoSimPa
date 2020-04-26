package com.mapx.kosten.mosimpa.presentation.fragments.internments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity
import kotlinx.android.synthetic.main.layout_internment_item.view.*

class InternmentsAdapter constructor(
    private val onPatientSelected: (InternmentEntity, View) -> Unit
) : RecyclerView.Adapter<InternmentsAdapter.PatientCellViewHolder>() {

    private var internments: List<InternmentEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_internment_item,
            parent,
            false)
        return PatientCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return internments.size
    }

    override fun onBindViewHolder(holder: PatientCellViewHolder, position: Int) {
        val node = internments[position]
        holder.bind(node, onPatientSelected)
    }

    fun setPatients(patientEntities: List<InternmentEntity>) {
        this.internments = patientEntities
        notifyDataSetChanged()
    }

    class PatientCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(internmentEntity: InternmentEntity, listener: (InternmentEntity, View) -> Unit) = with(itemView) {
            tv_item_internment_patient_name.text = context.resources.getString(
                R.string.internment_item_patient_name,
                internmentEntity.patient.surname,
                internmentEntity.patient.name
            )

            tv_item_internment_id.text = context.resources.getString(
                R.string.internments_item_id,
                internmentEntity.id
            )

            tv_item_internment_patient_age.text = context.resources.getString(
                R.string.internments_item_patient_age,
                internmentEntity.patient.age
            )

            tv_item_internment_patient_gender.text = context.resources.getString(
                R.string.internments_item_patient_gender,
                internmentEntity.patient.gender
            )

            tv_item_internment_location.text = context.resources.getString(
                R.string.internments_item_location,
                internmentEntity.location.desc
            )

            tv_item_internment_location_type.text = context.resources.getString(
                R.string.internments_item_location_type,
                internmentEntity.location.type
            )

            tv_item_internment_device_id.text = context.resources.getString(
                R.string.internments_item_device_id,
                internmentEntity.deviceId
            )

            setOnClickListener { listener(internmentEntity, itemView) }
        }
    }
}

