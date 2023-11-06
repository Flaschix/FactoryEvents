package com.example.factoryevents.domain.entity

data class WorkerHSE(
    val id: Int,
    val manager: String,
    val supervisor: String,

    val listOfHSE: List<HSE>
)
