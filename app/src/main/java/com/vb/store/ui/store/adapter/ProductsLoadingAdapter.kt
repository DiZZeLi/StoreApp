package com.vb.store.ui.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vb.store.R
import com.vb.store.databinding.RecyclerFooterLayoutBinding

class ProductsLoadingAdapter(private val retry: () -> Unit) :LoadStateAdapter<ProductsLoadingAdapter.MoviesLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: MoviesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MoviesLoadStateViewHolder {
        return MoviesLoadStateViewHolder.create(parent, retry)
    }


    class MoviesLoadStateViewHolder(private val binding: RecyclerFooterLayoutBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): MoviesLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_footer_layout, parent, false)
                val binding = RecyclerFooterLayoutBinding.bind(view)
                return MoviesLoadStateViewHolder(binding, retry)
            }
        }
    }


}