package org.ayaz.finance.data.repositories.spx

import org.ayaz.finance.data.dto_s.spx.SpxDetailResDTO
import org.ayaz.finance.data.dto_s.spx.SpxResDTO
import org.ayaz.finance.data.entities.spx.SPXDetailEntity
import org.ayaz.finance.data.entities.spx.SPXEntity
import org.ayaz.finance.data.uow_s.spx.IGetSpxDataUow
import org.ayaz.finance.data.util.Response
import org.ayaz.finance.domain.mapper.spx.SpxDetailResMapper
import org.ayaz.finance.domain.mapper.spx.SpxResMapper
import org.ayaz.finance.domain.util.Resource

interface ISpxDataRepo {
    fun getData(pageNo: Int, pageSize: Int): Response<List<SpxResDTO>>
    fun getDetailData(symbol: String): Response<SpxDetailResDTO>
}

class SpxDataRepo(
    private val getSpxDataUow: IGetSpxDataUow,
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