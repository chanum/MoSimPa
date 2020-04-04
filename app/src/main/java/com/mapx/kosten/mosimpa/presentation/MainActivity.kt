package com.mapx.kosten.mosimpa.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.data.client.MqttClient

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    // private val topics = arrayOf("#", "reads/b827eb8b862")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        // navigation host
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_main) as NavHostFragment? ?: return
        val navController = host.navController

        // bottom navigation menu
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBar(navController, appBarConfiguration)
        setupBottomNavMenu(navController)

        // TODO MQQT client move to data di
        // var mqttClient = MqttClient(this)
        //mqttClient.connect(topics)
    }

    private fun setupBottomNavMenu(
        navController: NavController
    ) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_main)
        bottomNav?.setupWithNavController(navController)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_main).navigateUp()
    }

}
