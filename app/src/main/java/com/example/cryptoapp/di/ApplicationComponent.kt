package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.presentation.CoinApplication
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, AppSubcomponentModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun inject(application: CoinApplication)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

    fun activityComponent(): ActivityComponent.Factory
}
