package org.ayaz.exchange.data.di

import org.ayaz.exchange.data.uow_s.user.UserValidationUow
import org.ayaz.exchange.data.auth.jwt.JWTUtil
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [UowModule::class])
class UtilModule {

    @Single
    fun provideJWTUtil(userValidationRepo: UserValidationUow): JWTUtil = JWTUtil(userValidationRepo)

}