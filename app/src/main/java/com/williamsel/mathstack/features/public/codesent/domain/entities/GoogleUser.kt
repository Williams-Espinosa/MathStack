package com.williamsel.mathstack.features.public.codesent.domain.entities

data class GoogleUser(
    val userId: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val idToken: String
)