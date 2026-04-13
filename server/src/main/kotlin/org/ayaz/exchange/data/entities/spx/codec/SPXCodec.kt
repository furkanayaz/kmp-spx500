package org.ayaz.exchange.data.entities.spx.codec

import org.ayaz.exchange.data.entities.spx.SPXDetailEntity
import org.ayaz.exchange.data.entities.spx.SPXEntity
import org.bson.BsonReader
import org.bson.BsonType
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.types.ObjectId

class SPXCodec(
    private val detailCodec: SPXDetailCodec
): Codec<SPXEntity> {
    override fun encode(
        writer: BsonWriter,
        value: SPXEntity,
        encoderContext: EncoderContext
    ) {
        writer.writeStartDocument()

        writer.writeString("symbol", value.symbol)
        writer.writeString("security", value.security)
        writer.writeString("sector", value.sector)

        writer.writeName("details")
        detailCodec.encode(writer, value.details!!, encoderContext)

        writer.writeObjectId("_id", value.id)

        writer.writeEndDocument()
    }

    override fun getEncoderClass(): Class<SPXEntity> = SPXEntity::class.java

    override fun decode(
        reader: BsonReader,
        decoderContext: DecoderContext
    ): SPXEntity {
        var symbol: String? = null
        var security: String? = null
        var sector: String? = null
        var details: SPXDetailEntity? = null
        var id: ObjectId? = null

        reader.readStartDocument()

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            when(reader.readName()) {
                "symbol" -> symbol = reader.readString()
                "security" -> security = reader.readString()
                "sector" -> sector = reader.readString()
                "details" -> details = detailCodec.decode(reader, decoderContext)
                "_id" -> id = reader.readObjectId()
                else -> reader.skipValue()
            }
        }

        reader.readEndDocument()

        return SPXEntity(symbol, security, sector, details, id!!)
    }

}