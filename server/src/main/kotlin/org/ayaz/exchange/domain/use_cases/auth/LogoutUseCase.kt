package org.ayaz.exchange.domain.use_cases.auth

import org.ayaz.exchange.data.repositories.auth.ILogoutRepo

class LogoutUseCase(
    private val repo: ILogoutRepo
) {
    operator fun invoke(email: String) = repo(email)
}