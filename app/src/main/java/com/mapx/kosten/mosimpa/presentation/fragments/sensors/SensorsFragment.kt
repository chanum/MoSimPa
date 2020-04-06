package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.common.Constants
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HR_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_SPO2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMP_ID
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.presentation.common.App
import com.mapx.kosten.mosimpa.presentation.common.Utils
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.INVALID_PATIENT_ID
import javax.inject.Inject


class SensorsFragment : Fragment() {
    @Inject
    lateinit var factory: SensorsViewModelFactory
    private lateinit var viewModel: SensorsViewModel
    private lateinit var rootLayout: CoordinatorLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyMessage: TextView
    private lateinit var adapter: SensorsAdapter
    private var patientId: Long = INVALID_PATIENT_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createSensorsComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(SensorsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sensors, container, false)
        val safeArgs: SensorsFragmentArgs by navArgs()
        patientId = safeArgs.patientId
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.spo2State.observe(viewLifecycleOwner, Observer {
            if (it != null) handleViewSensorState(it)
        })
        viewModel.hrState.observe(viewLifecycleOwner, Observer {
            if (it != null) handleViewSensorState(it)
        })
        viewModel.tempState.observe(viewLifecycleOwner, Observer {
            if (it != null) handleViewSensorState(it)
        })
        viewModel.errorState.observe(viewLifecycleOwner, Observer { throwable ->
            throwable?.let {
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootLayout = view.findViewById(R.id.frameLayoutSensors)
        progressBar = rootLayout.findViewById(R.id.pb_sensors)
        recyclerView = rootLayout.findViewById(R.id.rv_sensors)
        emptyMessage = rootLayout.findViewById(R.id.tv_sensors_empty)

        adapter = SensorsAdapter{ sensor, view ->
            goToDetailView(sensor, view)
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        progressBar.visibility = View.VISIBLE
        loadSensors()
        progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        // if (patientId > INVALID_PATIENT_ID) viewModel.loadSensors(patientId)
        viewModel.getSensorData()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseSensorsComponent()
    }

    private fun handleViewSensorState(sensor: SensorEntity) {
        val index = getSensorIndex(sensor.id)
        if (index > INVALID_SENSOR) {
            adapter.sensorEntities[index].value = sensor.value
            adapter.notifyItemChanged(index)
        }
    }

    private fun goToDetailView(sensorEntity: SensorEntity, view: View) {
        Log.i(javaClass.simpleName, "goToDetailView(): $sensorEntity")
        // TODO goto sensor detail
    }

    private fun loadSensors() {
        val sensors = mutableListOf<SensorEntity>()
        val spo2Sensor = SensorEntity (
            id = SENSOR_SPO2_ID,
            name = Utils.getSensorNameById(SENSOR_SPO2_ID),
            value = 0F
        )
        sensors.add(spo2Sensor)
        val hrSensor = SensorEntity (
            id = SENSOR_HR_ID,
            name = Utils.getSensorNameById(SENSOR_HR_ID),
            value = 0F
        )
        sensors.add(hrSensor)
        val tempSensor = SensorEntity (
            id = SENSOR_TEMP_ID,
            name = Utils.getSensorNameById(SENSOR_TEMP_ID),
            value = 0F
        )
        sensors.add(tempSensor)
        adapter.setSensors(sensors)
    }

    private fun getSensorIndex(id: Int): Int {
        return when(id) {
            SENSOR_SPO2_ID -> SENSOR_SPO2_IDX
            SENSOR_HR_ID -> SENSOR_HR_IDX
            SENSOR_TEMP_ID -> SENSOR_TEMP_IDX
            else -> INVALID_SENSOR
        }
    }

    companion object {
        const val INVALID_SENSOR = -1
        const val SENSOR_SPO2_IDX = 0
        const val SENSOR_HR_IDX = 1
        const val SENSOR_TEMP_IDX = 2
    }
}
