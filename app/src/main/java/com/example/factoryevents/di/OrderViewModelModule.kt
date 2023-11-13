package com.example.factoryevents.di

import androidx.lifecycle.ViewModel
import com.example.factoryevents.presentation.FireOrder.FireOrderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OrderViewModelModule {

    @IntoMap
    @ViewModelKey(FireOrderViewModel::class)
    @Binds
    fun bindFireOrderViewModel(viewModule: FireOrderViewModel): ViewModel
}