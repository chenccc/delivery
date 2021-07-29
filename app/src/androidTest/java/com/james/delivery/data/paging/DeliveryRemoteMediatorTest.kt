package com.james.delivery.data.paging

import androidx.paging.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.james.delivery.DeliveryFactory
import com.james.delivery.MockDeliveryApi
import com.james.delivery.data.model.Delivery
import com.james.delivery.db.AppDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DeliveryRemoteMediatorTest {
    private lateinit var deliveryFactory: DeliveryFactory
    private lateinit var mockDeliveryApi: MockDeliveryApi
    private lateinit var mockDb: AppDB

    @Before
    fun setUp() {
        deliveryFactory = DeliveryFactory()
        mockDeliveryApi = MockDeliveryApi()
        mockDb = AppDB.getDatabase(
            ApplicationProvider.getApplicationContext(),
            inMemoryUse = true
        )
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
        mockDeliveryApi.failureMsg = null
        mockDeliveryApi.clearDeliveryList()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        with(mockDeliveryApi) {
            for (i in 0..MockDeliveryApi.PAGE_SIZE*4) {
                addDeliveryItem(deliveryFactory.createDelivery())
            }
        }

        val remoteMediator = DeliveryRemoteMediator(
            db = mockDb,
            service = mockDeliveryApi
        )

        val pagingState = PagingState<Int, Delivery>(
            listOf(),
            null,
            PagingConfig(MockDeliveryApi.PAGE_SIZE),
            MockDeliveryApi.PAGE_SIZE
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        val remoteMediator = DeliveryRemoteMediator(
            db = mockDb,
            service = mockDeliveryApi
        )

        val pagingState = PagingState<Int, Delivery>(
            listOf(),
            null,
            PagingConfig(MockDeliveryApi.PAGE_SIZE),
            MockDeliveryApi.PAGE_SIZE
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        mockDeliveryApi.failureMsg = "Can not retrieve data"

        val remoteMediator = DeliveryRemoteMediator(
            db = mockDb,
            service = mockDeliveryApi
        )

        val pagingState = PagingState<Int, Delivery>(
            listOf(),
            null,
            PagingConfig(MockDeliveryApi.PAGE_SIZE),
            MockDeliveryApi.PAGE_SIZE
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

}