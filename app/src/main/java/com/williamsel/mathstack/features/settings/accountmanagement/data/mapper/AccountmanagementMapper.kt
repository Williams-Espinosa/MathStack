package com.williamsel.mathstack.features.settings.accountmanagement.data.mapper

import com.williamsel.mathstack.features.settings.accountmanagement.data.models.AccountmanagementDto
import com.williamsel.mathstack.features.settings.accountmanagement.domain.entities.Accountmanagement

fun AccountmanagementDto.toDomain(): Accountmanagement {
    return Accountmanagement(
        username = username,
        email = email
    )
}
