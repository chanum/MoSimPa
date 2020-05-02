package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.presentation.entities.SensorView
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.getSensorStringValue
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.getSensorValueColorByID
import kotlinx.android.synthetic.main.layout_sensor_item.view.*


class SensorsAdapter constructor(
    private val onSensorSelected: (SensorView, View) -> Unit
) : RecyclerView.Adapter<SensorsAdapter.SensorCellViewHolder>() {

    private var sensorViews: List<SensorView> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_sensor_item,
            parent,
            false)
        return SensorCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sensorViews.size
    }

    override fun onBindViewHolder(holder: SensorCellViewHolder, position: Int) {
        val sensor = sensorViews[position]
        holder.bind(sensor, onSensorSelected)
    }

    fun setSensors(sensors: List<SensorView>) {
        this.sensorViews = sensors
        notifyDataSetChanged()
    }

    fun updateSensorAlarms(index: Int, min: Float, max: Float) {
        val item = sensorViews[index]
        item.alarmMin = min
        item.alarmMax = max
        notifyItemChanged(index, item)
    }

    fun updateSensorValue(index: Int, value: Float) {
        val item = sensorViews[index]
        item.value = value
        notifyItemChanged(index, item)
    }

    class SensorCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entries = ArrayList<Entry>()
        private val lineChart = itemView.lineChart_sensor

        init {
            configureSensorChart()
        }

        fun bind(sensor: SensorView, listener: (SensorView, View) -> Unit) = with(itemView) {
            tv_item_sensor_title.text = sensor.name
            tv_item_sensor_value.text = getSensorStringValue(sensor.id, sensor.value)
            tv_item_sensor_value.setTextColor(
                ContextCompat.getColor(
                    context,
                    getSensorValueColorByID(
                        sensor.id,
                        sensor.value,
                        sensor.alarmMin,
                        sensor.alarmMax
                    )
                )
            )

            updateChart(sensor.value, sensor.id)

            setOnClickListener { listener(sensor, itemView) }
        }

        private fun configureSensorChart() {
            val set = LineDataSet(entries, "Value")
            lineChart.data = LineData(set)
            lineChart.description.text = "Sensor"
        }

        private fun updateChart(sensorData: Float, id: Int) {
            val data = lineChart.data

            if(data != null) {
                val set = data.getDataSetByIndex(0)
                val count = set.entryCount.toFloat()
                if (count >= VISIBLE_HISTORY) {
                    set.clear()
                }

                data.addEntry(Entry(count, sensorData), 0)
                data.notifyDataChanged()

                // let the chart know it's data has changed
                lineChart.notifyDataSetChanged()
                lineChart.moveViewToX(count)

                /*
                when(id) {
                    SENSOR_O2_ID -> {
                        lineChart.axisLeft.axisMaximum = SENSOR_O2_Y_MAX
                        lineChart.axisRight.axisMaximum = SENSOR_O2_Y_MAX
                        lineChart.axisLeft.axisMinimum = SENSOR_O2_Y_MIN
                        lineChart.axisRight.axisMinimum = SENSOR_O2_Y_MIN
                    }
                    SENSOR_HEART_ID -> {
                        lineChart.axisLeft.axisMaximum = SENSOR_HEART_Y_MAX
                        lineChart.axisRight.axisMaximum = SENSOR_HEART_Y_MAX
                        lineChart.axisLeft.axisMinimum = SENSOR_HEART_Y_MIN
                        lineChart.axisRight.axisMinimum = SENSOR_HEART_Y_MIN

                    }
                    SENSOR_BLOOD_ID -> {
                        lineChart.axisLeft.axisMaximum = SENSOR_BLOOD_Y_MAX
                        lineChart.axisRight.axisMaximum = SENSOR_BLOOD_Y_MAX
                        lineChart.axisLeft.axisMinimum = SENSOR_BLOOD_Y_MIN
                        lineChart.axisRight.axisMinimum = SENSOR_BLOOD_Y_MIN

                    }
                    SENSOR_TEMPERATURE_ID -> {
                        lineChart.axisLeft.axisMaximum = SENSOR_TEMPERATURE_Y_MAX
                        lineChart.axisRight.axisMaximum = SENSOR_TEMPERATURE_Y_MAX
                        lineChart.axisLeft.axisMinimum = SENSOR_TEMPERATURE_Y_MIN
                        lineChart.axisRight.axisMinimum = SENSOR_TEMPERATURE_Y_MIN

                    }
                }
                 */

                // limit the number of visible entries
                lineChart.setVisibleXRangeMaximum(VISIBLE_X_RANGE)
            }
        }

        companion object {
            const val VISIBLE_X_RANGE = 40F
            const val VISIBLE_HISTORY = 1000
        }
    }
}
