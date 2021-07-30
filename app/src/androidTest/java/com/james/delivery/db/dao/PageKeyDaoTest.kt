package com.james.delivery.db.dao
import com.google.common.truth.Truth.assertThat
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.james.delivery.data.model.PageKey
import com.james.delivery.db.AppDB
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PageKeyDaoTest {
    private lateinit var database: AppDB
    private lateinit var dao: PageKeyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDB::class.java
        ).allowMainThreadQueries().build()
        dao = database.pageKeyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertOrReplace() = runBlocking {
        val pageKey = PageKey("123", 2)
        dao.insertOrReplace(pageKey)
        val keys = dao.getAllKeys()

        assertThat(keys).contains(pageKey)
    }

    @Test
    fun clearAll() = runBlocking {
        val pageKey = PageKey("123", 2)
        dao.insertOrReplace(pageKey)
        dao.clearAll()
        val keys = dao.getAllKeys()
        assertThat(keys).isEmpty()
    }
}