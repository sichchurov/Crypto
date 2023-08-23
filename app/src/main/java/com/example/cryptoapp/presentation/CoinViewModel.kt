package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetCoinDetailUseCase
import com.example.cryptoapp.domain.usecases.GetCoinListUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repositoryImpl = CoinRepositoryImpl(application)
    private val getCoinListUseCase = GetCoinListUseCase(repositoryImpl)
    private val getCoinDetailUseCase = GetCoinDetailUseCase(repositoryImpl)
    private val loadDataUseCase = LoadDataUseCase(repositoryImpl)

    val coinList = getCoinListUseCase()

    fun getDetailInfo(fSym: String) = getCoinDetailUseCase(fSym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}