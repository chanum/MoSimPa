package com.mapx.kosten.mosimpa.presentation.fragments.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.Patient
import kotlinx.android.synthetic.main.layout_settings_patient_item.view.*

class SettingsAdapter constructor(
    private val onNodeSelected: (Patient, View) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.PatientCellViewHolder>() {

    private var patients: List<Patient> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_settings_patient_item,
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
            tv_settings_patient_name.text = patient.name
            tv_settings_patient_id.text = patient.id.toString()

            setOnClickListener { listener(patient, itemView) }
        }
    }
}

