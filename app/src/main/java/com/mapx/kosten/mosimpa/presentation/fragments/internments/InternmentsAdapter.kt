package com.mapx.kosten.mosimpa.presentation.fragments.internments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.presentation.entities.InternmentView
import kotlinx.android.synthetic.main.layout_blood_item.view.*
import kotlinx.android.synthetic.main.layout_heart_item.view.*
import kotlinx.android.synthetic.main.layout_internment_item.view.*
import kotlinx.android.synthetic.main.layout_o2_item.view.*
import kotlinx.android.synthetic.main.layout_temperature_item.view.*

class InternmentsAdapter constructor(
    private val onPatientSelected: (InternmentView, View) -> Unit
) : RecyclerView.Adapter<InternmentsAdapter.PatientCellViewHolder>() {

    var internments: List<InternmentView> = listOf()

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

    fun setPatients(patientEntities: List<InternmentView>) {
        this.internments = patientEntities
        notifyDataSetChanged()
    }

    class PatientCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(internmentEntity: InternmentView, listener: (InternmentView, View) -> Unit) = with(itemView) {
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

            tv_internment_item_o2_value.text = "11"
            tv_internment_item_blood_dia_value.text = "22"
            tv_internment_item_blood_sys_value.text = "33"
            tv_internment_item_heart_value.text = "44"
            tv_internment_item_temp_value.text = "55"

            setOnClickListener { listener(internmentEntity, itemView) }
        }
    }
}

