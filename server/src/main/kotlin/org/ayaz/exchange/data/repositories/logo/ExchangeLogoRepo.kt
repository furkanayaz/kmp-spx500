package org.ayaz.exchange.data.repositories.logo

interface IExchangeLogoRepo {
    fun getSpxLogo(symbol: String?): String?
    fun getCryptoLogo(symbol: String?): String?
}

class ExchangeLogoRepo : IExchangeLogoRepo {
    private companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/furkanayaz/kmp-exchange/refs/heads/master/storage/logo"
        const val SPX_PATH = "spx500"
        const val CRYPTO_PATH = "crypto"
        const val PNG_MIME_TYPE = "png"
        const val SVG_MIME_TYPE = "svg"
    }

    override fun getSpxLogo(symbol: String?): String? {
        if (symbol == null) return null
        return "$BASE_URL/$SPX_PATH/$symbol.$PNG_MIME_TYPE"
    }

    override fun getCryptoLogo(symbol: String?): String? {
        if (symbol == null) return null
        return "$BASE_URL/$CRYPTO_PATH/${symbol.lowercase()}.$SVG_MIME_TYPE"
    }
}