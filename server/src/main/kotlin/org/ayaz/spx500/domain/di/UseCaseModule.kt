package org.ayaz.spx500.domain.di

import org.ayaz.spx500.data.di.RepoModule
import org.ayaz.spx500.data.repositories.auth.ILoginRepo
import org.ayaz.spx500.data.repositories.auth.ISignUpRepo
import org.ayaz.spx500.domain.use_cases.auth.LoginUseCase
import org.ayaz.spx500.domain.use_cases.auth.SignUpUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [RepoModule::class])
class UseCaseModule {

    @Single
    fun provideLoginUseCase(loginRepo: ILoginRepo): LoginUseCase = LoginUseCase(loginRepo)

    @Single
    fun provideSignUpUseCase(signUpRepo: ISignUpRepo): SignUpUseCase = SignUpUseCase(signUpRepo)

}