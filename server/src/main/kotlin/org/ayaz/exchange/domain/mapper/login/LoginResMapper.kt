package org.ayaz.exchange.domain.mapper.login

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.ayaz.exchange.data.dto_s.auth.LoginResDTO
import org.ayaz.exchange.domain.models.user.UserModel
import org.ayaz.exchange.domain.util.Mapper

class LoginResMapper: Mapper<UserModel, LoginResDTO> {
    override fun invoke(dto: UserModel): LoginResDTO = LoginResDTO(dto.fistName, dto.lastName, "", dto.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()))
}