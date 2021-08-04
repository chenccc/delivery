package com.james.delivery.ui.deliveries

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.james.delivery.base.BaseViewModel
import com.james.delivery.data.model.Delivery
import com.james.delivery.data.repository.DeliveryRepository
import com.james.delivery.util.MyPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository
): BaseViewModel() {
    private lateinit var _deliveryFlow: Flow<PagingData<Delivery>>
    val deliveryFlow: Flow<PagingData<Delivery>>
        get() = _deliveryFlow

    init {
        getAllDelivery()
    }

    private fun getAllDelivery() = launchPagingAsync({
        deliveryRepository.getAllDeliveries().cachedIn(viewModelScope)
    }, {
        _deliveryFlow = it
    })
}