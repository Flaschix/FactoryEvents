package com.example.factoryevents.presentation

import android.app.Application
import com.example.factoryevents.di.ApplicationComponent
import com.example.factoryevents.di.DaggerApplicationComponent

class FactoryEventApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}