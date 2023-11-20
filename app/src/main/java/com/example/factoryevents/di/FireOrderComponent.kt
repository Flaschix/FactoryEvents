package com.example.factoryevents.di

import com.example.factoryevents.presentation.ViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [OrderViewModelModule::class])
interface FireOrderComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory{

        fun create() : FireOrderComponent
    }
}