package com.urobot.android.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: LoggedInUserView? = null,
        val successSignUp: Int? = null,
        val error: Int? = null
)
