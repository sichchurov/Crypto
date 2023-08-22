package com.example.cryptoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinDbModel
import com.example.cryptoapp.data.network.model.CoinJsonDto
import com.example.cryptoapp.data.network.ApiFactory
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getCoinList()

    fun getDetailInfo(fSym: String): LiveData<CoinDbModel> {
        return db.coinPriceInfoDao().getInfoAboutCoin(fSym)
    }

    init {
        loadData()
    }



    override fun onCleared() {
        super.onCleared()
    }
}