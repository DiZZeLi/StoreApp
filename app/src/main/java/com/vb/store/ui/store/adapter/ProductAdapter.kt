package com.vb.store.ui.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vb.store.R
import com.vb.store.api.model.Product
import com.vb.store.constants.DETAILS_FRAGMENT_PARAM_ID
import com.vb.store.databinding.RecyclerItemProductBinding
import com.vb.store.utils.loadImage

class ProductAdapter : PagingDataAdapter<Product, ProductAdapter.ViewHolder>(PRODUCT_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }


    inner class ViewHolder(private val binding: RecyclerItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {

            binding.productImage.loadImage(product.image)

            binding.productTitle.text = product.title
            binding.productDescription.text = product.description
            binding.productPrice.text =
                binding.productDescription.context.getString(R.string.product_price, product.price.toFloat())
        }

               init {
                   binding.root.setOnClickListener {
                       val data = getItem(bindingAdapterPosition)

                       val bundle = bundleOf(
                           DETAILS_FRAGMENT_PARAM_ID to data?.id
                       )

                       binding.root.findNavController().navigate(R.id.productDetailsFragment, bundle)
                   }
               }
    }


    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

}