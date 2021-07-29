package com.james.delivery.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.james.delivery.data.model.Delivery
import com.james.delivery.data.model.PageKey
import com.james.delivery.data.service.DeliveryApi
import com.james.delivery.db.AppDB

@OptIn(ExperimentalPagingApi::class)
class DeliveryRemoteMediator(private val service: DeliveryApi, private val db: AppDB) :
    RemoteMediator<Int, Delivery>() {

    companion object {
        const val PAGE_SIZE = 10
        val TAG = DeliveryRemoteMediator::class.simpleName
    }

    private val deliveryDao = db.deliveryDao()
    private val keyDao = db.pageKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Delivery>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    Log.d(TAG, "come to append")
                    // depend on db, instead of page state
                    val remoteKey = db.withTransaction {
                        keyDao.getAllKeys().lastOrNull()
                    }

                    if (remoteKey?.nextPage == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextPage
                }
            }

            val page = loadKey ?: 1
            val response = service.getAllDeliveries(1 + (page - 1) * PAGE_SIZE ,
                page * PAGE_SIZE)
            val deliveries = response.body()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    deliveryDao.clearAll()
                    keyDao.clearAll()
                }
                deliveries?.forEach {
                    it.page = loadKey
                    keyDao.insertOrReplace(PageKey(it.id, loadKey + 1))
                }
                deliveries?.let { deliveryDao.insertAll(it) }

            }

            MediatorResult.Success(
                endOfPaginationReached = deliveries.isNullOrEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}