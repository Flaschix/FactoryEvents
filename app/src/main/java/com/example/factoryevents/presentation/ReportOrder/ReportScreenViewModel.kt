package com.example.factoryevents.presentation.ReportOrder

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factoryevents.domain.entity.Order
import com.example.factoryevents.domain.usecase.CreateOrderUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReportScreenViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler{ _, _ ->
        Log.d("exceptionHandler", "ExceptionHandler catch a error")
    }

    private val orderTest = Order()

    fun createOrder(){
        if (checkFilledOrder()){
            viewModelScope.launch(exceptionHandler) {
                createOrderUseCase(orderTest)
            }
        }
    }

    fun checkFilledOrder(): Boolean{
        return !(orderTest.date.isBlank() ||
                orderTest.time.isBlank() ||
                orderTest.detectionCount.isBlank() ||
                orderTest.howItWasDetected.isBlank() ||
                orderTest.indicateYourManager.isBlank() ||
                orderTest.isItARecurrentIssue.isBlank() ||
                orderTest.whatHappened.isBlank() ||
                orderTest.whoDetectedIt.isBlank() ||
                orderTest.whyIsItProblem.isBlank())
    }

    fun updateWhatHappened(text: String){
        orderTest.whatHappened = text
    }

    fun updateImage(text: Uri){
        orderTest.imgLink = text
    }

    fun updateWhyIsItProblem(text: String){
        orderTest.whyIsItProblem = text
    }

    fun updateTime(text: String){
        orderTest.time = text
    }

    fun updateDate(text: String){
        orderTest.date = text
    }

    fun updateIsItARecurrentIssue(text: String){
        orderTest.isItARecurrentIssue = text
    }

    fun updateWhoDetectedIt(text: String){
        orderTest.whoDetectedIt = text
    }

    fun updateHowItWasDetected(text: String){
        orderTest.howItWasDetected = text
    }

    fun updateDetectionCount(text: String){
        orderTest.detectionCount = text
    }

    fun updateIndicateYourManager(text: String){
        orderTest.indicateYourManager = text
    }
}