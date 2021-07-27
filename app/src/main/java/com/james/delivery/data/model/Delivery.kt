package com.james.delivery.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "delivery")
data class Delivery (
    @PrimaryKey
    val id: String,
    val remarks: String,
    val pickupTime: String,
    val goodsPicture: String,
    val deliveryFee: String,
    val surcharge: String,

    @Embedded
    val route: Route,

    @Embedded
    val sender: Sender,
    var page: Int?
): Parcelable