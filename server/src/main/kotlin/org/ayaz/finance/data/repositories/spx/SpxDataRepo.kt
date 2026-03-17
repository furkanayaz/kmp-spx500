package org.ayaz.finance.data.repositories.spx

import org.ayaz.finance.data.dto_s.spx.SpxDetailResDTO
import org.ayaz.finance.data.dto_s.spx.SpxResDTO
import org.ayaz.finance.data.entities.spx.SpxEntity
import org.ayaz.finance.data.uow_s.spx.IGetSpxDataUow
import org.ayaz.finance.data.util.Response
import org.ayaz.finance.domain.mapper.spx.SpxDetailResMapper
import org.ayaz.finance.domain.mapper.spx.SpxResMapper
import org.ayaz.finance.domain.util.Resource

interface ISpxDataRepo {
    fun getData(pageNo: Int, pageSize: Int): Response<List<SpxResDTO>>
    fun getDetailData(id: String): Response<SpxDetailResDTO>
}

class SpxDataRepo(
    private val getSpxDataUow: IGetSpxDataUow,
    private val spxResMapper: SpxResMapper,
    private val spxDetailResMapper: SpxDetailResMapper
): ISpxDataRepo {
    override fun getData(pageNo: Int, pageSize: Int): Response<List<SpxResDTO>> {
        return when(val response = getSpxDataUow.getData()) {
            is Resource.Error<List<SpxEntity>> -> Response.Error(errorMessages = response.messages)
            is Resource.Success<List<SpxEntity>> -> {
                val fromIndex = pageNo * pageSize
                val toIndex = (pageNo + 1) * pageSize

                val spxList = spxResMapper(response.item).subList(fromIndex, toIndex)

                Response.Success(item = spxList)
            }
        }
    }

    override fun getDetailData(id: String): Response<SpxDetailResDTO> {
        return when(val response = getSpxDataUow.getDetailData(id)) {
            is Resource.Error<SpxEntity> -> Response.Error(errorMessages = response.messages)
            is Resource.Success<SpxEntity> -> Response.Success(item = spxDetailResMapper(response.item))
        }
    }

}