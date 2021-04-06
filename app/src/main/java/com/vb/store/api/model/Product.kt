package com.vb.store.api.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("details") val details: String,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Int,
    @SerializedName("sale_precent") val salePercent: Int,
    @SerializedName("short_description") val description: String,
    @SerializedName("title") val title: String
)