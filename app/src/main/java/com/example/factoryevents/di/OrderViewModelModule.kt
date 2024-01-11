package com.example.factoryevents.di

import androidx.lifecycle.ViewModel
import com.example.factoryevents.presentation.ReportOrder.ReportScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OrderViewModelModule {

    @IntoMap
    @ViewModelKey(ReportScreenViewModel::class)
    @Binds
    fun bindReportOrderViewModel(viewModule: ReportScreenViewModel): ViewModel
}