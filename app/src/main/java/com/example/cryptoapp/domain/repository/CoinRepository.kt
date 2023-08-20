package com.example.cryptoapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.entities.Coin

interface CoinRepository {

    fun getCoinList(): LiveData<List<Coin>>

    fun getCoinDetail(fSym: String): LiveData<Coin>
}