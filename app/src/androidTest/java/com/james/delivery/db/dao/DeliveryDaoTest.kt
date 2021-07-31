package com.james.delivery.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.james.delivery.DeliveryFactory
import com.james.delivery.db.AppDB
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class DeliveryDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDB

    private lateinit var dao: DeliveryDao
    private lateinit var deliveryFactory: DeliveryFactory

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.deliveryDao()
        deliveryFactory = DeliveryFactory()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAll() = runBlockingTest {
        val deliveryList = listOf(
            deliveryFactory.createDelivery(),
            deliveryFactory.createDelivery(),
            deliveryFactory.createDelivery()
        )

        dao.insertAll(deliveryList)
        val allDeliveries = dao.getDeliveries()

        for (item in deliveryList) {
            assertThat(allDeliveries).contains(item)
        }
    }

    @Test
    fun clearAll() = runBlockingTest {
        val deliveryList = listOf(
            deliveryFactory.createDelivery(),
            deliveryFactory.createDelivery(),
            deliveryFactory.createDelivery()
        )

        dao.insertAll(deliveryList)
        dao.clearAll()
        val allDeliveries = dao.getDeliveries()
        assertThat(allDeliveries).isEmpty()
    }
}