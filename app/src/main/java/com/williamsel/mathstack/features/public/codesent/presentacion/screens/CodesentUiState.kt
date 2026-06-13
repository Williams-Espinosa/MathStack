package com.williamsel.mathstack.features.public.codesent.presentacion.screens

data class CodesentUiState(
    val email: String = "",
    val codeDigits: List<String> = List(6) { "" },
    val resendCountdown: Int = 60,
    val canResend: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isVerified: Boolean = false
) {
    val isCodeComplete: Boolean
        get() = codeDigits.all { it.isNotEmpty() }

    val fullCode: String
        get() = codeDigits.joinToString("")
}