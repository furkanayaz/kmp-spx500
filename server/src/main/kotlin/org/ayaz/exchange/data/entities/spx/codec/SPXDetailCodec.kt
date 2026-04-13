package org.ayaz.exchange.data.entities.spx.codec

import org.ayaz.exchange.data.entities.spx.SPXDetailEntity
import org.bson.BsonReader
import org.bson.BsonType
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class SPXDetailCodec: Codec<SPXDetailEntity> {
    override fun encode(
        writer: BsonWriter,
        value: SPXDetailEntity,
        encoderContext: EncoderContext
    ) {
        writer.writeStartDocument()

        writer.writeString("sub_industry", value.subIndustry)
        writer.writeString("headquarters", value.headquarters)
        writer.writeString("date_added", value.addedDate)
        writer.writeInt32("cik", value.cik ?: -1)
        writer.writeString("founded_date", value.foundedDate)
        writer.writeString("net_worth", value.netWorth)
        writer.writeString("details", value.details)
        writer.writeString("details_tr", value.detailsTr)

        writer.writeEndDocument()
    }

    override fun getEncoderClass(): Class<SPXDetailEntity> = SPXDetailEntity::class.java

    override fun decode(
        reader: BsonReader,
        decoderContext: DecoderContext
    ): SPXDetailEntity {
        var subIndustry: String? = null
        var headquarters: String? = null
        var addedDate: String? = null
        var cik: Int? = null
        var foundedDate: String? = null
        var netWorth: String? = null
        var details: String? = null
        var detailsTr: String? = null

        reader.readStartDocument()

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            when(reader.readName()) {
                "sub_industry" -> subIndustry = reader.readString()
                "headquarters" -> headquarters = reader.readString()
                "date_added" -> addedDate = reader.readString()
                "cik" -> cik = reader.readInt32()
                "founded_date" -> foundedDate = reader.readString()
                "net_worth" -> netWorth = reader.readString()
                "details" -> details = reader.readString()
                "details_tr" -> detailsTr = reader.readString()
                else -> reader.skipValue()
            }
        }

        reader.readEndDocument()

        return SPXDetailEntity(subIndustry, headquarters, addedDate, cik, foundedDate, netWorth, details, detailsTr)
    }
}