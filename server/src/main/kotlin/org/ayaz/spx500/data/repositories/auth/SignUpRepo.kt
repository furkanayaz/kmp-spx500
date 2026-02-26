package org.ayaz.spx500.data.repositories.auth

import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.data.uow_s.auth.ISignUpUow
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.domain.util.Resource

fun interface ISignUpRepo {
    operator fun invoke(req: SignUpReqDTO): Response<Boolean>
}

class SignUpRepo(
    private val signUpUow: ISignUpUow
): ISignUpRepo {
    override fun invoke(req: SignUpReqDTO): Response<Boolean> {
        return when(val response = signUpUow(req)) {
            is Resource.Error<Boolean> -> Response.Error(errorMessages = response.messages)
            is Resource.Success<Boolean> -> Response.Success(item = response.item)
        }
    }
}