package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.repository.CoinRepository

class GetCoinDetailUseCase(private val repository: CoinRepository) {
    operator fun invoke(fSym: String) = repository.getCoinDetail(fSym)
}