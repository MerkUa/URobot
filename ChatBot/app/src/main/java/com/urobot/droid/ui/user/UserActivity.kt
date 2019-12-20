package com.urobot.droid.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.droid.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
//        val username = findViewById<TextView>(R.id.textViewName)

//        val actionBar = supportActionBar
//        actionBar!!.title = "Настройки"
//
//        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//        userViewModel.allUser.observe(this, Observer { users ->
//            // Update the cached copy of the words in the adapter.
//            users?.let {
//                val user = it[0]
//                textViewName.text = if (user != null) user.name else ""
//                textViewPhone.text = if (user != null) user.cellPhone else ""
//            }
//        })


    }
}
