package com.mapx.kosten.mosimpa.presentation.fragments.patients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.presentation.common.App
import kotlinx.android.synthetic.main.fragment_patients.*

import javax.inject.Inject

class PatientsFragment : Fragment() {

    @Inject
    lateinit var factory: PatientsViewModelFactory
    private lateinit var viewModel: PatientsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyMessage: TextView
    private lateinit var adapter: PatientsAdapter
    private lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createPatientsComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(PatientsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patients, container, false)
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
        addButton = fab_add_patient
        addButton.setOnClickListener{
            goToAddPatient()
        }
        progressBar = pb_patients
        adapter = PatientsAdapter{ node, view ->
            goToDetailView(node, view)
        }
        recyclerView = rv_patients
        emptyMessage = tv_patients_empty
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPatients()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releasePatientsComponent()
    }

    private fun handleViewState(state: PatientsViewState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        emptyMessage.visibility = if (!state.isLoading && state.isEmpty) View.VISIBLE else View.GONE
        state.patients?.let { adapter.setPatients(it) }
    }

    private fun goToDetailView(patientEntity: PatientEntity, view: View) {
        Log.i(javaClass.simpleName, "goToDetailView(): $patientEntity")
        // TODO goto sensor detail
    }

    private fun goToAddPatient() {
       // TODO
    }

}
