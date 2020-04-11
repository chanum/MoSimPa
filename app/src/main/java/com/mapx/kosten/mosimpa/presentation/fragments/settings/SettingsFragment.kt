package com.mapx.kosten.mosimpa.presentation.fragments.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.presentation.common.App
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.INVALID_PATIENT_ID

import javax.inject.Inject

class SettingsFragment : Fragment() {

    @Inject
    lateinit var factory: SettingsViewModelFactory
    private lateinit var viewModel: SettingsViewModel
    private lateinit var rootLayout: CoordinatorLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyMessage: TextView
    private lateinit var adapter: SettingsAdapter
    private lateinit var addButton: FloatingActionButton
    private lateinit var brokerIpTxt: EditText
    private lateinit var saveIpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createSettingsComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
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
        rootLayout = view.findViewById(R.id.frameLayoutSettings)
        addButton = view.findViewById(R.id.fab_settings_add_patient)
        progressBar = view.findViewById(R.id.pb_settings)
        recyclerView = view.findViewById(R.id.rv_settings_patients)
        emptyMessage = view.findViewById(R.id.tv_settings_empty)
        brokerIpTxt = view.findViewById(R.id.et_settings_server_ip)
        saveIpButton = view.findViewById(R.id.btn_settings_server_save)

        brokerIpTxt.setText(viewModel.getBrokerIp())
        addButton.setOnClickListener{
            goToAddPatient(INVALID_PATIENT_ID)
        }
        saveIpButton.setOnClickListener{
            viewModel.setBrokerIp(brokerIpTxt.text.toString())
        }
        adapter = SettingsAdapter{ node, view ->
            goToDetailView(node, view)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPatients()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseSettingsComponent()
    }

    private fun handleViewState(state: SettingsViewState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        emptyMessage.visibility = if (!state.isLoading && state.isEmpty) View.VISIBLE else View.GONE
        state.patients?.let { adapter.setPatients(it) }
    }

    private fun goToDetailView(patient: PatientEntity, view: View) {
        Log.i(javaClass.simpleName, "goToDetailView(): $patient")
        goToAddPatient(patient.id)
    }
    private fun goToAddPatient(id: Long) {
        val action = SettingsFragmentDirections.actionSettingsDestToSettingsPatientFragment(id)
        findNavController().navigate(action)
    }

}
