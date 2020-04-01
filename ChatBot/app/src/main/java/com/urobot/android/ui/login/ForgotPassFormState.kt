package com.urobot.android.ui.login

/**
 * Data validation state of the login form.
 */
data class ForgotPassFormState(
        val userEmailError: Int? = null,
        val successForgotPass: Int? = null,
        val errorForgotPass: Int? = null,
        val isDataValid: Boolean = false
)
