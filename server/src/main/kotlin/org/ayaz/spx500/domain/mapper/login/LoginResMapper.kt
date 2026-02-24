package org.ayaz.spx500.domain.mapper.login

import org.ayaz.spx500.data.dto_s.auth.LoginResDTO
import org.ayaz.spx500.domain.models.user.UserModel
import org.ayaz.spx500.domain.util.Mapper

class LoginResMapper: Mapper<UserModel, LoginResDTO> {
    override fun toModel(dto: UserModel): LoginResDTO = LoginResDTO(dto.name, dto.lastName, "")
}