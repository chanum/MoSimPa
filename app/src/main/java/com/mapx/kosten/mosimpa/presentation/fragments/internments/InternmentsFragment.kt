package com.mapx.kosten.mosimpa.presentation.fragments.internments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity
import com.mapx.kosten.mosimpa.presentation.common.App
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.INVALID_PATIENT_ID
import javax.inject.Inject

class InternmentsFragment : Fragment() {

    @Inject
    lateinit var factory: InternmentsViewModelFactory
    private lateinit var viewModel: InternmentsViewModel
    private lateinit var rootLayout: CoordinatorLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyMessage: TextView
    private lateinit var adapter: InternmentsAdapter
    private lateinit var refreshBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createInternmentsComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(InternmentsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_internments, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.internments.observe(viewLifecycleOwner, Observer {
            if (it != null) handleInternments(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootLayout = view.findViewById(R.id.frameLayoutInternments)
        progressBar = rootLayout.findViewById(R.id.pb_internments)
        emptyMessage = rootLayout.findViewById(R.id.tv_internments_empty)
        recyclerView = rootLayout.findViewById(R.id.rv_internments)
        refreshBtn = rootLayout.findViewById(R.id.fab_internments_refresh)

        adapter = InternmentsAdapter{ node, view ->
            goToDetailView(node, view)
        }

        refreshBtn.setOnClickListener{
            updateInternments()
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.connectAndSubscribeToAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseInternmentsComponent()
    }

    private fun handleInternments(internments: List<InternmentEntity>) {
        progressBar.visibility = View.GONE
        emptyMessage.visibility = View.GONE
        if (internments.isEmpty()) {
            emptyMessage.visibility = View.VISIBLE
        }
        adapter.setPatients(internments)
    }

    private fun goToDetailView(internmentEntity: InternmentEntity, view: View) {
        Log.i(javaClass.simpleName, "goToDetailView(): $internmentEntity")
        if (internmentEntity.id > INVALID_PATIENT_ID) goToDetails(internmentEntity.id)
    }

    private fun goToDetails(id: Long) {
       val action = InternmentsFragmentDirections.actionPatientsFragmentToSensorsFragment(id)
       findNavController().navigate(action)
    }

    private fun updateInternments() {
        //TODO
    }

}
