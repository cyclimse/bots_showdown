package com.kletto.bot_tutorial

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.button.MaterialButton
import android.widget.TextView as TextView

class HomeActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var code : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.fragment2)
        toolbar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_home_24)
        toolbar?.setNavigationOnClickListener {
            navController.navigate(R.id.action_global_homeFragment)
        }
    }


}