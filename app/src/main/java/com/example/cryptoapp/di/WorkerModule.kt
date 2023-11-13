package com.example.cryptoapp.di

import com.example.cryptoapp.workers.ChildWorkerFactory
import com.example.cryptoapp.workers.TopCryptoLoadingWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @WorkerKey(TopCryptoLoadingWorker::class)
    @IntoMap
    @Binds
    fun coinLoadingWorker(impl: TopCryptoLoadingWorker.Factory): ChildWorkerFactory
}
