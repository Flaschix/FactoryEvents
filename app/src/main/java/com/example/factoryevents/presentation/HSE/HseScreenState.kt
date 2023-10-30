package com.example.factoryevents.presentation.HSE

import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.WorkerHSE

sealed class HseScreenState{

    object Initial: HseScreenState()

    object Loading: HseScreenState()

    data class HSE_List(
        val list: List<WorkerHSE>
    ): HseScreenState()
}
