package com.james.delivery.data.repository

import androidx.paging.PagingData
import com.james.delivery.DeliveryFactory
import com.james.delivery.data.model.Delivery
import com.james.delivery.data.paging.DeliveryRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDeliveryRepositoryImpl: DeliveryRepository {
    private val deliveryList = mutableListOf<Delivery>()
    private val deliveryFactory = DeliveryFactory()
    override suspend fun getAllDeliveries(): Flow<PagingData<Delivery>> = flow {
        for (i in 0..DeliveryRemoteMediator.PAGE_SIZE) {
            deliveryList.add(deliveryFactory.createDelivery())
        }

        emit(PagingData.from(deliveryList))
    }
}