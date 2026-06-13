package com.williamsel.mathstack.features.public.register.data.mapper

import com.williamsel.mathstack.features.public.register.data.models.RegisterDto
import com.williamsel.mathstack.features.public.register.domain.entities.Register

fun RegisterDto.toDomain(): Register = Register(
    userId          = userId,
    username        = username,
    email           = email,
    displayName     = displayName,
    photoUrl        = photoUrl,
    isEmailVerified = emailVerified
)