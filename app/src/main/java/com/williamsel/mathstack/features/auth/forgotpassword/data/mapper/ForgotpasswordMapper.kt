package com.williamsel.mathstack.features.auth.forgotpassword.data.mapper

import com.williamsel.mathstack.features.auth.forgotpassword.data.models.ForgotpasswordDto
import com.williamsel.mathstack.features.auth.forgotpassword.domain.entities.Forgotpassword

fun ForgotpasswordDto.toDomain(): Forgotpassword = Forgotpassword(
    email   = email,
    message = message
)
