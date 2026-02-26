package org.ayaz.spx500.data.repositories.auth

import org.ayaz.spx500.data.sessions.token.TokenSession
import org.ayaz.spx500.data.uow_s.user.IUserGetUuidUow
import org.ayaz.spx500.data.util.Response

fun interface ILogoutRepo {
    operator fun invoke(email: String?): Response<Boolean>
}

class LogoutRepo(
    private val userGetUuidUow: IUserGetUuidUow,
    private val tokenSession: TokenSession
): ILogoutRepo {
    override fun invoke(email: String?): Response<Boolean> {
        if (email == null) return Response.Error(errorMessages = listOf("logout.error"))
        val userUuid = userGetUuidUow(email) ?: return Response.Error(errorMessages = listOf("logout.error"))
        return if (tokenSession.removeToken(userUuid)) Response.Success() else Response.Error(errorMessages = listOf("logout.error"))
    }
}