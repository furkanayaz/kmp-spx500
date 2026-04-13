package org.ayaz.exchange.domain.di

import org.ayaz.exchange.data.di.UowModule
import org.ayaz.exchange.data.repositories.auth.ILoginRepo
import org.ayaz.exchange.data.repositories.auth.ISignUpRepo
import org.ayaz.exchange.data.repositories.auth.ILogoutRepo
import org.ayaz.exchange.data.repositories.crypto.ICryptoDataRepo
import org.ayaz.exchange.data.repositories.spx.ISpxDataRepo
import org.ayaz.exchange.domain.use_cases.auth.LoginUseCase
import org.ayaz.exchange.domain.use_cases.auth.SignUpUseCase
import org.ayaz.exchange.domain.use_cases.auth.LogoutUseCase
import org.ayaz.exchange.domain.use_cases.crypto.CryptoDataDetailUseCase
import org.ayaz.exchange.domain.use_cases.crypto.CryptoDataUseCase
import org.ayaz.exchange.domain.use_cases.spx.GetSpxDataDetailUseCase
import org.ayaz.exchange.domain.use_cases.spx.GetSpxDataUseCase
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
    fun provideSpxDataUseCase(repo: ISpxDataRepo): GetSpxDataUseCase = GetSpxDataUseCase(repo)

    @Single
    fun provideSpxDataDetailUseCase(repo: ISpxDataRepo): GetSpxDataDetailUseCase = GetSpxDataDetailUseCase(repo)

    @Single
    fun provideCryptoDataUseCase(repo: ICryptoDataRepo): CryptoDataUseCase = CryptoDataUseCase(repo)

    @Single
    fun provideCryptoDataDetailUseCase(repo: ICryptoDataRepo): CryptoDataDetailUseCase = CryptoDataDetailUseCase(repo)

}