package com.james.delivery.data.repository

import androidx.paging.PagingData
import com.james.delivery.DeliveryFactory
import com.james.delivery.data.model.Delivery
import com.james.delivery.data.paging.DeliveryRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDeliveryRepositoryImpl: DeliveryRepository {
    private val _deliveryList = mutableListOf<Delivery>()
    val deliveryList: List<Delivery> = _deliveryList
    private val deliveryFactory = DeliveryFactory()
    override suspend fun getAllDeliveries(): Flow<PagingData<Delivery>> = flow {
        for (i in 1..DeliveryRemoteMediator.PAGE_SIZE) {
            _deliveryList.add(deliveryFactory.createDelivery())
        }

        emit(PagingData.from(_deliveryList))
    }
}