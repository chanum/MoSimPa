package com.mapx.kosten.mosimpa.presentation.fragments.internments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.SensorBloodEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorHeartEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorO2Entity
import com.mapx.kosten.mosimpa.domain.entites.SensorTempEntity
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

    // TODO replace with generic
    fun setO2Value(sensor: SensorO2Entity) {
        val idx = internments.indexOfFirst { it.id == sensor.patientId }
        if (idx > INVALID_INDEX) {
            internments[idx].sensorO2.spo2 = sensor.spo2
            notifyItemChanged(idx, internments[idx])
        }
    }

    fun setHeartValue(sensor: SensorHeartEntity) {
        val idx = internments.indexOfFirst { it.id == sensor.patientId }
        if (idx > INVALID_INDEX) {
            internments[idx].sensorHeart.heartR = sensor.heartR
            notifyItemChanged(idx, internments[idx])
        }
    }

    fun setBloodValue(sensor: SensorBloodEntity) {
        val idx = internments.indexOfFirst { it.id == sensor.patientId }
        if (idx > INVALID_INDEX) {
            internments[idx].sensorBlood.sys = sensor.sys
            internments[idx].sensorBlood.dia = sensor.dia
            notifyItemChanged(idx, internments[idx])
        }
    }

    fun setTempValue(sensor: SensorTempEntity) {
        val idx = internments.indexOfFirst { it.id == sensor.patientId }
        if (idx > INVALID_INDEX) {
            internments[idx].sensorTemp.temp = sensor.temp
            notifyItemChanged(idx, internments[idx])
        }
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

            tv_internment_item_o2_value.text = internmentEntity.sensorO2.spo2.toString()
            tv_internment_item_blood_dia_value.text = internmentEntity.sensorBlood.dia.toString()
            tv_internment_item_blood_sys_value.text = internmentEntity.sensorBlood.sys.toString()
            tv_internment_item_heart_value.text = internmentEntity.sensorHeart.heartR.toString()
            tv_internment_item_temp_value.text = internmentEntity.sensorTemp.temp.toString()

            setOnClickListener { listener(internmentEntity, itemView) }
        }
    }

    companion object {
        private const val INVALID_INDEX = -1
    }
}

