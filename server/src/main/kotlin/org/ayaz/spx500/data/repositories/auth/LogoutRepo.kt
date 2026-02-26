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
    private companion object {
        const val ERROR_MSG_SESSION_NOT_VALID = "Your session couldn't valid."
        const val ERROR_MSG_LOGOUT = "An error occurred while logging out."
    }

    override fun invoke(email: String?): Response<Boolean> {
        if (email == null) return Response.Error(errorMessages = listOf(ERROR_MSG_SESSION_NOT_VALID))
        val userUuid = userGetUuidUow(email) ?: return Response.Error(errorMessages = listOf(ERROR_MSG_SESSION_NOT_VALID))
        return if (tokenSession.removeToken(userUuid)) Response.Success() else Response.Error(errorMessages = listOf(ERROR_MSG_LOGOUT))
    }
}