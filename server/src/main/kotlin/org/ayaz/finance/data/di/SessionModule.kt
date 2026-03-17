package org.ayaz.finance.data.di

import org.ayaz.finance.data.sessions.token.ITokenSession
import org.ayaz.finance.data.sessions.token.TokenSession
import org.ayaz.finance.data.sessions.user.IUserSession
import org.ayaz.finance.data.sessions.user.UserSession
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import redis.clients.jedis.RedisClient

@Module
class SessionModule {

    @Single([ITokenSession::class])
    fun provideTokenSession(client: RedisClient) = TokenSession(client)

    @Single([IUserSession::class])
    fun provideUserSession() = UserSession()

}