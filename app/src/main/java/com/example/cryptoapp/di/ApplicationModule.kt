package com.example.cryptoapp.di

import android.content.Context
import androidx.room.Room
import com.example.cryptoapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule() {

    @ApplicationScope
    @Provides
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DB_NAME
    ).build()

    @ApplicationScope
    @Provides
    fun providesDao(db: AppDatabase) = db.coinDao()

}
