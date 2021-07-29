package com.james.delivery

import com.james.delivery.data.model.Delivery
import com.james.delivery.data.service.DeliveryApi
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import kotlin.math.min

class MockDeliveryApi: DeliveryApi {
    companion object {
        private const val ERROR_RESPONSE_CODE = 300
        const val PAGE_SIZE = 10
    }

    var failureMsg: String? = null
    private val deliveryList = mutableListOf<Delivery>()

    fun addDeliveryItem(delivery: Delivery) {
        deliveryList.add(delivery)
    }

    fun clearDeliveryList() {
        deliveryList.clear()
    }
    override suspend fun getAllDeliveries(offset: Int, limit: Int): Response<List<Delivery>> {
        failureMsg?.let {
            return Response.error(ERROR_RESPONSE_CODE, "".toResponseBody())
        }

        return Response.success(getSubList(offset, limit))
    }

    private fun getSubList(offset: Int, limit: Int): List<Delivery> {
        if (deliveryList.size < offset) {
            return emptyList()
        }

        return deliveryList.subList(offset, min(deliveryList.size, offset + limit))
    }
}