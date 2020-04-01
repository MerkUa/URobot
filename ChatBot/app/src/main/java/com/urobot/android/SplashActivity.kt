package com.urobot.android

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.urobot.android.ui.login.LoginActivity
import com.urobot.android.ui.main.MainChatActivity

class SplashActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "isTutorialShowed"
    private val PREF_IS_LOGED = "isLoged"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val sharedPrefL: SharedPreferences = getSharedPreferences(PREF_IS_LOGED, PRIVATE_MODE)

        var intent = Intent(this, LoginActivity::class.java)
        if (sharedPref.getBoolean(PREF_NAME, true)) {
            intent = Intent(this, TutorialActivity::class.java)
            val editor = sharedPref.edit()
            editor.putBoolean(PREF_NAME, false)
            editor.apply()
        }
        if (sharedPrefL.getBoolean(PREF_IS_LOGED, false)) {
            intent = Intent(this, MainChatActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
