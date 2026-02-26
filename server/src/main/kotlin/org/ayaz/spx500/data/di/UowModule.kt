package org.ayaz.spx500.data.di

import com.mongodb.client.MongoCollection
import org.ayaz.spx500.data.entities.user.UserEntity
import org.ayaz.spx500.data.uow_s.auth.ILoginUow
import org.ayaz.spx500.data.uow_s.auth.ISignUpUow
import org.ayaz.spx500.data.uow_s.auth.LoginUow
import org.ayaz.spx500.data.uow_s.auth.SignUpUow
import org.ayaz.spx500.data.uow_s.user.IUserGetUuidUow
import org.ayaz.spx500.data.uow_s.user.IUserValidationUow
import org.ayaz.spx500.data.uow_s.user.UserGetUuidUow
import org.ayaz.spx500.data.uow_s.user.UserValidationUow
import org.ayaz.spx500.domain.di.MapperModule
import org.ayaz.spx500.domain.di.UtilModule
import org.ayaz.spx500.domain.mapper.user.UserMapper
import org.ayaz.spx500.domain.util.encryption.PasswordEncryption
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [DBModule::class, UtilModule::class, MapperModule::class])
class UowModule {

    @Single([ILoginUow::class])
    fun provideLoginUow(
        userCollection: MongoCollection<UserEntity>,
        passwordEncryption: PasswordEncryption,
        userMapper: UserMapper
    ) = LoginUow(userCollection, passwordEncryption, userMapper)

    @Single([ISignUpUow::class])
    fun provideSignUpUow(
        userCollection: MongoCollection<UserEntity>,
        passwordEncryption: PasswordEncryption
    ) = SignUpUow(userCollection, passwordEncryption)

    @Single([IUserValidationUow::class])
    fun provideUserValidationUow(
        userCollection: MongoCollection<UserEntity>
    ) = UserValidationUow(userCollection)

    @Single([IUserGetUuidUow::class])
    fun provideUserGetUuidUow(
        userCollection: MongoCollection<UserEntity>
    ) = UserGetUuidUow(userCollection)

}