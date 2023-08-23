package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.entities.Coin
import com.example.cryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(
    application: Application,
) : CoinRepository {

    private val mapper: CoinMapper = CoinMapper()
    private val coinDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val coinApiService = ApiFactory.apiService
    override fun getCoinList(): LiveData<List<Coin>> {
        return coinDao.getCoinList().map { dbModelList ->
            dbModelList.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinDetail(fSym: String): LiveData<Coin> {
        return coinDao.getInfoAboutCoin(fSym).map {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoinItemsList = coinApiService.getTopCoinsInfo(limit = 50)
                val topCoinItemsString = mapper.mapCoinNamesToString(topCoinItemsList)
                val jsonListContainer = coinApiService.getFullPriceList(fSyms = topCoinItemsString)
                val coinDtoList = mapper.mapJsonObjectToListCoin(jsonListContainer)
                val dbModelList = coinDtoList.map { mapper.mapDtoToDbModel(it) }
                coinDao.insertCoinList(dbModelList)
            } catch (_: Exception) {

            }
            delay(10000)
        }
    }
}