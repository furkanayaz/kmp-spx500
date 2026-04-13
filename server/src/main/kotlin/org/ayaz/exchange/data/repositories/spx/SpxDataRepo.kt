package org.ayaz.exchange.data.repositories.spx

import org.ayaz.exchange.data.dto_s.spx.SpxDetailResDTO
import org.ayaz.exchange.data.dto_s.spx.SpxResDTO
import org.ayaz.exchange.data.entities.spx.SPXDetailEntity
import org.ayaz.exchange.data.entities.spx.SPXEntity
import org.ayaz.exchange.data.uow_s.spx.ISpxDataUow
import org.ayaz.exchange.data.base.Response
import org.ayaz.exchange.domain.mapper.spx.SpxDetailResMapper
import org.ayaz.exchange.domain.mapper.spx.SpxResMapper
import org.ayaz.exchange.domain.base.Resource

interface ISpxDataRepo {
    fun getData(pageNo: Int, pageSize: Int): Response<List<SpxResDTO>>
    fun getDetailData(symbol: String): Response<SpxDetailResDTO>
}

class SpxDataRepo(
    private val getSpxDataUow: ISpxDataUow,
    private val spxResMapper: SpxResMapper,
    private val spxDetailResMapper: SpxDetailResMapper
): ISpxDataRepo {
    override fun getData(pageNo: Int, pageSize: Int): Response<List<SpxResDTO>> {
        return when(val response = getSpxDataUow.getData()) {
            is Resource.Error<List<SPXEntity>> -> Response.Error(errorMessages = response.messages)
            is Resource.Success<List<SPXEntity>> -> {
                val newPageNo = if (pageNo < 0) 0 else pageNo
                val newPageSize = if (pageSize < 0) 10 else pageSize

                val spxLastIndex = response.item.size

                val tempFromIndex = newPageNo * newPageSize
                val fromIndex = if (tempFromIndex > spxLastIndex) spxLastIndex else tempFromIndex

                val tempToIndex = (newPageNo + 1) * newPageSize
                val toIndex = if (tempToIndex > spxLastIndex) spxLastIndex else tempToIndex

                val spxList = spxResMapper(response.item).subList(fromIndex, toIndex)

                Response.Success(item = spxList)
            }
        }
    }

    override fun getDetailData(symbol: String): Response<SpxDetailResDTO> {
        return when(val response = getSpxDataUow.getDetailData(symbol)) {
            is Resource.Error<SPXDetailEntity> -> Response.Error(errorMessages = response.messages)
            is Resource.Success<SPXDetailEntity> -> {
                Response.Success(item = spxDetailResMapper(response.item))
            }
        }
    }

}