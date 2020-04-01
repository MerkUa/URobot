package com.urobot.android.ui.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.urobot.android.R

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
