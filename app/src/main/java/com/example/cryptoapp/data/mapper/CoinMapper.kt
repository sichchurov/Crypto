package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinDbModel
import com.example.cryptoapp.data.network.model.CoinDto
import com.example.cryptoapp.data.network.model.CoinJsonDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.entities.Coin
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CoinMapper {

    fun mapDbModelToEntity(coinDbModel: CoinDbModel): Coin {
        return Coin(
            fromSymbol = coinDbModel.fromSymbol,
            toSymbol = coinDbModel.toSymbol,
            price = coinDbModel.price,
            lastUpdate = convertTimestampToTime(coinDbModel.lastUpdate),
            highDay = coinDbModel.highDay,
            lowDay = coinDbModel.lowDay,
            lastMarket = coinDbModel.lastMarket,
            imageUrl = coinDbModel.imageUrl
        )
    }

    fun mapDtoToDbModel(coinDto: CoinDto): CoinDbModel {
        return CoinDbModel(
            fromSymbol = coinDto.fromSymbol,
            toSymbol = coinDto.toSymbol,
            price = coinDto.price,
            lastUpdate = coinDto.lastUpdate,
            highDay = coinDto.highDay,
            lowDay = coinDto.lowDay,
            lastMarket = coinDto.lastMarket,
            imageUrl =  BASE_IMAGE_URL + coinDto.imageUrl
        )
    }

    fun mapCoinNamesToString(namesListDto: CoinNamesListDto):String {
        return namesListDto.names?.map {
            it.coinItemName?.name
        }?.joinToString(",") ?: EMPTY_STRING
    }

    fun mapJsonObjectToListCoin(json: CoinJsonDto): List<CoinDto> {
        val result = mutableListOf<CoinDto>()
        val jsonObject = json.jsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val BASE_IMAGE_URL = "https://cryptocompare.com"

    }
}
