package com.example.factoryevents.presentation.OJT

import com.example.factoryevents.presentation.HSE.SortTypeOwn

sealed class SortTypeIncident(val name: String)  {
    object ALL : SortTypeIncident("Все")
    object COVID : SortTypeIncident("COVID 19")
    object SAF: SortTypeIncident("SAF")
    object SGR: SortTypeIncident("SGR")

    fun getNext(): SortTypeIncident = when(this){
        is ALL -> COVID
        is COVID -> SAF
        is SAF -> SGR
        is SGR -> ALL
    }

    fun getPrevious(): SortTypeIncident = when(this){
        is ALL -> SGR
        is COVID -> ALL
        is SAF -> COVID
        is SGR -> SAF
    }
}