package org.ayaz.finance.domain.mapper.user

import org.ayaz.finance.data.entities.user.UserEntity
import org.ayaz.finance.domain.models.user.UserModel
import org.ayaz.finance.domain.util.Mapper

class UserMapper: Mapper<UserEntity, UserModel> {
    override fun invoke(dto: UserEntity): UserModel {
        return UserModel(dto.id.toString(), dto.fistName, dto.lastName, dto.token, dto.createdAt)
    }
}