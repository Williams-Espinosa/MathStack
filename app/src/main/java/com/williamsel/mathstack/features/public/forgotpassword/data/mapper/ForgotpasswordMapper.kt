package com.williamsel.mathstack.features.public.forgotpassword.data.mapper

import com.williamsel.mathstack.features.public.forgotpassword.data.models.ForgotpasswordDto
import com.williamsel.mathstack.features.public.forgotpassword.domain.entities.Forgotpassword

fun ForgotpasswordDto.toDomain(): Forgotpassword = Forgotpassword(
    email   = email,
    message = message
)