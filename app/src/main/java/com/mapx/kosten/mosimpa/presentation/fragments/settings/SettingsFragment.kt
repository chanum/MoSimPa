package com.mapx.kosten.mosimpa.presentation.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.presentation.activities.main.MainActivity
import com.mapx.kosten.mosimpa.presentation.common.App
import com.mapx.kosten.mosimpa.presentation.viewmodels.SettingsViewModel
import com.mapx.kosten.mosimpa.presentation.viewmodels.SettingsViewModelFactory
import kotlinx.android.synthetic.main.layout_server_item.*
import javax.inject.Inject

class SettingsFragment : Fragment() {

    @Inject
    lateinit var factory: SettingsViewModelFactory
    private lateinit var viewModel: SettingsViewModel
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var brokerNameTxt: TextView
    private lateinit var brokerIpTxt: TextView
    private lateinit var logoutButton: Button

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
        viewModel.errorState.observe(viewLifecycleOwner, Observer { throwable ->
            throwable?.let {
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootLayout = view.findViewById(R.id.frameLayoutSettings)
        brokerNameTxt = view.findViewById(R.id.tv_server_item_name)
        brokerIpTxt = view.findViewById(R.id.tv_server_item_ip)
        logoutButton = view.findViewById(R.id.btn_settings_server_logout)

        logoutButton.setOnClickListener {
            gotToLoginActivity(view)
        }

        initCurrentServer()

//        saveIpButton.setOnClickListener{
//            viewModel.saveServer(
//                ServerEntity(
//                    -1,
//                    brokerNameTxt.text.toString().trim(),
//                    brokerIpTxt.text.toString().trim()
//                )
//            )
//            showSnack()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseSettingsComponent()
    }

    private fun initCurrentServer() {
        val currentServer = viewModel.getCurrentServerInfo()
        brokerNameTxt.setText(currentServer.name)
        brokerIpTxt.setText(currentServer.ip)
        iv_server_item_delete.visibility = View.GONE
    }

    private fun gotToLoginActivity(view: View) {
        view.findNavController().navigate(R.id.loginActivity)
        (activity as MainActivity).finish()
    }

    private fun showSnack() {
        Toast.makeText(
            this.context,
            resources.getString(R.string.settings_ip_saved),
            Toast.LENGTH_LONG
        ).show()
    }

}
