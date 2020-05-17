package com.mapx.kosten.mosimpa.presentation.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.presentation.activities.main.MainActivity
import com.mapx.kosten.mosimpa.presentation.common.ActivityUtils
import com.mapx.kosten.mosimpa.presentation.common.App
import com.mapx.kosten.mosimpa.presentation.viewmodels.LoginViewModel
import com.mapx.kosten.mosimpa.presentation.viewmodels.LoginViewModelFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var loginBtn: Button
    private lateinit var serverNameTxt: EditText
    private lateinit var serverIpTxt: EditText
    private lateinit var optionsTxt: TextView
    private lateinit var serversList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (application as App).createLoginComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        serverNameTxt = findViewById(R.id.et_login_name)
        serverIpTxt = findViewById(R.id.et_login_ip)
        loginBtn = findViewById(R.id.button_login)
        optionsTxt = findViewById(R.id.tv_login_options)
        serversList = findViewById(R.id.rv_login_servers)

        loginBtn.setOnClickListener{
           //  viewModel.setBrokerIp(serverIpTxt.text.toString())
            goToMain()
        }

        initCurrentServer()
    }

    private fun initCurrentServer() {
        val currentServer = viewModel.getCurrentServer()
        serverNameTxt.setText(currentServer.name)
        serverIpTxt.setText(currentServer.ip)
    }

    private fun goToMain() {
        val intent = MainActivity.getStartIntent(this)
        ActivityUtils.startActivityWithCrossFade(this, intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releaseLoginComponent()
    }
}
