package org.ayaz.spx500.data.di

import com.mongodb.client.MongoCollection
import org.ayaz.spx500.data.entities.user.UserEntity
import org.ayaz.spx500.data.repositories.auth.ILoginRepo
import org.ayaz.spx500.data.repositories.auth.ISignUpRepo
import org.ayaz.spx500.data.repositories.auth.LoginRepo
import org.ayaz.spx500.data.repositories.auth.SignUpRepo
import org.ayaz.spx500.data.repositories.user.IUserValidationRepo
import org.ayaz.spx500.data.repositories.user.UserValidationRepo
import org.ayaz.spx500.domain.di.MapperModule
import org.ayaz.spx500.domain.di.UtilModule
import org.ayaz.spx500.domain.mapper.user.UserMapper
import org.ayaz.spx500.domain.util.encryption.PasswordEncryption
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [DBModule::class, UtilModule::class, MapperModule::class])
class RepoModule {

    @Single([ILoginRepo::class])
    fun provideLoginRepo(
        userCollection: MongoCollection<UserEntity>,
        passwordEncryption: PasswordEncryption,
        userMapper: UserMapper
    ) = LoginRepo(userCollection, passwordEncryption, userMapper)

    @Single([ISignUpRepo::class])
    fun provideSignUpRepo(
        userCollection: MongoCollection<UserEntity>,
        passwordEncryption: PasswordEncryption
    ) = SignUpRepo(userCollection, passwordEncryption)

    @Single([IUserValidationRepo::class])
    fun provideUserValidationRepo(
        userCollection: MongoCollection<UserEntity>
    ) = UserValidationRepo(userCollection)

}