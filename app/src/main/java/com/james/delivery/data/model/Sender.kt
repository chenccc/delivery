package com.james.delivery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sender(
    val phone: String,
    val name: String,
    val email: String
): Parcelable
