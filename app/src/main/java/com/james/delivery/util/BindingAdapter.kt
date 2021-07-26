package com.james.delivery.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.james.delivery.data.model.Delivery

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) return
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("setFromText")
fun TextView.setFromText(string: String?) {
    val result = Constants.FROM_PREFIX
    string?.let {
        result.plus(it)
    }
    text = result
}

@BindingAdapter("setToText")
fun TextView.setToText(string: String?) {
    val result = Constants.TO_PREFIX
    string?.let {
        result.plus(it)
    }
    text = result
}

@BindingAdapter("setPrice")
fun TextView.setPrice(delivery: Delivery) {
    val prefix = delivery.deliveryFee.first()
    val deliveryFee = delivery.deliveryFee.toDoubleOrNull()
    val surCharge = delivery.surcharge.toDoubleOrNull()

    deliveryFee?.let { fee ->
        surCharge?.let { charge ->
            text = String.format("$prefix%.2f", fee + charge)
        }
    }
}