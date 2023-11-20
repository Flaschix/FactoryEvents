package com.example.factoryevents.domain.entity

sealed class Order{
    data class FireOrder(
        val title: String
    ): Order()


}



