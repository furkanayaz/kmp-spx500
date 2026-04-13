package org.ayaz.exchange.domain.mapper.spx

import org.ayaz.exchange.data.dto_s.spx.SpxDetailResDTO
import org.ayaz.exchange.data.dto_s.spx.SpxResDTO
import org.ayaz.exchange.data.entities.spx.SPXDetailEntity
import org.ayaz.exchange.data.entities.spx.SPXEntity
import org.ayaz.exchange.data.repositories.logo.IExchangeLogoRepo
import org.ayaz.exchange.domain.util.Mapper

class SpxResMapper : Mapper<List<SPXEntity>, List<SpxResDTO>> {
    override fun invoke(dto: List<SPXEntity>): List<SpxResDTO> {
        return dto.map { SpxResDTO(it.symbol, it.security, it.sector, null) }
    }
}

class SpxDetailResMapper : Mapper<SPXDetailEntity, SpxDetailResDTO> {
    override fun invoke(dto: SPXDetailEntity): SpxDetailResDTO {
        return SpxDetailResDTO(
            dto.subIndustry,
            dto.headquarters,
            dto.addedDate,
            dto.cik,
            dto.foundedDate,
            dto.netWorth,
            dto.details,
            dto.detailsTr
        )
    }
}