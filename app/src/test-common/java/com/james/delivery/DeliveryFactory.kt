package com.james.delivery

import com.james.delivery.data.model.Delivery
import com.james.delivery.data.model.Route
import com.james.delivery.data.model.Sender
import java.util.concurrent.atomic.AtomicInteger

class DeliveryFactory {
    private val counter = AtomicInteger(0)

    fun getDefaultDelivery(): Delivery = Delivery(
        id = "5dd5f3a787c49789dca0b43f",
        remarks = "Minim deserunt nisi qui veniam est amet pariatur voluptate ea est exercitation cupidatat sit ea.",
        pickupTime= "2018-11-22T07:06:05-08:00",
        goodsPicture = "https://loremflickr.com/320/240/cat?lock=28542",
        deliveryFee= "$104.23",
        surcharge = "$288.13",
        route = Route(start = "Henry Street", end = "Clinton Street"),
        sender = Sender(phone = "+1 (942) 512-3379", name = "Kendra Guthrie", email = "kendraguthrie@comdom.com"),
        page = 1
    )

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