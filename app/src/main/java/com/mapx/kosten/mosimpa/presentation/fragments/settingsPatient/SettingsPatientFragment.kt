package com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.EMPTY_STRING
import com.mapx.kosten.mosimpa.presentation.common.App
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.INVALID_PATIENT_ID
import javax.inject.Inject

class SettingsPatientFragment : Fragment() {

    @Inject
    lateinit var factory: SettingsPatientViewModelFactory
    private lateinit var viewModel: SettingsPatientViewModel
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var nameText: EditText
    private lateinit var nodeIdText: EditText
    private lateinit var saveBtn: Button
    private lateinit var cancelBtn: Button
    private var patientId: Long = INVALID_PATIENT_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createSettingsPatientComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(SettingsPatientViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_patient, container, false)
        val safeArgs: SettingsPatientFragmentArgs by navArgs()
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
        rootLayout = view.findViewById(R.id.frameLayoutSettingsPatient)
        nameText = view.findViewById(R.id.et_settings_patient_name)
        nodeIdText = view.findViewById(R.id.et_settings_patient_id)
        saveBtn = view.findViewById(R.id.btn_settings_patient_save)
        cancelBtn = view.findViewById(R.id.btn_settings_patient_cancel)

        if (patientId > 0) {
            cancelBtn.text = resources.getString(R.string.settings_patient_delete_btn)
            viewModel.getPatient(patientId)
        }

        // TODO removed, only for debug
        // nodeIdText.setText("b827eb8b862d")

        cancelBtn.setOnClickListener { assignButtonFunction(patientId) }
        saveBtn.setOnClickListener { doSave() }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseSettingsPatientComponent()
    }

    private fun handleViewState(state: SettingsPatientViewState) {
        state.patient?.let { updatePatientEntry(it) }
        if(state.close) { close() }
    }

    private fun updatePatientEntry(patient: PatientEntity) {
        nameText.setText(patient.name)
        nodeIdText.setText(patient.deviceId)
    }

    private fun doSave() {
        val name = nameText.text.toString()
        val id = nodeIdText.text.toString()

        if (isValidName(name) && isValidId(id)) {
            viewModel.savePatient(id, name)
        }

    }

    private fun isValidName(name: String): Boolean {
        val isValid = name.isNotEmpty()
        if (isValid.not()) {
            showSnack(INVALID_NAME)
        }
        return isValid
    }

    private fun isValidId(id: String): Boolean {
        val isValid = id.isNotEmpty()
        if (isValid.not()) {
            showSnack(INVALID_ID)
        }
        return isValid
    }

    private fun assignButtonFunction (id: Long) {
        if(id > 0) {
            viewModel.deletePatient(id)
        } else {
            close()
        }
    }

    private fun close() {
        findNavController().popBackStack()
    }

    private fun showSnack(error: Int) {
        var msg: String = EMPTY_STRING
        when(error) {
            INVALID_NAME -> msg = resources.getString(R.string.settings_patient_error_name)
            INVALID_ID -> msg = resources.getString(R.string.settings_patient_error_id)
            SAVE_ERROR -> msg = resources.getString(R.string.settings_patient_error_save)
            else -> msg = resources.getString(R.string.settings_patient_error)
        }
        val snack = Snackbar.make(
            rootLayout,
            msg,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    companion object {
        private const val INVALID_NAME = 0
        private const val INVALID_ID = 1
        const val SAVE_OK = 0
        const val SAVE_ERROR = -1
    }
}
