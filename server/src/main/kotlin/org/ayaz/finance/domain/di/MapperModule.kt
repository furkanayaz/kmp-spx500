package org.ayaz.finance.domain.di

import org.ayaz.finance.domain.mapper.login.LoginResMapper
import org.ayaz.finance.domain.mapper.spx.SpxDetailResMapper
import org.ayaz.finance.domain.mapper.spx.SpxResMapper
import org.ayaz.finance.domain.mapper.user.UserMapper
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class MapperModule {

    @Single
    fun provideUserMapper(): UserMapper = UserMapper()

    @Single
    fun provideLoginResMapper(): LoginResMapper = LoginResMapper()

    @Single
    fun provideSpxResMapper(): SpxResMapper = SpxResMapper()

    @Single
    fun provideSpxDetailResMapper(): SpxDetailResMapper = SpxDetailResMapper()

}