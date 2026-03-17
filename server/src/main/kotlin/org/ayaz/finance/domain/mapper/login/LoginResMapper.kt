package org.ayaz.finance.domain.mapper.login

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.ayaz.finance.data.dto_s.auth.LoginResDTO
import org.ayaz.finance.domain.models.user.UserModel
import org.ayaz.finance.domain.util.Mapper

class LoginResMapper: Mapper<UserModel, LoginResDTO> {
    override fun invoke(dto: UserModel): LoginResDTO = LoginResDTO(dto.fistName, dto.lastName, "", dto.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()))
}