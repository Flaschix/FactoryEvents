package com.example.factoryevents.di

import android.content.Context
import com.example.factoryevents.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun getFireOrderComponentFactory(): FireOrderComponent.Factory

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}