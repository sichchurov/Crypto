package com.example.cryptoapp.di

import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindLocalDataSource(impl: CoinRepositoryImpl): CoinRepository
}
