package com.urobot.droid.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.urobot.droid.R
import com.urobot.droid.db.User


class MainChatActivity : AppCompatActivity() {


    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.popBackStack(R.id.nav_host_fragment, true)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_ubot, R.id.navigation_chats, R.id.navigation_contacts, R.id.navigation_settings,
                        R.id.navigation_settings_support, R.id.navigation_settings_support_details,
                        R.id.navigation_settings_promo, R.id.navigation_messages
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    fun getUser(): User? = mainViewModel.currentUser.value


    fun shouldDisplayHomeUp() { //Enable Up button only  if there are entries in the back stack
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(canGoBack)
    }
}
