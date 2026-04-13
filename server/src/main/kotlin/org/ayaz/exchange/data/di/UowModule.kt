package org.ayaz.exchange.data.di

import com.mongodb.client.MongoCollection
import io.ktor.client.HttpClient
import org.ayaz.exchange.data.entities.spx.SPXEntity
import org.ayaz.exchange.data.entities.user.UserEntity
import org.ayaz.exchange.data.uow_s.auth.ILoginUow
import org.ayaz.exchange.data.uow_s.auth.ISignUpUow
import org.ayaz.exchange.data.uow_s.auth.LoginUow
import org.ayaz.exchange.data.uow_s.auth.SignUpUow
import org.ayaz.exchange.data.uow_s.crypto.CryptoDataUow
import org.ayaz.exchange.data.uow_s.crypto.ICryptoDataUow
import org.ayaz.exchange.data.uow_s.spx.SpxDataUow
import org.ayaz.exchange.data.uow_s.spx.ISpxDataUow
import org.ayaz.exchange.data.uow_s.user.IUserGetUuidUow
import org.ayaz.exchange.data.uow_s.user.IUserValidationUow
import org.ayaz.exchange.data.uow_s.user.UserGetUuidUow
import org.ayaz.exchange.data.uow_s.user.UserValidationUow
import org.ayaz.exchange.data.util.CoinMarketCap
import org.ayaz.exchange.data.util.SpxCollection
import org.ayaz.exchange.data.util.UserCollection
import org.ayaz.exchange.domain.di.MapperModule
import org.ayaz.exchange.domain.di.UtilModule
import org.ayaz.exchange.domain.mapper.user.UserMapper
import org.ayaz.exchange.domain.util.encryption.PasswordEncryption
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [DBModule::class, UtilModule::class, MapperModule::class])
class UowModule {

    /** AUTH UNIT OF WORKS */

    @Single([ILoginUow::class])
    fun bindLoginUow(
        @UserCollection userCollection: MongoCollection<UserEntity>,
        passwordEncryption: PasswordEncryption,
        userMapper: UserMapper
    ) = LoginUow(userCollection, passwordEncryption, userMapper)

    @Single([ISignUpUow::class])
    fun bindSignUpUow(
        @UserCollection userCollection: MongoCollection<UserEntity>,
        passwordEncryption: PasswordEncryption
    ) = SignUpUow(userCollection, passwordEncryption)

    @Single([IUserValidationUow::class])
    fun bindUserValidationUow(
        @UserCollection userCollection: MongoCollection<UserEntity>
    ) = UserValidationUow(userCollection)

    @Single([IUserGetUuidUow::class])
    fun bindUserGetUuidUow(
        @UserCollection userCollection: MongoCollection<UserEntity>
    ) = UserGetUuidUow(userCollection)

    /** SPX UNIT OF WORKS */

    @Single([ISpxDataUow::class])
    fun bindSpxDataUow(@SpxCollection collection: MongoCollection<SPXEntity>) = SpxDataUow(collection)

    @Single([ICryptoDataUow::class])
    fun bindCryptoDataUow(@CoinMarketCap client: HttpClient) = CryptoDataUow(client)

}