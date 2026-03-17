package org.ayaz.finance.domain.di

import org.ayaz.finance.data.di.UowModule
import org.ayaz.finance.data.repositories.auth.ILoginRepo
import org.ayaz.finance.data.repositories.auth.ISignUpRepo
import org.ayaz.finance.data.repositories.auth.ILogoutRepo
import org.ayaz.finance.data.repositories.spx.ISpxDataRepo
import org.ayaz.finance.domain.use_cases.auth.LoginUseCase
import org.ayaz.finance.domain.use_cases.auth.SignUpUseCase
import org.ayaz.finance.domain.use_cases.auth.LogoutUseCase
import org.ayaz.finance.domain.use_cases.spx.GetSpxDataDetailUseCase
import org.ayaz.finance.domain.use_cases.spx.GetSpxDataUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [UowModule::class])
class UseCaseModule {

    @Single
    fun provideLoginUseCase(loginRepo: ILoginRepo): LoginUseCase = LoginUseCase(loginRepo)

    @Single
    fun provideSignUpUseCase(signUpRepo: ISignUpRepo): SignUpUseCase = SignUpUseCase(signUpRepo)

    @Single
    fun provideGetUuidUseCase(getUuidRepo: ILogoutRepo): LogoutUseCase = LogoutUseCase(getUuidRepo)

    @Single
    fun provideGetSpxDataUseCase(repo: ISpxDataRepo): GetSpxDataUseCase = GetSpxDataUseCase(repo)

    @Single
    fun provideGetSpxDataDetailUseCase(repo: ISpxDataRepo): GetSpxDataDetailUseCase = GetSpxDataDetailUseCase(repo)

}