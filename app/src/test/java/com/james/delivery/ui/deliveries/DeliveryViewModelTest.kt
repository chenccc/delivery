package com.james.delivery.ui.deliveries

import com.james.delivery.data.repository.FakeDeliveryRepositoryImpl
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class DeliveryViewModelTest {
    private lateinit var deliveryViewModel: DeliveryViewModel
    @Before
    fun setUp() {
        deliveryViewModel = DeliveryViewModel(FakeDeliveryRepositoryImpl())
    }
}