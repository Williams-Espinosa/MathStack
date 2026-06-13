package com.williamsel.mathstack.features.public.login.data.mapper

import com.williamsel.mathstack.features.public.login.data.models.LoginDto
import com.williamsel.mathstack.features.public.login.domain.entities.Login

fun LoginDto.toDomain(): Login = Login(
    userId         = userId,
    email          = email,
    displayName    = displayName,
    photoUrl       = photoUrl,
    isEmailVerified = emailVerified
)