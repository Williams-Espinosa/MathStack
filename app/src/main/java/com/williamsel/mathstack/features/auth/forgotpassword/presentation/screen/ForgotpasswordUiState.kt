package com.williamsel.mathstack.features.auth.forgotpassword.presentation.screen

enum class ForgotPasswordStep { ENTER_EMAIL, ENTER_CODE, SUCCESS }

data class ForgotpasswordUiState(
    val step: ForgotPasswordStep = ForgotPasswordStep.ENTER_EMAIL,

    val email: String = "",

    val codeDigits: List<String> = List(6) { "" },
    val resendCountdown: Int = 60,
    val canResend: Boolean = false,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isEmailValid: Boolean
        get() = email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isCodeComplete: Boolean
        get() = codeDigits.all { it.isNotEmpty() }

    val fullCode: String
        get() = codeDigits.joinToString("")
}
