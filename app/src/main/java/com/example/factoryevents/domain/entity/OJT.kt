package com.example.factoryevents.domain.entity

data class OJT(
    val id: Int,
    val type: String,
    val week: String,
    val place: String,
    val offence: String,
    val img: String,
    val byWhomOpened: String,
    val areResponsible: String,
    val options: String,
    val pilot: String,
    val dueDate: String,
    val status: Boolean
)