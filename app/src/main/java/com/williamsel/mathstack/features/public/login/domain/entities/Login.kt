package com.williamsel.mathstack.features.public.login.domain.entities

data class Login(
    val userId: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val isEmailVerified: Boolean = false
)