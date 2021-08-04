package com.james.delivery.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.james.delivery.data.model.Delivery
import com.james.delivery.databinding.ItemDeliveryBinding
import com.james.delivery.ui.deliveries.DeliveryViewModel
import com.james.delivery.util.MyPreference
import javax.inject.Inject

class DeliveryAdapter @Inject constructor(
    private val preference: MyPreference
) :
    PagingDataAdapter<Delivery, DeliveryAdapter.DeliveryViewHolder>(DeliveryComparator){
    var deliveryClickListener: DeliveryClickListener? = null

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder =
        DeliveryViewHolder(
            ItemDeliveryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    inner class DeliveryViewHolder(private val binding: ItemDeliveryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                deliveryClickListener?.onDeliveryClicked(
                    binding,
                    getItem(absoluteAdapterPosition) as Delivery
                )
            }
        }

        fun bind(item: Delivery) = with(binding) {
            delivery = item
            isFav = preference.getBoolean(item.id)
        }
    }

    object DeliveryComparator : DiffUtil.ItemCallback<Delivery>() {
        override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery) =
            oldItem == newItem
    }

    interface DeliveryClickListener {
        fun onDeliveryClicked(binding: ItemDeliveryBinding, delivery: Delivery)
    }
}