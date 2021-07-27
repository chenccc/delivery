package com.james.delivery.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.james.delivery.data.model.Delivery
import com.james.delivery.data.model.PageKey
import com.james.delivery.db.dao.DeliveryDao
import com.james.delivery.db.dao.PageKeyDao

@Database(entities = [Delivery::class, PageKey::class], version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
    abstract fun pageKeyDao(): PageKeyDao

    companion object {
        @Volatile private var instance: AppDB? = null
        private const val DB_NAME = "deliveryDB"

        fun getDatabase(context: Context, inMemoryUse: Boolean = false): AppDB =
            instance ?: synchronized(this) { instance ?:
                buildDatabase(context, inMemoryUse).also { instance = it } }

        private fun buildDatabase(appContext: Context, inMemoryUse: Boolean) =
            if (inMemoryUse) {
                Room.inMemoryDatabaseBuilder(appContext, AppDB::class.java)
                    .fallbackToDestructiveMigration()
                    .build()
            } else {
                Room.databaseBuilder(appContext, AppDB::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}