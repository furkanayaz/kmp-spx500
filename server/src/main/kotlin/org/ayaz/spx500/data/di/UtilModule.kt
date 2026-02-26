package org.ayaz.spx500.data.di

import org.ayaz.spx500.data.uow_s.user.UserValidationUow
import org.ayaz.spx500.data.util.jwt.JWTUtil
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [UowModule::class])
class UtilModule {

    @Single
    fun provideJWTUtil(userValidationRepo: UserValidationUow): JWTUtil = JWTUtil(userValidationRepo)

}