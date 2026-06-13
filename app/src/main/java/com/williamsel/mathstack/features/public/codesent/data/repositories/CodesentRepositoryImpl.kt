package com.williamsel.mathstack.features.public.codesent.data.repositories

import com.williamsel.mathstack.features.public.codesent.data.datasource.api.CodesentApi
import com.williamsel.mathstack.features.public.codesent.data.mapper.toDomain
import com.williamsel.mathstack.features.public.codesent.domain.repositories.CodesentRepository
import javax.inject.Inject

class CodesentRepositoryImpl @Inject constructor(
    private val api: CodesentApi
) : CodesentRepository {

    override suspend fun resendCode(email: String): Result<Unit> = runCatching {
        api.resendCode(email).toDomain()
    }.map { }

    override suspend fun verifyCode(email: String, code: String): Result<Unit> = runCatching {
        api.verifyCode(email, code).toDomain()
    }.map { }
}