package com.james.delivery.data.repository

import androidx.paging.PagingData
import com.james.delivery.data.model.Delivery
import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {
    suspend fun getAllDeliveries(): Flow<PagingData<Delivery>>
}