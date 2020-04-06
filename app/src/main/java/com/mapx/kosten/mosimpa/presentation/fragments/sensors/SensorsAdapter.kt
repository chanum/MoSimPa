package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.getSensorSufixByID
import kotlinx.android.synthetic.main.layout_sensor_item.view.*

class SensorsAdapter constructor(
    private val onSensorSelected: (SensorEntity, View) -> Unit
) : RecyclerView.Adapter<SensorsAdapter.SensorCellViewHolder>() {

    var sensorEntities: List<SensorEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_sensor_item,
            parent,
            false)
        return SensorCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sensorEntities.size
    }

    override fun onBindViewHolder(holder: SensorCellViewHolder, position: Int) {
        val node = sensorEntities[position]
        holder.bind(node, onSensorSelected)
    }

    fun setSensors(sensors: List<SensorEntity>) {
        this.sensorEntities = sensors
        notifyDataSetChanged()
    }

    class SensorCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sensor: SensorEntity, listener: (SensorEntity, View) -> Unit) = with(itemView) {
            tv_item_sensor_title.text = sensor.name
            tv_item_sensor_value.text = sensor.value.toString() + getSensorSufixByID(sensor.id)

            setOnClickListener { listener(sensor, itemView) }
        }
    }
}
