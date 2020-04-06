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
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            if (it != null) handleViewState(it)
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

        loadSensors()
    }

    override fun onResume() {
        super.onResume()
        // if (patientId > INVALID_PATIENT_ID) viewModel.loadSensors(patientId)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseSensorsComponent()
    }

    private fun handleViewState(state: SensorsViewState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        emptyMessage.visibility = if (!state.isLoading && state.isEmpty) View.VISIBLE else View.GONE
        state.sensorSpo2?.let {
            val newValue = 2.34F
            adapter.sensorEntities[1].value = newValue
            adapter.notifyItemChanged(1)
        }
    }

    private fun goToDetailView(sensorEntity: SensorEntity, view: View) {
        Log.i(javaClass.simpleName, "goToDetailView(): $sensorEntity")
        // TODO goto sensor detail
    }

    private fun loadSensors() {
        val sensors = mutableListOf<SensorEntity>()
        val spo2Sensor = SensorEntity (
            id = Constants.SENSOR_SPO2_ID,
            name = Utils.getSensorNameById(Constants.SENSOR_SPO2_ID),
            value = 0F
        )
        sensors.add(spo2Sensor)
        val hrSensor = SensorEntity (
            id = Constants.SENSOR_HR_ID,
            name = Utils.getSensorNameById(Constants.SENSOR_HR_ID),
            value = 0F
        )
        sensors.add(hrSensor)
        val tempSensor = SensorEntity (
            id = Constants.SENSOR_TEMP_ID,
            name = Utils.getSensorNameById(Constants.SENSOR_TEMP_ID),
            value = 0F
        )
        sensors.add(tempSensor)
        adapter.setSensors(sensors)
    }
}
