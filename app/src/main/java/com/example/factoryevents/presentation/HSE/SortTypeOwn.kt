package com.example.factoryevents.presentation.HSE

sealed class SortTypeOwn(val name: String) {
    object All : SortTypeOwn("Все")
    object Mine: SortTypeOwn("Мои")

    fun getNext(): SortTypeOwn = when(this){
        is All -> Mine
        is Mine -> All
    }
}