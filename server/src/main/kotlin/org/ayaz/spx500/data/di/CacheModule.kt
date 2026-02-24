package org.ayaz.spx500.data.di

import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import redis.clients.jedis.ConnectionPoolConfig
import redis.clients.jedis.RedisClient
import java.time.Duration

@Module
class CacheModule {
    private companion object {
        const val HOST = "127.0.0.1"
        const val PORT = 6379
    }

    @Single
    fun provideConnPoolConfig(): ConnectionPoolConfig {
        return ConnectionPoolConfig().apply {
            maxTotal = 5
            maxIdle = 2
            minIdle = 1

            setMaxWait(Duration.ofSeconds(5))
            blockWhenExhausted = true
            testWhileIdle = true
        }
    }

    @Single
    fun provideRedisClient(config: ConnectionPoolConfig): RedisClient {
        val builder = RedisClient.builder()
            .hostAndPort(HOST, PORT)
            .poolConfig(config)

        return builder.build()
    }
}