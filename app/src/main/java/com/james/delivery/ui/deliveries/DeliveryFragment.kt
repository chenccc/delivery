package com.james.delivery.ui.deliveries

import androidx.fragment.app.viewModels
import com.james.delivery.R
import com.james.delivery.base.BaseFragment
import com.james.delivery.data.model.Delivery
import com.james.delivery.databinding.FragmentDeliveryBinding
import com.james.delivery.databinding.ItemDeliveryBinding
import com.james.delivery.ui.adapter.DeliveryAdapter
import com.james.delivery.util.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeliveryFragment: BaseFragment<FragmentDeliveryBinding, DeliveryViewModel>(),
    DeliveryAdapter.DeliveryClickListener{
    private val deliveryViewModel: DeliveryViewModel by viewModels()

    @Inject
    lateinit var deliveryAdapter: DeliveryAdapter

    override val layoutId: Int
        get() = R.layout.fragment_delivery

    override fun getVM(): DeliveryViewModel = deliveryViewModel

    override fun bindVM(binding: FragmentDeliveryBinding, viewModel: DeliveryViewModel) =
        with(binding) {
            with(deliveryAdapter) {
                rvDelivery.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }

                rvDelivery.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                swipeRefresh.setOnRefreshListener{ refresh() }
                deliveryClickListener = this@DeliveryFragment

                with(viewModel) {
                    // need to collect data here
                }
            }
        }

    override fun onDeliveryClicked(binding: ItemDeliveryBinding, delivery: Delivery) {
    }
}