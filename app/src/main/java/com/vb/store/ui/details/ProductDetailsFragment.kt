package com.vb.store.ui.details

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vb.store.R
import com.vb.store.constants.DETAILS_FRAGMENT_PARAM_ID
import com.vb.store.databinding.FragmentProductDetailsBinding
import com.vb.store.ui.store.StoreViewModel
import com.vb.store.utils.loadImage
import com.vb.store.utils.state.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding

    private val viewModel: StoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(DETAILS_FRAGMENT_PARAM_ID)?.let { id ->
            viewModel.getProductDetails(id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)

        viewModel.productData.observe(viewLifecycleOwner, { dataState ->

            when (dataState) {
                is DataState.Success -> {
                    binding?.productDetailsImage?.loadImage(dataState.data.image)
                    binding?.productDetails?.text = dataState.data.details
                    binding?.productDetailsPrice?.text =
                        context?.getString(R.string.product_price, dataState.data.price.toFloat())

                    if (dataState.data.salePercent > 0) {
                        setDiscount(dataState.data.price, dataState.data.salePercent)
                        binding?.productDetailsDiscountTag?.isVisible = true
                    }

                    binding?.productDetailsTitle?.text = dataState.data.title
                }
                is DataState.Error -> {
                }
                is DataState.Loading -> {
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDiscount(price: Int, discount: Int) {

        val priceWithDiscount = price - ((price * discount) / 100.0)

        binding?.productDetailsPrice?.paintFlags
            ?.or(Paint.STRIKE_THRU_TEXT_FLAG)?.let {
                binding?.productDetailsPrice?.setPaintFlags(
                    it
                )
            }

        binding?.productDetailsPriceDiscount?.text =
            context?.getString(R.string.product_price, priceWithDiscount.toFloat())
    }

}