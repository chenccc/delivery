package com.james.delivery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Route(
    val start: String,
    val end: String
): Parcelable