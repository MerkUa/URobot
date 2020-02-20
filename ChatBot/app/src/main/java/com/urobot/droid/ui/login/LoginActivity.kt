package com.urobot.droid.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.twitter.sdk.android.core.*
import com.urobot.droid.R
import com.urobot.droid.data.NetModel.Request.RequestSignInSocial.Companion.FACEBOOK
import com.urobot.droid.data.NetModel.Request.RequestSignInSocial.Companion.GOOGLE
import com.urobot.droid.data.NetModel.Request.RequestSignInSocial.Companion.TWITTER
import com.urobot.droid.ui.main.MainChatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    companion object {
        const val SIGN_IN_REQ_CODE: Int = 54321
        const val USER_KEY: String = "firebase_user"
    }

    private lateinit var callbackManager: CallbackManager
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

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

        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig("XnSbA5ptLFQPHD1x5tROiCRHo", "j8cHkhx2deVhj7W3LulrwmOiLCl19FDBVDMpWx0iLeuJOPxmmJ"))
                .debug(true)
                .build()
        Twitter.initialize(config)

        val username = findViewById<AppCompatEditText>(R.id.UserNameEditText)
        val password = findViewById<AppCompatEditText>(R.id.PasswordEditText)
        val emailSignin = findViewById<AppCompatEditText>(R.id.EmailEditText)
        val passSignin = findViewById<AppCompatEditText>(R.id.PassEditText)
        val nameSignin = findViewById<AppCompatEditText>(R.id.NameEditText)
        val lastnameSignin = findViewById<AppCompatEditText>(R.id.LastNameEditText)


        val loginButton = findViewById<Button>(R.id.login)
        val signUnButton = findViewById<Button>(R.id.SignUpButton)
        val loading = findViewById<ProgressBar>(R.id.loading)

        buttonForgotPass.setOnClickListener {
            layoutLogin.visibility = GONE
            layoutForgotPass.visibility = VISIBLE
        }

        buttonCancel.setOnClickListener {
            layoutLogin.visibility = VISIBLE
            layoutForgotPass.visibility = GONE
        }


        imGoodle.setOnClickListener {
            //            loginButtonGoogle.performClick()
            firebaseLogin()
        }


        imFacebook.setOnClickListener {
            loginButtonFacebook.performClick()
        }

        forgotPassButton.setOnClickListener {
            loginViewModel.forgotPass(forgotEmailEditText.text.toString(), applicationContext)
        }

        loginButtonFacebook.registerCallback(callbackManager, object :
                FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) { // App code
                val accessToken = AccessToken.getCurrentAccessToken()
                val isLoggedIn = accessToken != null && !accessToken.isExpired
                if (isLoggedIn) {
                    loginViewModel.signInSocial(
                        FACEBOOK,
                        accessToken.token,
                        null,
                        applicationContext
                    )
                }
            }

            override fun onCancel() {

            }

            override fun onError(exception: FacebookException) {

            }
        })

        imTwitter.setOnClickListener {
            loginButtonTwitter.performClick()
        }

        loginButtonTwitter.callback = object : Callback<TwitterSession>() {
            override fun success(result: com.twitter.sdk.android.core.Result<TwitterSession>?) {
                var session = result!!.data
                loginViewModel.signInSocial(
                    TWITTER,
                    session.authToken.token,
                    session.authToken.secret,
                    applicationContext
                )
            }

            override fun failure(exception: TwitterException) {
            }
        }

        buttonCreateAccount.setOnClickListener {
            layoutLogin.visibility = GONE
            layoutSignUp.visibility = VISIBLE
        }

        buttonLogin.setOnClickListener {
            layoutLogin.visibility = VISIBLE
            layoutSignUp.visibility = GONE
        }

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            loginButton.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.sigUpFormState.observe(this@LoginActivity, Observer {
            val signinState = it ?: return@Observer

            // disable login button unless both username / password is valid
            signUnButton.isEnabled = signinState.isDataValid

            if (signinState.userEmailError != null) {
                emailSignin.error = getString(signinState.userEmailError)
            }
            if (signinState.passwordError != null) {
                passSignin.error = getString(signinState.passwordError)
            }
            if (signinState.nameError != null) {
                nameSignin.error = getString(signinState.nameError)
            }
            if (signinState.lastNameError != null) {
                lastnameSignin.error = getString(signinState.lastNameError)
            }

        })

        loginViewModel.forgotPassState.observe(this@LoginActivity, Observer {
            val forgotPassState = it ?: return@Observer

            // disable login button unless both username / password is valid
            forgotPassButton.isEnabled = forgotPassState.isDataValid

            if (forgotPassState.userEmailError != null) {
                forgotEmailEditText.error = getString(forgotPassState.userEmailError)
            }
            if (forgotPassState.successForgotPass != null) {
                buttonCancel.performClick()
                Toast.makeText(applicationContext, forgotPassState.successForgotPass, Toast.LENGTH_SHORT).show()
            }
            if (forgotPassState.errorForgotPass != null) {
                Toast.makeText(applicationContext, forgotPassState.errorForgotPass, Toast.LENGTH_SHORT).show()
            }
        })


        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.successSignUp != null) {
                buttonLogin.performClick()
                Toast.makeText(applicationContext, loginResult.successSignUp, Toast.LENGTH_SHORT).show()
            }
            if (loginResult.success != null) {
                val intent = Intent(this, MainChatActivity::class.java)
                startActivity(intent)
                finish()
            }
            auth = FirebaseAuth.getInstance()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        forgotEmailEditText.afterTextChanged {
            loginViewModel.forgotPassDataChanged(
                    forgotEmailEditText.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString(),
                            context
                        )
                }
                false
            }

            loginButton.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString(), context)
            }
        }

        emailSignin.afterTextChanged {
            loginViewModel.sigInDataChanged(
                    emailSignin.text.toString(),
                    passSignin.text.toString(),
                    nameSignin.text.toString(),
                    lastnameSignin.text.toString()
            )
        }

        passSignin.afterTextChanged {
            loginViewModel.sigInDataChanged(
                    emailSignin.text.toString(),
                    passSignin.text.toString(),
                    nameSignin.text.toString(),
                    lastnameSignin.text.toString()
            )
        }

        nameSignin.afterTextChanged {
            loginViewModel.sigInDataChanged(
                    emailSignin.text.toString(),
                    passSignin.text.toString(),
                    nameSignin.text.toString(),
                    lastnameSignin.text.toString()
            )
        }

        lastnameSignin.apply {
            afterTextChanged {
                loginViewModel.sigInDataChanged(
                        emailSignin.text.toString(),
                        passSignin.text.toString(),
                        nameSignin.text.toString(),
                        lastnameSignin.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.signUp(
                                emailSignin.text.toString(),
                                passSignin.text.toString(),
                                nameSignin.text.toString(),
                            lastnameSignin.text.toString(),
                            context
                        )
                }
                false
            }

            signUnButton.setOnClickListener {
                loginViewModel.signUp(
                        emailSignin.text.toString(),
                        passSignin.text.toString(),
                        nameSignin.text.toString(),
                    lastnameSignin.text.toString(),
                    context
                )
            }
        }
    }

    private fun firebaseLogin() {
        val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                SIGN_IN_REQ_CODE
        )
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        loginButtonTwitter.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQ_CODE) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                user!!.getIdToken(true)
                        .addOnSuccessListener { result ->
                            val idToken = result.token
                            loginViewModel.signInSocial(GOOGLE, idToken!!, null, applicationContext)

                        }
                // ...
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
