package com.example.cryptoapp.data.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CoinJsonDto (
    @SerializedName("RAW")
    val jsonObject: JsonObject? = null
)
