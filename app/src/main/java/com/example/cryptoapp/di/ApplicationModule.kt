package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinDao
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule() {

    @ApplicationScope
    @Provides
    fun providesDao(
        application: Application
    ): CoinDao {
        return AppDatabase.getInstance(application).coinDao()
    }

}
