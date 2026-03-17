package org.ayaz.finance.data.uow_s.spx

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Projections
import org.ayaz.finance.data.entities.spx.SpxEntity
import org.ayaz.finance.domain.util.Resource
import org.bson.types.ObjectId
import org.litote.kmongo.findOneById

interface IGetSpxDataUow {
    fun getData(): Resource<List<SpxEntity>>
    fun getDetailData(id: String): Resource<SpxEntity>
}

class GetSpxDataUow(
    private val collection: MongoCollection<SpxEntity>
) : IGetSpxDataUow {
    override fun getData(): Resource<List<SpxEntity>> {
        val aggregates = listOf(
            Aggregates.project(
                Projections.include(
                    SpxEntity::symbol.name,
                    SpxEntity::security.name,
                    SpxEntity::sector.name
                )
            )
        )

        val entityList = collection.aggregate(aggregates).toList()

        return if (entityList.isNotEmpty()) Resource.Success(entityList) else Resource.Error(listOf("spx.data.empty"))
    }

    override fun getDetailData(id: String): Resource<SpxEntity> {
        val entity = collection.findOneById(ObjectId(id))
        return if (entity != null) Resource.Success(entity) else Resource.Error(listOf("spx.data.detail.empty"))
    }
}