package org.ayaz.exchange.data.uow_s.spx

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import org.ayaz.exchange.data.entities.spx.SPXDetailEntity
import org.ayaz.exchange.data.entities.spx.SPXEntity
import org.ayaz.exchange.data.util.SpxCollection
import org.ayaz.exchange.domain.base.Resource

interface ISpxDataUow {
    fun getData(): Resource<List<SPXEntity>>
    fun getDetailData(symbol: String): Resource<SPXDetailEntity>
}

class SpxDataUow(
    @SpxCollection private val collection: MongoCollection<SPXEntity>
) : ISpxDataUow {
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
            return Resource.Error(messages = listOf(e.message.orEmpty()))
        }

        return if (entityList.isNotEmpty()) Resource.Success(entityList) else Resource.Error(messages = listOf("spx.data.empty"))
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
            return Resource.Error(messages = listOf(e.message.orEmpty()))
        }

        return if (entity != null) Resource.Success(entity) else Resource.Error(messages = listOf("spx.data.detail.empty"))
    }
}