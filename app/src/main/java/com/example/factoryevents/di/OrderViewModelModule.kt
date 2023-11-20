package com.example.factoryevents.di

import androidx.lifecycle.ViewModel
import com.example.factoryevents.presentation.FireOrder.FireOrderScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OrderViewModelModule {

    @IntoMap
    @ViewModelKey(FireOrderScreenViewModel::class)
    @Binds
    fun bindFireOrderViewModel(viewModule: FireOrderScreenViewModel): ViewModel
}