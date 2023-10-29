package com.example.factoryevents.di

import android.content.Context
import com.example.factoryevents.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}