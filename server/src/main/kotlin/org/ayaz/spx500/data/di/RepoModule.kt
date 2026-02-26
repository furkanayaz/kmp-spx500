package org.ayaz.spx500.data.di

import org.ayaz.spx500.data.repositories.auth.ILoginRepo
import org.ayaz.spx500.data.repositories.auth.ISignUpRepo
import org.ayaz.spx500.data.repositories.auth.LoginRepo
import org.ayaz.spx500.data.repositories.auth.SignUpRepo
import org.ayaz.spx500.data.repositories.auth.ILogoutRepo
import org.ayaz.spx500.data.repositories.auth.LogoutRepo
import org.ayaz.spx500.data.sessions.token.TokenSession
import org.ayaz.spx500.data.uow_s.auth.ILoginUow
import org.ayaz.spx500.data.uow_s.auth.ISignUpUow
import org.ayaz.spx500.data.uow_s.user.IUserGetUuidUow
import org.ayaz.spx500.data.util.jwt.JWTUtil
import org.ayaz.spx500.domain.di.MapperModule
import org.ayaz.spx500.domain.mapper.login.LoginResMapper
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module([UowModule::class, UtilModule::class, SessionModule::class, MapperModule::class])
class RepoModule {

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

}