package com.james.delivery.ui.deliveries.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.james.delivery.R
import com.james.delivery.base.BaseFragment
import com.james.delivery.databinding.FragmentDeliveryDetailBinding
import com.james.delivery.ext.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveryDetailFragment:
    BaseFragment<FragmentDeliveryDetailBinding, DeliveryDetailViewModel>(){
    private val deliveryDetailViewModel: DeliveryDetailViewModel by viewModels()
    private val args: DeliveryDetailFragmentArgs by navArgs()

    override val layoutId: Int
        get() = R.layout.fragment_delivery_detail

    override fun getVM(): DeliveryDetailViewModel = deliveryDetailViewModel

    override fun bindVM(
        binding: FragmentDeliveryDetailBinding,
        vm: DeliveryDetailViewModel
    ) {
        with(binding) {
            delivery = args.delivery
            viewModel = vm
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getVM()) {
            observe(backEvent) {
                findNavController().navigateUp()
            }
        }
    }
}