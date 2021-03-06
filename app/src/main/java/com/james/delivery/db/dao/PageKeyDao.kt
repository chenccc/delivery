package com.james.delivery.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.james.delivery.data.model.PageKey

@Dao
interface PageKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(pageKey: PageKey)

    @Query("SELECT * FROM pageKey")
    fun getAllKeys(): List<PageKey>

    @Query("DELETE FROM pageKey")
    suspend fun clearAll()
}