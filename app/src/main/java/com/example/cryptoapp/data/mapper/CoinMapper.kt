package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinDbModel
import com.example.cryptoapp.data.network.model.CoinDto
import com.example.cryptoapp.data.network.model.CoinJsonDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.entities.Coin
import com.google.gson.Gson

class CoinMapper {

    fun mapDbModelToEntity(coinDbModel: CoinDbModel): Coin {
        return Coin(
            fromSymbol = coinDbModel.fromSymbol,
            toSymbol = coinDbModel.toSymbol,
            price = coinDbModel.price,
            lastUpdate = coinDbModel.lastUpdate,
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
            imageUrl = coinDto.imageUrl
        )
    }

    fun mapCoinNamesToString(namesListDto: CoinNamesListDto):String {
        return namesListDto.names?.map {
            it.coinItemName?.name
        }?.joinToString(", ") ?: EMPTY_STRING
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

    companion object {
        private const val EMPTY_STRING = ""
    }
}
