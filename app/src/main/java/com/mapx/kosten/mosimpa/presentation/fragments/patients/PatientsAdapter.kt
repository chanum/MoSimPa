package com.mapx.kosten.mosimpa.presentation.fragments.patients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.PatientEntity
import kotlinx.android.synthetic.main.layout_patient_item.view.*

class PatientsAdapter constructor(
    private val onNodeSelected: (PatientEntity, View) -> Unit
) : RecyclerView.Adapter<PatientsAdapter.PatientCellViewHolder>() {

    private var patientEntities: List<PatientEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_patient_item,
            parent,
            false)
        return PatientCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patientEntities.size
    }

    override fun onBindViewHolder(holder: PatientCellViewHolder, position: Int) {
        val node = patientEntities[position]
        holder.bind(node, onNodeSelected)
    }

    fun setPatients(patientEntities: List<PatientEntity>) {
        this.patientEntities = patientEntities
        notifyDataSetChanged()
    }

    class PatientCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(patientEntity: PatientEntity, listener: (PatientEntity, View) -> Unit) = with(itemView) {
            tv_item_patient_title.text = patientEntity.name
            // tv_item_patient_id.text = context.resources.getString(R.string.node_adapter_prefix, patient.id)
            tv_item_patient_id.text = patientEntity.id.toString()
            tv_item_patient_status.text = patientEntity.status.toString()
            // tv_item_patient_date.text = patient.dateTime.toString()

            setOnClickListener { listener(patientEntity, itemView) }
        }
    }
}

