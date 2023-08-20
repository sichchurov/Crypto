package com.example.cryptoapp.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CoinJson (
    @SerializedName("RAW")
    val coinPriceInfoJsonObject: JsonObject? = null
)
