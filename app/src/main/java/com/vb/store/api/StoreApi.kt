package com.vb.store.api

import com.vb.store.api.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreApi {

    @GET("/products")
    suspend fun getProducts(@Query("offset")offset: Int, @Query("limit")limit: Int): List<Product>


    @GET("/product")
    suspend fun getProduct(@Query("id")id: Int): Product
}