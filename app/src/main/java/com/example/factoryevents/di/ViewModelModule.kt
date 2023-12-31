package com.example.factoryevents.di

import androidx.lifecycle.ViewModel
import com.example.factoryevents.presentation.HSE.HseScreenViewModel
import com.example.factoryevents.presentation.OJT.OjtScreenViewModel
import com.example.factoryevents.presentation.Order.OrderScreenViewModel
import com.example.factoryevents.presentation.main.AccessViewModel
import com.example.factoryevents.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(HseScreenViewModel::class)
    @Binds
    fun bindHseScreenViewModel(viewModel: HseScreenViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AccessViewModel::class)
    @Binds
    fun bindAccessViewModel(viewModel: AccessViewModel): ViewModel

    @IntoMap
    @ViewModelKey(OjtScreenViewModel::class)
    @Binds
    fun bindOjtScreenViewModel(viewModel: OjtScreenViewModel): ViewModel

    @IntoMap
    @ViewModelKey(OrderScreenViewModel::class)
    @Binds
    fun bindOrderScreenViewModel(viewModel: OrderScreenViewModel): ViewModel
}