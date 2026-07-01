package com.williamsel.mathstack.features.auth.login.domain.entities

data class Login(
    val userId: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val emailVerified: Boolean,
    val accessToken: String,
    val refreshToken: String
)
