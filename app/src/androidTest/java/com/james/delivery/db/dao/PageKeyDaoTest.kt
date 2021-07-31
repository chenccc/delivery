package com.james.delivery.db.dao
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.james.delivery.data.model.PageKey
import com.james.delivery.db.AppDB
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class PageKeyDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDB
    private lateinit var dao: PageKeyDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.pageKeyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertOrReplace() = runBlockingTest {
        val pageKey = PageKey("123", 2)
        dao.insertOrReplace(pageKey)
        val keys = dao.getAllKeys()

        assertThat(keys).contains(pageKey)
    }

    @Test
    fun clearAll() = runBlockingTest {
        val pageKey = PageKey("123", 2)
        dao.insertOrReplace(pageKey)
        dao.clearAll()
        val keys = dao.getAllKeys()
        assertThat(keys).isEmpty()
    }
}