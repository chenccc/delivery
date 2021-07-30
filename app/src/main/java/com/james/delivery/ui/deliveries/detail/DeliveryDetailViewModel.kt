package com.james.delivery.ui.deliveries.detail

import androidx.lifecycle.LiveData
import com.james.delivery.base.BaseViewModel
import com.james.delivery.util.MyPreference
import com.james.delivery.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeliveryDetailViewModel @Inject constructor(
    private val preference: MyPreference
) : BaseViewModel(){
    companion object{
        private val TAG = DeliveryDetailViewModel::class.simpleName
    }

    private val _backEvent = SingleLiveEvent<Unit>()
    val backEvent: LiveData<Unit> = _backEvent

    fun getFavStatus(id: String): Boolean =
        preference.getBoolean(id)

    fun updateDeliveryFav(id: String, fav: Boolean) {
        preference.setBoolean(id, fav)
        _backEvent.value = Unit
    }

    fun backToList() {
        _backEvent.value = Unit
    }
}