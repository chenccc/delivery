package com.james.delivery.ui.deliveries

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.james.delivery.R
import com.james.delivery.base.BaseFragment
import com.james.delivery.data.model.Delivery
import com.james.delivery.databinding.FragmentDeliveryBinding
import com.james.delivery.databinding.ItemDeliveryBinding
import com.james.delivery.ui.adapter.DeliveryAdapter
import com.james.delivery.util.PagingLoadStateAdapter
import com.james.delivery.util.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
                    addItemDecoration(SpaceItemDecoration(15))
                }

                rvDelivery.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                swipeRefresh.setOnRefreshListener{ refresh() }
                deliveryClickListener = this@DeliveryFragment

                with(viewModel) {
                    launchOnLifecycleScope {
                        deliveryFlow.collectLatest { submitData(it) }
                    }
                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                        }
                    }
                }
            }
        }

    override fun onDeliveryClicked(binding: ItemDeliveryBinding, delivery: Delivery) {
        val action = DeliveryFragmentDirections.
            actionDeliveryFragmentToDeliveryDetailFragment(delivery)
        findNavController().navigate(action)
    }
}