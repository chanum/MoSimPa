package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.common.Constants
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_BLOOD_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HEART_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_O2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMPERATURE_ID
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.getSensorStringValue
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.getSensorValueColorByID
import com.mapx.kosten.mosimpa.presentation.entities.SensorView
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

            configureSensorChart(sensor.id)
            updateLimits(sensor.alarmMin, sensor.alarmMax)
            updateChart(sensor.value, sensor.id)

            setOnClickListener { listener(sensor, itemView) }
        }

        private fun configureSensorChart(id: Int) {
            var labelValue = "Value"
            var labelY = "Sensor"
            when(id) {
                SENSOR_O2_ID -> {
                    labelValue = "SpO2"
                    labelY = "%"
                }
                SENSOR_HEART_ID -> {
                    labelValue = "HR"
                    labelY = "Lpm"
                }
                SENSOR_BLOOD_ID -> {
                    labelValue = "BP"
                    labelY = "mmHg"
                }
                SENSOR_TEMPERATURE_ID -> {
                    labelValue = "Temp"
                    labelY = "ºC"
                }
            }
            val set = LineDataSet(entries, labelValue)
            lineChart.data = LineData(set)
            lineChart.description.text = labelY
        }

        // TODO try to update only when internments change
        private fun updateLimits(min: Float, max: Float) {
            val upperLimit = LimitLine(max, "Max")
            upperLimit.lineWidth = 2f
            upperLimit.enableDashedLine(10f, 10f, 0f)
            upperLimit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
            upperLimit.textSize = 10f

            val lowerLimit = LimitLine(min, "Min")
            lowerLimit.lineWidth = 2f
            lowerLimit.enableDashedLine(10f, 10f, 0f)
            lowerLimit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
            lowerLimit.textSize = 10f

            lineChart.axisLeft.removeAllLimitLines();
            lineChart.axisLeft.limitLines.add(upperLimit)
            lineChart.axisLeft.limitLines.add(lowerLimit)
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
            const val VISIBLE_HISTORY = 600
        }
    }
}
