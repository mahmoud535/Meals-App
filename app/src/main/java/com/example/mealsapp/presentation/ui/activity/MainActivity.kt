package com.example.mealsapp.presentation.ui.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mealsapp.R
import com.example.mealsapp.databinding.ActivityMain2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_meals_list, R.id.navigation_add_meals, R.id.navigation_shopping_card
            )
        )
        navView.setupWithNavController(navController)

        val userRole = intent.getStringExtra("USER_ROLE")
        if (userRole == "Guest") {
            navView.menu.findItem(R.id.navigation_add_meals).isVisible = false
        } else if (userRole == "Admin") {
            navView.menu.findItem(R.id.navigation_add_meals).isVisible = true
        }
    }
}