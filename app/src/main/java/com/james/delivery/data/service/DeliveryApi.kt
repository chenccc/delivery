package com.james.delivery.data.service

import com.james.delivery.data.model.Delivery
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// test api https://mock-api-mobile.dev.lalamove.com/v2/deliveries/?offset=1&limit=5
interface DeliveryApi {
    @GET("deliveries/")
    suspend fun getAllDeliveries(@Query("offset") offset: Int,
        @Query("limit") limit: Int): Response<List<Delivery>>
}