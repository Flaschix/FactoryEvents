package com.example.factoryevents.presentation.OJT

import com.example.factoryevents.domain.entity.OJT

sealed class OjtScreenState {

    object Initial: OjtScreenState()

    object Loading: OjtScreenState()

    data class OJT_List(
        val list: List<OJT>
    ): OjtScreenState()
}