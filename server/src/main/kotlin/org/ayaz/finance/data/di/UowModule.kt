package org.ayaz.finance.data.di

import com.mongodb.client.MongoCollection
import org.ayaz.finance.data.entities.spx.SPXEntity
import org.ayaz.finance.data.entities.user.UserEntity
import org.ayaz.finance.data.uow_s.auth.ILoginUow
import org.ayaz.finance.data.uow_s.auth.ISignUpUow
import org.ayaz.finance.data.uow_s.auth.LoginUow
import org.ayaz.finance.data.uow_s.auth.SignUpUow
import org.ayaz.finance.data.uow_s.spx.GetSpxDataUow
import org.ayaz.finance.data.uow_s.spx.IGetSpxDataUow
import org.ayaz.finance.data.uow_s.user.IUserGetUuidUow
import org.ayaz.finance.data.uow_s.user.IUserValidationUow
import org.ayaz.finance.data.uow_s.user.UserGetUuidUow
import org.ayaz.finance.data.uow_s.user.UserValidationUow
import org.ayaz.finance.data.util.SpxCollection
import org.ayaz.finance.data.util.UserCollection
import org.ayaz.finance.domain.di.MapperModule
import org.ayaz.finance.domain.di.UtilModule
import org.ayaz.finance.domain.mapper.user.UserMapper
import org.ayaz.finance.domain.util.encryption.PasswordEncryption
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [DBModule::class, UtilModule::class, MapperModule::class])
class UowModule {

    /** AUTH UNIT OF WORKS */

    @Single([ILoginUow::class])
    fun provideLoginUow(
        @UserCollection userCollection: MongoCollection<UserEntity>,
        passwordEncryption: PasswordEncryption,
        userMapper: UserMapper
    ) = LoginUow(userCollection, passwordEncryption, userMapper)

    @Single([ISignUpUow::class])
    fun provideSignUpUow(
        @UserCollection userCollection: MongoCollection<UserEntity>,
        passwordEncryption: PasswordEncryption
    ) = SignUpUow(userCollection, passwordEncryption)

    @Single([IUserValidationUow::class])
    fun provideUserValidationUow(
        @UserCollection userCollection: MongoCollection<UserEntity>
    ) = UserValidationUow(userCollection)

    @Single([IUserGetUuidUow::class])
    fun provideUserGetUuidUow(
        @UserCollection userCollection: MongoCollection<UserEntity>
    ) = UserGetUuidUow(userCollection)

    /** SPX UNIT OF WORKS */

    @Single([IGetSpxDataUow::class])
    fun provideGetSpxDataUow(@SpxCollection collection: MongoCollection<SPXEntity>) = GetSpxDataUow(collection)

}