package com.williamsel.mathstack.features.public.codesent.data.mapper

import com.williamsel.mathstack.features.public.codesent.data.models.CodesentDto
import com.williamsel.mathstack.features.public.codesent.domain.entities.Codesent

fun CodesentDto.toDomain(): Codesent = Codesent(
    email   = email,
    message = message
)