package com.williamsel.mathstack.features.public.forgotpassword.domain.entities

data class GoogleUser(
    val userId: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val idToken: String
)