package org.ayaz.exchange.data.repositories.auth

import org.ayaz.exchange.data.dto_s.auth.SignUpReqDTO
import org.ayaz.exchange.data.uow_s.auth.ISignUpUow
import org.ayaz.exchange.data.base.Response
import org.ayaz.exchange.domain.base.Resource

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