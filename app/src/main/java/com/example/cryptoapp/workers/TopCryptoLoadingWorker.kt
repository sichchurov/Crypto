package com.example.cryptoapp.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.CoinDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class TopCryptoLoadingWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val mapper: CoinMapper,
    private val coinDao: CoinDao,
    private val coinApiService: ApiService
) : CoroutineWorker(context, workerParams) {

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

    class Factory @Inject constructor(
        private val mapper: CoinMapper,
        private val coinDao: CoinDao,
        private val coinApiService: ApiService
    ) : ChildWorkerFactory {
        override fun create(context: Context, workerParams: WorkerParameters): ListenableWorker {
            return TopCryptoLoadingWorker(
                context,
                workerParams,
                mapper,
                coinDao,
                coinApiService
            )
        }

    }
}