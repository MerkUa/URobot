package com.urobot.android.ui.main

import android.Manifest
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.urobot.android.Helper.Utils.ensureCalendarExists
import com.urobot.android.R
import com.urobot.android.db.User
import com.urobot.android.ui.dialogs.CreateEventDialogFragment
import com.urobot.android.ui.dialogs.SubscribeDialogFragment


class MainChatActivity : AppCompatActivity() {


    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val navController = findNavController(R.id.nav_host_fragment)
        navController.popBackStack(R.id.nav_host_fragment, true)
        val PREF_NAME = "SHOW_SUBSCRIBE"
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, 0)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_ubot,
                R.id.navigation_chats,
                R.id.navigation_contacts,
                R.id.navigation_settings,
                R.id.navigation_settings_support,
                R.id.navigation_settings_support_details,
                R.id.navigation_settings_promo,
                R.id.navigation_messages,
                R.id.navigation_services_fragment,
                R.id.navigation_create_calendar,
                R.id.navigation_create_payment,
                R.id.navigation_settings_add_messenger,
                R.id.navigation_industry_fragment,
                R.id.navigation_create_bot,
                R.id.navigation_settings_tariffs,
                R.id.navigation_settings_web
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        getIntent().getExtras()


        val intent = intent
        val id = intent.getStringExtra("message")
        if (!id.isNullOrEmpty()) {
            val bundle = Bundle()
            bundle.putInt("IdRecipient", id.toInt())
            navController.navigate(R.id.navigation_messages, bundle)
        }
        mainViewModel.currentUser.observe(this, Observer { user ->
            user?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (PermissionChecker.checkSelfPermission(
                            this,
                            Manifest.permission.READ_CALENDAR
                        ) ==
                        PermissionChecker.PERMISSION_GRANTED
                    ) {
                        mainViewModel.getCalendarEvent(this, user.token!!)
                    }
                } else {
                    mainViewModel.getCalendarEvent(this, user.token!!)
                }
                Log.d("merk", "pref " + sharedPref.getBoolean(PREF_NAME, true))
                if (sharedPref.getBoolean(PREF_NAME, true)) {
                    Log.d("merk", "alertDialogSubscribe")
                    alertDialogSubscribe(user.id)
                }

            }
        })
        checkPermission()

    }

    fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    fun getUser(): User? = mainViewModel.currentUser.value


    fun shouldDisplayHomeUp() { //Enable Up button only  if there are entries in the back stack
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(canGoBack)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CreateEventDialogFragment.PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PermissionChecker.PERMISSION_GRANTED
                ) {
                    ensureCalendarExists(this)
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CALENDAR
                ) ==
                PermissionChecker.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR
                )
                //show popup to request runtime permission
                requestPermissions(permissions, CreateEventDialogFragment.PERMISSION_CODE);
            } else {
                ensureCalendarExists(this)

            }
        } else {
            ensureCalendarExists(this)
        }
    }

    fun alertDialogSubscribe(id: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.subscribe_message))

        builder.setPositiveButton("OK") { dialogInterface, i ->
            val dialog = SubscribeDialogFragment()
            dialog.setUserId(id)
            dialog.show(this.supportFragmentManager, "TAG")
        }
        builder.show()
    }
}
