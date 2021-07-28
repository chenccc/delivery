package com.james.delivery.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.james.delivery.R
import com.james.delivery.data.model.Delivery

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) return
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("setFromText")
fun TextView.setFromText(string: String?) {
    val result = Constants.FROM_PREFIX + string
    text = result
}

@BindingAdapter("setToText")
fun TextView.setToText(string: String?) {
    val result = Constants.TO_PREFIX + string
    text = result
}

@BindingAdapter("setPrice")
fun TextView.setPrice(delivery: Delivery?) {
    delivery?.let {
        val prefix = it.deliveryFee.first()
        val deliveryFee = it.deliveryFee.run {
            substring(1, length).toDoubleOrNull()
        }
        val surCharge = it.surcharge.run {
            substring(1, length).toDoubleOrNull()
        }

        deliveryFee?.let {
            surCharge?.let {
                text = String.format("$prefix%.2f", deliveryFee + surCharge)
            }
        }
    }
}