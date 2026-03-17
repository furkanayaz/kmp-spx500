package org.ayaz.finance.data.uow_s.spx

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import org.ayaz.finance.data.entities.spx.SPXDetailEntity
import org.ayaz.finance.data.entities.spx.SPXEntity
import org.ayaz.finance.data.util.SpxCollection
import org.ayaz.finance.domain.util.Resource

interface IGetSpxDataUow {
    fun getData(): Resource<List<SPXEntity>>
    fun getDetailData(symbol: String): Resource<SPXDetailEntity>
}

class GetSpxDataUow(
    @SpxCollection private val collection: MongoCollection<SPXEntity>
) : IGetSpxDataUow {
    override fun getData(): Resource<List<SPXEntity>> {
        val aggregates = listOf(
            Aggregates.project(
                Projections.include(
                    SPXEntity::symbol.name,
                    SPXEntity::security.name,
                    SPXEntity::sector.name
                )
            )
        )

        val entityList = try {
            collection.aggregate(aggregates).toList()
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(listOf(e.message.orEmpty()))
        }

        return if (entityList.isNotEmpty()) Resource.Success(entityList) else Resource.Error(listOf("spx.data.empty"))
    }

    override fun getDetailData(symbol: String): Resource<SPXDetailEntity> {
        val aggregates = listOf(
            Aggregates.match(Filters.eq(SPXEntity::symbol.name, symbol)),
            Aggregates.project(
                Projections.include(SPXEntity::details.name)
            )
        )

        val entity = try {
            collection.aggregate(aggregates).firstOrNull()?.details
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(listOf(e.message.orEmpty()))
        }

        return if (entity != null) Resource.Success(entity) else Resource.Error(listOf("spx.data.detail.empty"))
    }
}