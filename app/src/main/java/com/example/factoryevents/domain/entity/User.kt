package com.example.factoryevents.domain.entity

data class User(
    val mail: String = "",
    val rank: AccessType = AccessType.NONE,
    val firstName: String = "",
    val secondName: String = "",
    val function: String = "",
)

enum class AccessType{
    L1, L2, L3, NONE
}