package com.james.delivery.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.james.delivery.data.model.Delivery

@Dao
interface DeliveryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(deliveries: List<Delivery>)

    @Query("SELECT * FROM delivery")
    fun pagingSource(): PagingSource<Int, Delivery>

    @Query("DELETE FROM delivery")
    suspend fun clearAll()

    @Query("SELECT * FROM delivery")
    suspend fun getDeliveries(): List<Delivery>
}