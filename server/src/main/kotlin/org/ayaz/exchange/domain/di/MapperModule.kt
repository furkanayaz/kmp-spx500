package org.ayaz.exchange.domain.di

import org.ayaz.exchange.domain.mapper.login.LoginResMapper
import org.ayaz.exchange.domain.mapper.spx.SpxDetailResMapper
import org.ayaz.exchange.domain.mapper.spx.SpxResMapper
import org.ayaz.exchange.domain.mapper.user.UserMapper
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