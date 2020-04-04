package com.mapx.kosten.mosimpa.presentation.fragments.patients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.Patient
import kotlinx.android.synthetic.main.layout_patient_item.view.*

class PatientsAdapter constructor(
    private val onNodeSelected: (Patient, View) -> Unit
) : RecyclerView.Adapter<PatientsAdapter.PatientCellViewHolder>() {

    private var patients: List<Patient> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_patient_item,
            parent,
            false)
        return PatientCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patients.size
    }

    override fun onBindViewHolder(holder: PatientCellViewHolder, position: Int) {
        val node = patients[position]
        holder.bind(node, onNodeSelected)
    }

    fun setPatients(patients: List<Patient>) {
        this.patients = patients
        notifyDataSetChanged()
    }

    class PatientCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(patient: Patient, listener: (Patient, View) -> Unit) = with(itemView) {
            tv_item_patient_title.text = patient.name
            // tv_item_patient_id.text = context.resources.getString(R.string.node_adapter_prefix, patient.id)
            tv_item_patient_id.text = patient.id.toString()
            tv_item_patient_status.text = patient.status.toString()
            // tv_item_patient_date.text = patient.dateTime.toString()

            setOnClickListener { listener(patient, itemView) }
        }
    }
}

