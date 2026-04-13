package org.ayaz.exchange.data.sessions.token

import redis.clients.jedis.RedisClient
import redis.clients.jedis.params.SetParams

interface ITokenSession {
    fun addToken(uuid: String, token: String): Boolean
    fun removeToken(uuid: String): Boolean
}

class TokenSession(
    private val redisClient: RedisClient
): ITokenSession {
    private companion object {
        const val BASE_KEY = "token"
        const val EXPIRE_MILLIS = 1000 * 60 * 60 * 2L
    }

    private fun getKey(uuid: String): String = "$BASE_KEY:$uuid"

    override fun addToken(uuid: String, token: String): Boolean {
        val result = redisClient.set(getKey(uuid), token, SetParams().px(EXPIRE_MILLIS))
        return result == "OK"
    }

    override fun removeToken(uuid: String): Boolean {
        val result = redisClient.del(getKey(uuid))
        return result == 1L
    }
}