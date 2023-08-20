package com.example.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.entities.Coin
import com.example.cryptoapp.domain.repository.CoinRepository

class GetCoinListUseCase(private val repository: CoinRepository) {

    operator fun invoke(): LiveData<List<Coin>> {
        return repository.getCoinList()
    }
}