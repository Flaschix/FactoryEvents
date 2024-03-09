package com.example.factoryevents.presentation.HSE

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.factoryevents.domain.usecase.GetWorkerHSEListUseCase
import com.example.factoryevents.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HseScreenViewModel @Inject constructor(
    private val getHSEListUseCase: GetWorkerHSEListUseCase,
): ViewModel() {

    private val hseListFlow = getHSEListUseCase()

//    private val loadDataEvents = MutableSharedFlow<Unit>()
//
//    private val loadDataFlow = flow {
//        loadDataEvents.collect{
//            emit(HseScreenState.HSE_List(hseListFlow.value))
//        }
//    }

    val screenState = hseListFlow
        .catch {  }
        .filter { it.isNotEmpty() }
        .map { HseScreenState.HSE_List(it) as HseScreenState }
        .onStart { emit(HseScreenState.Loading) }
//        .mergeWith(loadDataFlow)

    fun refresh(){
        Log.d("REFRESH_TEST", "refresh: TESTIM")
    }
}