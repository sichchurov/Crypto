package com.example.cryptoapp.presentation

import android.app.Application
import com.example.cryptoapp.di.DaggerApplicationComponent
import com.example.cryptoapp.workers.CoinWorkerFactory
import javax.inject.Inject

class CoinApplication : Application(), androidx.work.Configuration.Provider {

    @Inject
    lateinit var coinWorkerFactory: CoinWorkerFactory

    val appComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this
        )
    }

    override fun onCreate() {
        appComponent.inject(this)

        super.onCreate()

    }

    override fun getWorkManagerConfiguration(): androidx.work.Configuration {
        return androidx.work.Configuration.Builder()
            .setWorkerFactory(coinWorkerFactory)
            .build()
    }
}
