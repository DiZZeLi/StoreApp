package com.vb.store.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vb.store.api.model.Product
import com.vb.store.utils.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(private val repository: StoreRepository) : ViewModel() {

    private val _productData = MutableLiveData<DataState<Product>>()
    val productData: LiveData<DataState<Product>>
        get() = _productData

    fun getProductDetails(id: Int) {
        repository.getProductDetails(id).onEach {
            _productData.value = it
        }.launchIn(viewModelScope)
    }

    fun getPopularMovieWithPagination(): Flow<PagingData<Product>> {
        return repository.getProductsPaging().cachedIn(viewModelScope)
    }


}