package org.ayaz.spx500.data.di

import org.ayaz.spx500.data.repositories.user.UserValidationRepo
import org.ayaz.spx500.data.util.jwt.JWTUtil
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [RepoModule::class])
class UtilModule {

    @Single
    fun provideJWTUtil(userValidationRepo: UserValidationRepo): JWTUtil = JWTUtil(userValidationRepo)

}