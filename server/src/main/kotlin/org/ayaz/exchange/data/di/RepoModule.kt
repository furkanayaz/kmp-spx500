package org.ayaz.exchange.data.di

import org.ayaz.exchange.data.repositories.auth.ILoginRepo
import org.ayaz.exchange.data.repositories.auth.ISignUpRepo
import org.ayaz.exchange.data.repositories.auth.LoginRepo
import org.ayaz.exchange.data.repositories.auth.SignUpRepo
import org.ayaz.exchange.data.repositories.auth.ILogoutRepo
import org.ayaz.exchange.data.repositories.auth.LogoutRepo
import org.ayaz.exchange.data.repositories.crypto.CryptoDataRepo
import org.ayaz.exchange.data.repositories.crypto.ICryptoDataRepo
import org.ayaz.exchange.data.repositories.spx.ISpxDataRepo
import org.ayaz.exchange.data.repositories.spx.SpxDataRepo
import org.ayaz.exchange.data.sessions.token.TokenSession
import org.ayaz.exchange.data.uow_s.auth.ILoginUow
import org.ayaz.exchange.data.uow_s.auth.ISignUpUow
import org.ayaz.exchange.data.uow_s.crypto.ICryptoDataUow
import org.ayaz.exchange.data.uow_s.spx.ISpxDataUow
import org.ayaz.exchange.data.uow_s.user.IUserGetUuidUow
import org.ayaz.exchange.data.auth.jwt.JWTUtil
import org.ayaz.exchange.data.repositories.logo.ExchangeLogoRepo
import org.ayaz.exchange.data.repositories.logo.IExchangeLogoRepo
import org.ayaz.exchange.domain.di.MapperModule
import org.ayaz.exchange.domain.mapper.login.LoginResMapper
import org.ayaz.exchange.domain.mapper.spx.SpxDetailResMapper
import org.ayaz.exchange.domain.mapper.spx.SpxResMapper
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module([UowModule::class, UtilModule::class, SessionModule::class, MapperModule::class])
class RepoModule {
    /** AUTH REPOSITORIES */

    @Single([ILoginRepo::class])
    fun bindLoginRepo(
        loginUow: ILoginUow,
        jwtUtil: JWTUtil,
        tokenSession: TokenSession,
        loginResMapper: LoginResMapper
    ) = LoginRepo(loginUow, jwtUtil, tokenSession, loginResMapper)

    @Single([ISignUpRepo::class])
    fun bindSignUpRepo(
        signUpUow: ISignUpUow
    ) = SignUpRepo(signUpUow)

    @Single([ILogoutRepo::class])
    fun bindUserGetUuidRepo(
        userGetUuidUow: IUserGetUuidUow,
        tokenSession: TokenSession
    ) = LogoutRepo(userGetUuidUow, tokenSession)

    /** SPX REPOSITORIES */

    @Single([ISpxDataRepo::class])
    fun bindSpxDataRepo(uow: ISpxDataUow, spxResMapper: SpxResMapper, spxDetailResMapper: SpxDetailResMapper, logoRepo: IExchangeLogoRepo) = SpxDataRepo(uow, spxResMapper, spxDetailResMapper, logoRepo)

    /** CRYPTO REPOSITORIES */

    @Single([ICryptoDataRepo::class])
    fun bindCryptoDataRepo(uow: ICryptoDataUow, logoRepo: IExchangeLogoRepo) = CryptoDataRepo(uow, logoRepo)

    /** LOGO REPOSITORY */

    @Single([IExchangeLogoRepo::class])
    fun bindLogoRepo() = ExchangeLogoRepo()
}