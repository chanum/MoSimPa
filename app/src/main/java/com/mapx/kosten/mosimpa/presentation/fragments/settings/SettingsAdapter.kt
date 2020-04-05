package com.mapx.kosten.mosimpa.presentation.fragments.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import kotlinx.android.synthetic.main.layout_settings_patient_item.view.*

class SettingsAdapter constructor(
    private val onSettingPatientSelected: (PatientEntity, View) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.PatientCellViewHolder>() {

    private var patientEntities: List<PatientEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_settings_patient_item,
            parent,
            false)
        return PatientCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patientEntities.size
    }

    override fun onBindViewHolder(holder: PatientCellViewHolder, position: Int) {
        val node = patientEntities[position]
        holder.bind(node, onSettingPatientSelected)
    }

    fun setPatients(patientEntities: List<PatientEntity>) {
        this.patientEntities = patientEntities
        notifyDataSetChanged()
    }

    class PatientCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(patientEntity: PatientEntity, listener: (PatientEntity, View) -> Unit) = with(itemView) {
            tv_settings_patient_name.text = patientEntity.name
            tv_settings_patient_id.text = patientEntity.id.toString()

            setOnClickListener { listener(patientEntity, itemView) }
        }
    }
}

