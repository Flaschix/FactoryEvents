package com.example.factoryevents.domain.entity

data class User(
    val mail: String,
    val rank: AccessType,
    val firstName: String,
    val secondName: String
)

enum class AccessType{
    L1, L2, L3
}