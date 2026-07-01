package com.williamsel.mathstack.features.auth.codesent.data.mapper

import com.williamsel.mathstack.features.auth.codesent.data.models.CodesentDto
import com.williamsel.mathstack.features.auth.codesent.domain.entities.Codesent

fun CodesentDto.toDomain(): Codesent = Codesent(
    email   = email,
    message = message
)
