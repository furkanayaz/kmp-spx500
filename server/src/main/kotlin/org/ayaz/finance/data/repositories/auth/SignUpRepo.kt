package org.ayaz.finance.data.repositories.auth

import org.ayaz.finance.data.dto_s.auth.SignUpReqDTO
import org.ayaz.finance.data.uow_s.auth.ISignUpUow
import org.ayaz.finance.data.util.Response
import org.ayaz.finance.domain.util.Resource

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