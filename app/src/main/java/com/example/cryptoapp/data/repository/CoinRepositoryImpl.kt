package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.CoinDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.domain.entities.Coin
import com.example.cryptoapp.domain.repository.CoinRepository
import com.example.cryptoapp.workers.TopCryptoLoadingWorker.Companion.WORKER_NAME
import com.example.cryptoapp.workers.TopCryptoLoadingWorker.Companion.makeRequest
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val application: Application,
    private val coinDao: CoinDao,
    private val mapper: CoinMapper
) : CoinRepository {

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
