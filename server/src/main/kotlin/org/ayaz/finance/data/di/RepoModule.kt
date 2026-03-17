package org.ayaz.finance.data.di

import org.ayaz.finance.data.repositories.auth.ILoginRepo
import org.ayaz.finance.data.repositories.auth.ISignUpRepo
import org.ayaz.finance.data.repositories.auth.LoginRepo
import org.ayaz.finance.data.repositories.auth.SignUpRepo
import org.ayaz.finance.data.repositories.auth.ILogoutRepo
import org.ayaz.finance.data.repositories.auth.LogoutRepo
import org.ayaz.finance.data.repositories.spx.ISpxDataRepo
import org.ayaz.finance.data.repositories.spx.SpxDataRepo
import org.ayaz.finance.data.sessions.token.TokenSession
import org.ayaz.finance.data.uow_s.auth.ILoginUow
import org.ayaz.finance.data.uow_s.auth.ISignUpUow
import org.ayaz.finance.data.uow_s.spx.IGetSpxDataUow
import org.ayaz.finance.data.uow_s.user.IUserGetUuidUow
import org.ayaz.finance.data.util.jwt.JWTUtil
import org.ayaz.finance.domain.di.MapperModule
import org.ayaz.finance.domain.mapper.login.LoginResMapper
import org.ayaz.finance.domain.mapper.spx.SpxDetailResMapper
import org.ayaz.finance.domain.mapper.spx.SpxResMapper
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
    fun bindSpxDataRepo(uow: IGetSpxDataUow, spxResMapper: SpxResMapper, spxDetailResMapper: SpxDetailResMapper) = SpxDataRepo(uow, spxResMapper, spxDetailResMapper)

}