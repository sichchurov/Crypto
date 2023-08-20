package com.example.cryptoapp.pojo

import com.google.gson.annotations.SerializedName

data class CoinItemList (
    @SerializedName("Data")
    val data: List<CoinItem>? = null
)
