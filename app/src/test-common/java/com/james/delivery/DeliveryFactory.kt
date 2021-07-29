package com.james.delivery

import com.james.delivery.data.model.Delivery
import com.james.delivery.data.model.Route
import com.james.delivery.data.model.Sender
import java.util.concurrent.atomic.AtomicInteger

class DeliveryFactory {
    private val counter = AtomicInteger(0)

    fun createDelivery(): Delivery {
        val id = counter.incrementAndGet().toString()

        return Delivery(
            id = id,
            remarks = "",
            pickupTime = "",
            goodsPicture = "",
            deliveryFee = "$100.01",
            surcharge = "$110.09",
            route = Route(start = "", end = ""),
            sender = Sender(phone = "", name = "", email = ""),
            page = null
        )
    }
}