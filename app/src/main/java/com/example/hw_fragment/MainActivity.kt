package com.example.hw_fragment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.hw_fragment.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_fragment)

        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.mainFragment, R.id.infoFragment), navigation_drawer)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigation_view.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_fragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
