package com.vb.store.ui.store

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vb.store.api.StoreApi
import com.vb.store.api.model.Product
import com.vb.store.utils.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StoreRepository(private val api: StoreApi) {

    fun getProductDetails(id: Int): Flow<DataState<Product>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = api.getProduct(id)
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }

    fun getProductsPaging(): Flow<PagingData<Product>> {
        return Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = false, prefetchDistance = 1),
            pagingSourceFactory = { StorePagingSource(api) }
        ).flow
    }
}