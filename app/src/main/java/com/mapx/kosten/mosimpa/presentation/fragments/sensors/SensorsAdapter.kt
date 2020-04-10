package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
        val entries = ArrayList<Entry>()
        var i = 0F
        val lineChart = itemView.lineChart_sensor

        init {

            //configureSensorChart(lineChart)
            createDataSet(lineChart)
            // entries.add(Entry(i, 0F))
        }

        fun bind(sensor: SensorEntity, listener: (SensorEntity, View) -> Unit) = with(itemView) {
            tv_item_sensor_title.text = sensor.name
            tv_item_sensor_value.text = sensor.value.toString() + getSensorSufixByID(sensor.id)
            updateChart(sensor.value)

            setOnClickListener { listener(sensor, itemView) }
        }

        private fun createDataSet(lineChart: LineChart) {

            val set = LineDataSet(entries, "Value")
            // lineChart.xAxis.labelRotationAngle = 0f
            lineChart.data = LineData(set)
            lineChart.description.text = "Sensor"

            // lineChart.animateX(1800, Easing.EaseInExpo)

            // val leftAxis: YAxis = lineChart.axisLeft
            // leftAxis.mAxisMaximum = 200f

        }

        private fun updateChart(sensorData: Float) {
            val data = lineChart.data

            if(data != null) {
                var set = data.getDataSetByIndex(0)
                if (set == null) {
                    // set = createSet()
                    // data.addDataSet(set)
                }

                val count = set.entryCount.toFloat()
                Log.i(javaClass.simpleName, "entryCount: $count")
                data.addEntry(Entry(count, sensorData), 0)
                data.notifyDataChanged()

                // let the chart know it's data has changed

                // let the chart know it's data has changed
                lineChart.notifyDataSetChanged()

                // limit the number of visible entries

                // limit the number of visible entries
                // lineChart.setVisibleXRangeMaximum(500F)
                // lineChart.setVisibleYRangeMaximum(200F, YAxis.AxisDependency.LEFT);

                lineChart.axisLeft.axisMaximum = 100F
                lineChart.axisRight.axisMaximum = 100F
                lineChart.axisLeft.axisMinimum = 0F
                lineChart.axisRight.axisMinimum = 0F

                lineChart.setVisibleXRangeMaximum(1000F)

                // move to the latest entry
                // mChart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                // lineChart.moveViewToX(data.entryCount.toFloat())
            }
        }
    }
}
