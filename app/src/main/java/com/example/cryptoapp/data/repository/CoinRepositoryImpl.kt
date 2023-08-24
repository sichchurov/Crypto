package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.domain.entities.Coin
import com.example.cryptoapp.domain.repository.CoinRepository
import com.example.cryptoapp.workers.TopCryptoLoadingWorker.Companion.WORKER_NAME
import com.example.cryptoapp.workers.TopCryptoLoadingWorker.Companion.makeRequest

class CoinRepositoryImpl(
    private val application: Application,
) : CoinRepository {

    private val mapper: CoinMapper = CoinMapper()
    private val coinDao = AppDatabase.getInstance(application).coinPriceInfoDao()

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

    override fun loadData() {

        WorkManager.getInstance(application)
            .enqueueUniqueWork(
                WORKER_NAME,
                ExistingWorkPolicy.REPLACE,
                makeRequest()
            )
    }
}