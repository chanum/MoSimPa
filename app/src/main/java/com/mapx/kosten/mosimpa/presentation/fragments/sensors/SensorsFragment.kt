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
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HEART_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_O2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_BLOOD_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMPERATURE_ID
import com.mapx.kosten.mosimpa.domain.entites.*
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
        viewModel.sensorO2Value.observe(viewLifecycleOwner, Observer {
            it?.let{ handleViewSensorO2State(it) }
        })
        viewModel.sensorBloodValue.observe(viewLifecycleOwner, Observer {
            it?.let{ handleViewSensorBloodState(it) }
        })
        viewModel.sensorHeartValue.observe(viewLifecycleOwner, Observer {
            it?.let{ handleViewSensorHeartState(it) }
        })
        viewModel.sensorTempValue.observe(viewLifecycleOwner, Observer {
            it?.let{ handleViewSensorTempState(it) }
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
        viewModel.subscribePatient(patientId)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseSensorsComponent()
    }

    private fun handleViewSensorO2State(sensor: SensorO2Entity) {
        val index = SENSOR_O2_IDX
        if (index > INVALID_SENSOR && patientId == sensor.deviceId) {
            val item = adapter.sensorEntities[index]
            item.id = SENSOR_O2_ID
            item.value = sensor.spo2
            adapter.notifyItemChanged(index, item)
        }
    }

    private fun handleViewSensorHeartState(sensor: SensorHeartEntity) {
        val index = SENSOR_HEART_IDX
        if (index > INVALID_SENSOR && patientId == sensor.deviceId) {
            val item = adapter.sensorEntities[index]
            item.id = SENSOR_HEART_ID
            item.value = sensor.heartR.toFloat()
            adapter.notifyItemChanged(index, item)
        }
    }

    private fun handleViewSensorBloodState(sensor: SensorBloodEntity) {
        val index = SENSOR_BLOOD_IDX
        if (index > INVALID_SENSOR && patientId == sensor.deviceId) {
            val item = adapter.sensorEntities[index]
            item.id = SENSOR_BLOOD_ID
            item.value = sensor.sys.toFloat()
            adapter.notifyItemChanged(index, item)
        }
    }

    private fun handleViewSensorTempState(sensor: SensorTempEntity) {
        val index = SENSOR_TEMPERATURE_IDX
        if (index > INVALID_SENSOR && patientId == sensor.deviceId) {
            val item = adapter.sensorEntities[index]
            item.id = SENSOR_TEMPERATURE_ID
            item.value = sensor.temp
            adapter.notifyItemChanged(index, item)
        }
    }

    private fun goToDetailView(sensorEntity: SensorEntity, view: View) {
        Log.i(javaClass.simpleName, "goToDetailView(): $sensorEntity")
        // TODO goto sensor detail
    }

    private fun loadSensors() {
        val sensors = mutableListOf<SensorEntity>()
        val o2Sensor = SensorEntity (
            id = SENSOR_O2_ID,
            name = Utils.getSensorNameById(SENSOR_O2_ID),
            value = 0F
        )
        sensors.add(o2Sensor)

        val heartSensor = SensorEntity (
            id = SENSOR_HEART_ID,
            name = Utils.getSensorNameById(SENSOR_HEART_ID),
            value = 0F
        )
        sensors.add(heartSensor)

        val bloodSensor = SensorEntity (
            id = SENSOR_BLOOD_ID,
            name = Utils.getSensorNameById(SENSOR_BLOOD_ID),
            value = 0F
        )
        sensors.add(bloodSensor)

        val temperatureSensor = SensorEntity (
            id = SENSOR_TEMPERATURE_ID,
            name = Utils.getSensorNameById(SENSOR_TEMPERATURE_ID),
            value = 0F
        )
        sensors.add(temperatureSensor)
        adapter.setSensors(sensors)
    }

    companion object {
        const val INVALID_SENSOR = -1
        const val SENSOR_O2_IDX = 0
        const val SENSOR_HEART_IDX = 1
        const val SENSOR_BLOOD_IDX = 2
        const val SENSOR_TEMPERATURE_IDX = 3
    }
}
