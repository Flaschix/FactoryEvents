package com.example.factoryevents.presentation.HSE

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factoryevents.domain.usecase.GetWorkerHSEListUseCase
import com.example.factoryevents.domain.usecase.RefreshHSEListUseCase
import com.example.factoryevents.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HseScreenViewModel @Inject constructor(
    private val getHSEListUseCase: GetWorkerHSEListUseCase,
    private val refreshHSEListUseCase: RefreshHSEListUseCase
): ViewModel() {

    private val hseListFlow = getHSEListUseCase()

//    private val loadDataEvents = MutableSharedFlow<Unit>()
//
//    private val loadDataFlow = flow {
//        loadDataEvents.collect{
//            emit(HseScreenState.HSE_List(hseListFlow.value))
//        }
//    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val screenState = hseListFlow
        .catch {  }
        .filter {
            _isLoading.value = false
            it.isNotEmpty()
        }
        .map { HseScreenState.HSE_List(it) as HseScreenState }
        .onStart { emit(HseScreenState.Loading) }


//        .mergeWith(loadDataFlow)

    fun refresh(){
        viewModelScope.launch {
            _isLoading.value = true
            refreshHSEListUseCase()
        }
    }
}