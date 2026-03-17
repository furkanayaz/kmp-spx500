package org.ayaz.finance.domain.mapper.spx

import org.ayaz.finance.data.dto_s.spx.SpxDetailResDTO
import org.ayaz.finance.data.dto_s.spx.SpxResDTO
import org.ayaz.finance.data.entities.spx.SpxEntity
import org.ayaz.finance.domain.util.Mapper

class SpxResMapper: Mapper<List<SpxEntity>, List<SpxResDTO>> {
    override fun invoke(dto: List<SpxEntity>): List<SpxResDTO> {
        return dto.map { SpxResDTO(it.symbol, it.security, it.sector) }
    }
}

class SpxDetailResMapper: Mapper<SpxEntity, SpxDetailResDTO> {
    override fun invoke(dto: SpxEntity): SpxDetailResDTO {
        return SpxDetailResDTO(dto.subIndustry, dto.headquarters, dto.addedDate, dto.cik, dto.foundedDate, dto.netWorth, dto.details, dto.detailsTr)
    }
}