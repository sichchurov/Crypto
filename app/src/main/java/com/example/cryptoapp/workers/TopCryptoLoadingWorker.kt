package com.example.cryptoapp.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import kotlinx.coroutines.delay

class TopCryptoLoadingWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val mapper: CoinMapper = CoinMapper()
    private val coinDao = AppDatabase.getInstance(context).coinPriceInfoDao()
    private val coinApiService = ApiFactory.apiService
    override suspend fun doWork(): Result {
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

    companion object {

        const val WORKER_NAME = "TopCryptoLoadingWorker"

        private val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
        fun makeRequest() = OneTimeWorkRequestBuilder<TopCryptoLoadingWorker>()
            .setConstraints(constraints)
            .build()

    }
}