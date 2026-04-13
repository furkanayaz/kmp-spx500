package org.ayaz.exchange.data.util.serializers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.jsonPrimitive
import java.math.BigDecimal

typealias CryptoDecimal = @Serializable(NullableBigDecimalSerializer::class) BigDecimal?

class NullableBigDecimalSerializer: KSerializer<BigDecimal?> {
    override val descriptor: SerialDescriptor get() = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: BigDecimal?) {
        val jsonEncoder = encoder as? JsonEncoder

        if (jsonEncoder == null) encoder.encodeNull()
        if (value == null) jsonEncoder?.encodeNull()

        jsonEncoder?.encodeJsonElement(JsonUnquotedLiteral(value?.toPlainString()))
    }

    override fun deserialize(decoder: Decoder): BigDecimal? {
        val jsonDecoder = decoder as? JsonDecoder ?: return null
        val element = jsonDecoder.decodeJsonElement()
        if (element is JsonNull) return null

        return element.jsonPrimitive.content.toBigDecimal()
    }

}