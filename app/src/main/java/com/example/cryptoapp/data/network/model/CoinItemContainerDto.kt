package com.example.cryptoapp.data.network.model

import com.google.gson.annotations.SerializedName

data class CoinItemContainerDto (
    @SerializedName("CoinInfo")
    val coinItemName: CoinItemNameDto? = null
)
