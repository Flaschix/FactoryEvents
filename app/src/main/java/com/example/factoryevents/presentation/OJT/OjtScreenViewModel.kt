package com.example.factoryevents.presentation.OJT

import androidx.lifecycle.ViewModel
import com.example.factoryevents.domain.usecase.GetOJTListUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class OjtScreenViewModel @Inject constructor(
    private val getOJTListUseCase: GetOJTListUseCase
): ViewModel() {

    private val ojtListFlow = getOJTListUseCase()

    val screenState = ojtListFlow
        .catch {  }
        .filter { it.isNotEmpty() }
        .map { OjtScreenState.OJT_List(it) as OjtScreenState }
        .onStart { emit(OjtScreenState.Loading) }
}