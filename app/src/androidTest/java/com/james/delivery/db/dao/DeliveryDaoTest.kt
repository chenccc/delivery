package com.james.delivery.db.dao

import com.google.common.truth.Truth.assertThat

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.james.delivery.DeliveryFactory
import com.james.delivery.db.AppDB
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class DeliveryDaoTest {
    private lateinit var database: AppDB
    private lateinit var dao: DeliveryDao
    private lateinit var deliveryFactory: DeliveryFactory

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDB::class.java
        ).allowMainThreadQueries().build()
        dao = database.deliveryDao()
        deliveryFactory = DeliveryFactory()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAll() = runBlocking {
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
    fun clearAll() = runBlocking {
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