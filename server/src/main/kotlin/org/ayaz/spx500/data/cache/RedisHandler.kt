package org.ayaz.spx500.data.cache

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single
import redis.clients.jedis.ConnectionPoolConfig
import redis.clients.jedis.RedisClient
import java.time.Duration

@Single
class RedisHandler(
    @InjectedParam private val host: String,
    @InjectedParam private val port: Int
) {
    private fun getConfig() = ConnectionPoolConfig().apply {
        maxTotal = 5
        maxIdle = 2
        minIdle = 1

        setMaxWait(Duration.ofSeconds(5))
        blockWhenExhausted = true
        testWhileIdle = true
    }

    private fun getClient(): RedisClient {
        val builder = RedisClient.builder()
            .hostAndPort("127.0.0.1", 6379)
            .poolConfig(getConfig())

        return builder.build()
    }

    fun execute(callBack: RedisClient.() -> Unit) = getClient().use(callBack)
}