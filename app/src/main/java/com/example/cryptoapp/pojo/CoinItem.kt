package com.example.cryptoapp.pojo

import com.google.gson.annotations.SerializedName

data class CoinItem (
    @SerializedName("CoinInfo")
    val coinItemName: CoinItemName? = null
)
