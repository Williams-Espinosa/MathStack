package com.williamsel.mathstack.features.auth.login.data.mapper

import com.williamsel.mathstack.features.auth.login.data.models.LoginDto
import com.williamsel.mathstack.features.auth.login.domain.entities.Login

fun LoginDto.toDomain(): Login {
    return Login(
        userId = userId,
        email = email,
        displayName = displayName,
        photoUrl = photoUrl,
        emailVerified = emailVerified,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}
