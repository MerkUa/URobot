package com.urobot.droid.ui.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.NetModel.Industries
import com.urobot.droid.R
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.ILoginContract
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.LoginRepository
import com.urobot.droid.db.Industry
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application), ILoginContract,
    IUserContract {

    private val app: Application = application

    private val loginRepository: LoginRepository = LoginRepository(this)

    private val _loginForm = MutableLiveData<LoginFormState>()
    private val _signUpForm = MutableLiveData<SignInFormState>()
    private val _forgotPass = MutableLiveData<ForgotPassFormState>()

    val loginFormState: LiveData<LoginFormState> = _loginForm
    val sigUpFormState: LiveData<SignInFormState> = _signUpForm
    val forgotPassState: LiveData<ForgotPassFormState> = _forgotPass

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String, context: Context) {
        loginRepository.login(username, password, context)
    }

    fun signUp(email: String, password: String, name: String, lastname: String) {
        loginRepository.signUp(email, password, name, lastname)
    }

    fun signInSocial(type: String, token: String, secret: String?) {
        loginRepository.signInSocial(type, token, secret)
    }

    fun forgotPass(email: String) {
        loginRepository.forgotPass(email)
    }

    // Show Error
    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun forgotPassDataChanged(username: String) {
        if (!isUserNameValid(username)) {
            _forgotPass.value = ForgotPassFormState(userEmailError = R.string.invalid_username)
        } else {
            _forgotPass.value = ForgotPassFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
//        return if (username.contains('@')) {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
//        } else {
//            username.isNotBlank()
//        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 0
    }

    override fun onLoginError() {
        _loginResult.value = LoginResult(error = R.string.login_failed)
    }

    override fun onSignUpResult() {
        LoginResult(successSignUp = R.string.signUp_success)
        _loginResult.value = LoginResult(error = R.string.signUp_success)

    }

    override fun onForgotPassError() {
        _forgotPass.value = ForgotPassFormState(errorForgotPass = R.string.forgotPass_error)
    }

    override fun onForgotPassSuccess() {
        _forgotPass.value = ForgotPassFormState(successForgotPass = R.string.forgotPass_success)
    }

    override fun insertIndustryDB(result: List<Industries>?) {

        CoroutineScope(Dispatchers.IO).launch {
            for (item in result!!){
                UserRoomDatabase.getDatabase(getApplication()).industryDao().insertIndustry(
                    Industry(item.id, item.name))
            }
        }
    }


    // Show Error
    fun sigInDataChanged(username: String, password: String, name: String, lastName: String) {
        if (!isUserNameValid(username)) {
            _signUpForm.value = SignInFormState(userEmailError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _signUpForm.value = SignInFormState(passwordError = R.string.invalid_password)
        } else if (!isNameValid(name)) {
            _signUpForm.value = SignInFormState(nameError = R.string.invalid_username)
        } else if (!isLastNameValid(lastName)) {
            _signUpForm.value = SignInFormState(lastNameError = R.string.invalid_password)
        } else {
            _signUpForm.value = SignInFormState(isDataValid = true)
        }
    }

    // A placeholder name validation check
    private fun isNameValid(name: String): Boolean {
        return name.length > 0
    }

    // A placeholder lastname validation check
    private fun isLastNameValid(lastname: String): Boolean {
        return lastname.length > 0
    }

    override fun onLoginResult(user: User) {
        val sharedPref: SharedPreferences = app.getSharedPreferences("isLoged", 0)
        val editor = sharedPref.edit()
        editor.putBoolean("isLoged", true)
        editor.apply()

        val userDao = UserRoomDatabase.getDatabase(app).userDao()
        val repository = UserRepository(userDao, this)
        viewModelScope.launch {
            userDao.deleteAll()
            repository.insert(user)

            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = user.fName + user.lName!!))
        }


    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}
