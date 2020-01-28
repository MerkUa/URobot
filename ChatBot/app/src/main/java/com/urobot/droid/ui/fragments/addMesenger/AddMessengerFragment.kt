package com.urobot.droid.ui.fragments.addMesenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.urobot.droid.R
import com.urobot.droid.ui.fragments.addMesenger.AddmessengerByTypeFragment.Companion.TELEGTAM
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.FBLayout
import kotlinx.android.synthetic.main.activity_login.loginButtonFacebook
import kotlinx.android.synthetic.main.add_messenger_fragment.*


class AddMessengerFragment : Fragment() {

    private lateinit var callbackManager: CallbackManager

    private lateinit var viewModel: AddMessengerViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_messenger_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddMessengerViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                    }

                    override fun onCancel() {
                    }

                    override fun onError(exception: FacebookException) {
                    }
                })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        if (isLoggedIn) {
            imageFacebookLogin.visibility = View.VISIBLE
            textConnect.visibility = View.INVISIBLE
        }
        FBLayout.setOnClickListener {
            Log.d("performClick", "imFacebook")
            loginButtonFacebook.performClick()
        }
        loginButtonFacebook.setFragment(this)
        loginButtonFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) { // App code
                Log.d("callbackManager", "onSuccess 2" + loginResult?.accessToken)
                val accessToken = AccessToken.getCurrentAccessToken()
                val isLoggedIn = accessToken != null && !accessToken.isExpired
                if (isLoggedIn) {
                    imageFacebookLogin.visibility = View.VISIBLE
                    textConnect.visibility = View.INVISIBLE
                }
            }

            override fun onCancel() {
                Log.d("callbackManager", "onCancel 2")

            }

            override fun onError(exception: FacebookException) {
                Log.d("callbackManager", "onError 2")

            }
        })
        textConnectTelegram.setOnClickListener {
            val action = AddMessengerFragmentDirections.navigationToAddMessenger(TELEGTAM)
            view.findNavController().navigate(action)
        }

    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

}
