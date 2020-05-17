package com.mapx.kosten.mosimpa.presentation.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.presentation.common.App
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var loginBtn: Button
    private lateinit var serverNameTxt: EditText
    private lateinit var serverIpTxt: EditText
    private lateinit var optionsTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (application as App).createLoginComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        serverNameTxt = findViewById(R.id.et_login_name)
        serverIpTxt = findViewById(R.id.et_login_ip)
        loginBtn = findViewById(R.id.button_login)
        optionsTxt = findViewById(R.id.tv_login_options)

    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releaseLoginComponent()
    }
}
