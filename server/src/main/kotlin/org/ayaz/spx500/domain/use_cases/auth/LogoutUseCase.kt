package org.ayaz.spx500.domain.use_cases.auth

import org.ayaz.spx500.data.repositories.auth.ILogoutRepo

class LogoutUseCase(
    private val repo: ILogoutRepo
) {
    operator fun invoke(email: String?) = repo(email)
}