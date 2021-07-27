package com.james.delivery.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.james.delivery.data.model.Delivery
import com.james.delivery.data.paging.DeliveryRemoteMediator
import com.james.delivery.data.service.DeliveryApi
import com.james.delivery.db.AppDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
    private val service: DeliveryApi,
    private val db: AppDB
): DeliveryRepository{

    @ExperimentalPagingApi
    override suspend fun getAllDeliveries(): Flow<PagingData<Delivery>> = Pager(
        config = PagingConfig(pageSize = DeliveryRemoteMediator.PAGE_SIZE, prefetchDistance = 2),
        remoteMediator = DeliveryRemoteMediator(service, db)
    ) {
        db.deliveryDao().pagingSource()
    }.flow

}