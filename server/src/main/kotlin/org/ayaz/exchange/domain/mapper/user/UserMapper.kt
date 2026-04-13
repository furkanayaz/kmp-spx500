package org.ayaz.exchange.domain.mapper.user

import org.ayaz.exchange.data.entities.user.UserEntity
import org.ayaz.exchange.domain.models.user.UserModel
import org.ayaz.exchange.domain.util.Mapper

class UserMapper: Mapper<UserEntity, UserModel> {
    override fun invoke(dto: UserEntity): UserModel {
        return UserModel(dto.id.toString(), dto.fistName, dto.lastName, dto.token, dto.createdAt)
    }
}