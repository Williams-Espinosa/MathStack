package com.williamsel.mathstack.features.public.register.domain.entities

data class Register(
    val userId: String,
    val username: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val isEmailVerified: Boolean = false
)