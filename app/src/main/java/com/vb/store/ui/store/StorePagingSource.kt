package com.vb.store.ui.store

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vb.store.api.StoreApi
import com.vb.store.api.model.Product
import java.lang.Exception


private const val STARTING_PAGE_INDEX = 0
private const val PAGE_LIMIT = 10

class StorePagingSource(private val api: StoreApi) : PagingSource<Int, Product>(){

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return  try {
            val response = api.getProducts(position, PAGE_LIMIT)
            val nextKey = if (response.isEmpty() || response.size < PAGE_LIMIT) {
                null
            } else {
                position + 10
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (e : Exception){
            LoadResult.Error(e)
        }

    }
}