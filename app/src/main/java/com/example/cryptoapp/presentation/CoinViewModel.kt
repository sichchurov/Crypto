package com.example.cryptoapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.domain.entities.Coin
import com.example.cryptoapp.domain.usecases.GetCoinDetailUseCase
import com.example.cryptoapp.domain.usecases.GetCoinListUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    getCoinListUseCase: GetCoinListUseCase,
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val coinList = getCoinListUseCase()

    fun getDetailInfo(fSym: String): LiveData<Coin> {
        return getCoinDetailUseCase(fSym)
    }

    init {
        loadDataUseCase()
    }
}