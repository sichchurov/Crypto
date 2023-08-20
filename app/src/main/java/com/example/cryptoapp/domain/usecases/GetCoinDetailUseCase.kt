package com.example.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.entities.Coin
import com.example.cryptoapp.domain.repository.CoinRepository

class GetCoinDetailUseCase(private val repository: CoinRepository) {
    operator fun invoke(fSym: String): LiveData<Coin> {
        return repository.getCoinDetail(fSym)
    }
}