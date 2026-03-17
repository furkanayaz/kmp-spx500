package org.ayaz.finance.domain.use_cases.auth

import org.ayaz.finance.data.repositories.auth.ILogoutRepo

class LogoutUseCase(
    private val repo: ILogoutRepo
) {
    operator fun invoke(email: String?) = repo(email)
}