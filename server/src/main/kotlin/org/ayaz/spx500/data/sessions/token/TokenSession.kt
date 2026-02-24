package org.ayaz.spx500.data.sessions.token

import redis.clients.jedis.RedisClient

interface ITokenSession {
    fun addToken(uuid: String, token: String): String
    fun removeToken(uuid: String)
}

class TokenSession(
    private val redisClient: RedisClient
): ITokenSession {
    private companion object {
        const val BASE_KEY = "token"
    }

    private fun getKey(uuid: String): String {
        return "$BASE_KEY:$uuid"
    }

    override fun addToken(uuid: String, token: String): String {
        return redisClient.use {
            it.set(getKey(uuid), token)
        }
    }

    override fun removeToken(uuid: String) {
        redisClient.use {
            it.del(getKey(uuid))
        }
    }
}