package com.example.factoryevents.data.mapper

import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.entity.User
import kotlinx.coroutines.delay
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun mapResponseToHSE(){

    }

    fun mapResponseToOJT(){

    }

    fun mapResponseToUser(): User{
        Thread.sleep(5000)
        return User("awd@efes", AccessType.L3, "gre","ad")
    }
}