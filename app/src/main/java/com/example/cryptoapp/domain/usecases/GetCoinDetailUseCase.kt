package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(fSym: String) = repository.getCoinDetail(fSym)
}