package org.ayaz.spx500.domain.mapper.user

import org.ayaz.spx500.data.entities.user.UserEntity
import org.ayaz.spx500.domain.models.user.UserModel
import org.ayaz.spx500.domain.util.Mapper

class UserMapper: Mapper<UserEntity, UserModel> {
    override fun toModel(dto: UserEntity): UserModel {
        return UserModel(dto.id.toString(), dto.name, dto.lastName, dto.token)
    }
}