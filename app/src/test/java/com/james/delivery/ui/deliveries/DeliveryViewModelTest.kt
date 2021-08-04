package com.james.delivery.ui.deliveries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.james.delivery.MainCoroutineRule
import com.james.delivery.data.repository.FakeDeliveryRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DeliveryViewModelTest {

    @get:Rule
    var instantRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var deliveryViewModel: DeliveryViewModel
    @Before
    fun setUp() {
        deliveryViewModel = DeliveryViewModel(FakeDeliveryRepositoryImpl())
    }

    @Test
    fun `view model get flow successfully`() {

    }
}