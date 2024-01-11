package com.example.factoryevents.presentation.ReportOrder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factoryevents.domain.entity.Order
import com.example.factoryevents.domain.usecase.CreateOrderUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReportScreenViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler{ _, _ ->
        Log.d("exceptionHandler", "ExceptionHandler catch a error")
    }

    fun createOrder(order: Order){
        viewModelScope.launch(exceptionHandler) {
            createOrderUseCase(order)
        }
    }
}