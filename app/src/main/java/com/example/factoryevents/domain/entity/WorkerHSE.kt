package com.example.factoryevents.domain.entity

data class WorkerHSE(
    val manager: String,
    val supervisor: String,

    val listOfHSE: List<HSE>
)
