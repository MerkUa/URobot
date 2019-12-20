package com.urobot.droid.ui.login

/**
 * Data validation state of the login form.
 */
data class SignInFormState(
        val userEmailError: Int? = null,
        val passwordError: Int? = null,
        val nameError: Int? = null,
        val lastNameError: Int? = null,
        val isDataValid: Boolean = false
)
